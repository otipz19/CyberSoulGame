package com.mygdx.game.entities.obstacles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.obstacles.EntryObstacleAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.Surface;
import com.mygdx.game.entities.resources.InstantDamageEffect;
import com.mygdx.game.entities.resources.ResourcesManager;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.ObstacleData;
import com.mygdx.game.physics.Collider;

import java.util.function.Consumer;

public abstract class GateObstacle extends Entity implements ICollisionListener {
    private enum State {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING,
    }

    private static final State[] STATES = {State.CLOSED, State.OPENING, State.OPENED, State.CLOSING};
    private int stateIndex;

    private static final float PERIOD = 0.75f;
    private float elapsedTime;

    private final Fixture mainFixture;
    private final Array<MortalEntity<ResourcesManager>> entitiesToDamage =  new Array<>();


    public GateObstacle(Level level, Collider collider, ObstacleData obstacleData, CoordinatesProjector projector) {
        this.level = level;
        this.body = new Surface(level, collider).getBody();
        createDamageFixture();
        mainFixture = body.getFixtureList().first();
        setupSize(obstacleData, projector);
        animator = createAnimator();
    }

    protected abstract Animator createAnimator();

    private void createDamageFixture() {
        Shape colliderShape = SensorPosition.SLIM_INSIDE.getColliderShape(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = SensorPosition.SLIM_INSIDE.getColliderShape(width, height);
        fixtureDef.isSensor = true;

        Fixture damageFixture = body.createFixture(fixtureDef);
        damageFixture.setUserData(this);

        colliderShape.dispose();
    }

    private void setupSize(ObstacleData obstacleData, CoordinatesProjector projector) {
        Vector2 worldSize = projector.toWorldSize(obstacleData.getBounds());
        this.width = worldSize.x;
        this.height = worldSize.y;
    }

    @Override
    public void render(float deltaTime) {
        updateState(deltaTime, this::onStateChanged);
        animate(deltaTime);
    }

    private void updateState(float deltaTime, Consumer<State> onStateChanged) {
        elapsedTime += deltaTime;
        if (elapsedTime > PERIOD) {
            elapsedTime = 0;
            stateIndex++;
            State curState = getCurrentState();
            onStateChanged.accept(curState);
        }
    }

    private State getCurrentState() {
        return STATES[stateIndex % STATES.length];
    }

    private void onStateChanged(State state) {
        switch(state) {
            case CLOSED -> {
                mainFixture.setSensor(false);
                damageEntitiesInside();
            }
            case OPENING -> {
                mainFixture.setSensor(true);
                animator.setState(EntryObstacleAnimator.State.OPENING);
            }
            case OPENED -> {
                mainFixture.setSensor(true);
            }
            case CLOSING -> {
                mainFixture.setSensor(false);
                freezeEntitiesInside();
                animator.setState(EntryObstacleAnimator.State.CLOSING);
            }
        }
    }

    private void damageEntitiesInside() {
        entitiesToDamage.forEach(e -> e.addResourcesEffect(new InstantDamageEffect(1000)));
    }

    private void freezeEntitiesInside() {
        entitiesToDamage.forEach(e -> e.getBody().setType(BodyDef.BodyType.StaticBody));
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof MortalEntity) {
            try {
                // can't replace with instanceof check for some reason
                var entity = (MortalEntity<ResourcesManager>) other;
                entitiesToDamage.add(entity);
            }
            catch (Exception e){
                //do nothing
            }
        }
    }

    @Override
    public void onCollisionExit(Entity other) {
        if (other instanceof MortalEntity) {
            try {
                // can't replace with instanceof check for some reason
                var entity = (MortalEntity<ResourcesManager>) other;
                entitiesToDamage.removeValue(entity, true);
            }
            catch (Exception e){
                //do nothing
            }
        }
    }
}
