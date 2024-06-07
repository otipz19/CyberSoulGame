package com.mygdx.game.entities.obstacles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.obstacles.GateObstacleAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.Surface;
import com.mygdx.game.entities.projectiles.ProjectileCollidable;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
import com.mygdx.game.entities.resources.RelativeInstantDamageEffect;
import com.mygdx.game.entities.resources.ResourcesManager;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.ObstacleData;
import com.mygdx.game.physics.Collider;

import java.util.HashMap;
import java.util.Map;

public abstract class GateObstacle extends Entity implements ICollisionListener, ProjectileCollidable {
    private enum State {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING,
    }

    private static final Map<State, Animator.State> objectToAnimatorState = new HashMap<>() {{
        put(State.OPENING, GateObstacleAnimator.State.OPENING);
        put(State.CLOSING, GateObstacleAnimator.State.CLOSING);
    }};

    private static final State[] STATES = {State.CLOSED, State.OPENING, State.OPENED, State.CLOSING};
    private int stateIndex;
    private State state;

    private static final float STATIC_STATES_PERIOD = 1f;
    private float period;
    private float elapsedTime;

    private final Fixture mainFixture;
    private final Array<MortalEntity<ResourcesManager>> entitiesToDamage = new Array<>();

    public GateObstacle(Level level, Collider collider, ObstacleData obstacleData) {
        this.level = level;
        this.body = new Surface(level, collider).getBody();
        animator = createAnimator();
        setupSize(obstacleData, level.getCoordinatesProjector());
        mainFixture = body.getFixtureList().first();
        createDamageFixture();
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

    private void updateState(float deltaTime, Runnable onStateChanged) {
        elapsedTime += deltaTime;
        if (elapsedTime > period) {
            elapsedTime = 0;
            stateIndex++;
            state = getCurrentState();
            onStateChanged.run();
        }
    }

    private State getCurrentState() {
        return STATES[stateIndex % STATES.length];
    }

    private void onStateChanged() {
        switch (state) {
            case CLOSED -> {
                allowPass(false);
                damageEntitiesInside();
                setStaticPeriod();
            }
            case OPENING -> {
                allowPass(false);
                updateAnimation();
                setAnimationPeriod();
            }
            case OPENED -> {
                allowPass(true);
                setStaticPeriod();
            }
            case CLOSING -> {
                allowPass(false);
                freezeEntitiesInside();
                updateAnimation();
                setAnimationPeriod();
            }
        }
    }

    private void allowPass(boolean allow) {
        mainFixture.setSensor(allow);
    }

    private void setStaticPeriod() {
        period = STATIC_STATES_PERIOD;
    }

    private void setAnimationPeriod() {
        period = animator.getCurrentAnimationDuration();
    }

    private void damageEntitiesInside() {
        entitiesToDamage.forEach(e -> e.addResourcesEffect(new RelativeInstantDamageEffect<>(1)));
    }

    private void freezeEntitiesInside() {
        entitiesToDamage.forEach(e -> e.getBody().setType(BodyDef.BodyType.StaticBody));
    }

    private void updateAnimation() {
        animator.setState(objectToAnimatorState.get(state));
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof MortalEntity) {
            try {
                // can't replace with instanceof check for some reason
                var entity = (MortalEntity<ResourcesManager>) other;
                entitiesToDamage.add(entity);
            } catch (Exception e) {
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
            } catch (Exception e) {
                //do nothing
            }
        }
    }
}
