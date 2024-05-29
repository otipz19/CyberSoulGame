package com.mygdx.game.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.animation.EntryObstacleAnimator;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.resources.InstantDamageEffect;
import com.mygdx.game.entities.resources.ResourcesManager;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;

public class EntryObstacle extends Entity implements ICollisionListener {
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

    private Fixture fixture;

    private Array<MortalEntity<ResourcesManager>> entitiesToDamage =  new Array<>();


    public EntryObstacle(Level level, Collider collider) {
        this.level = level;
        this.body = new Surface(level, collider).getBody();

        animator = new EntryObstacleAnimator();
        width = 2;
        height = 3;
        fixture = body.getFixtureList().first();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = SensorPosition.SLIM_INSIDE.getColliderShape(width, height);
        fixtureDef.isSensor = true;

        Fixture damageFixture = body.createFixture(fixtureDef);
        damageFixture.setUserData(this);
    }

    @Override
    public void render(float deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime > PERIOD) {
            elapsedTime = 0;
            stateIndex++;
            State curState = getCurrentState();
            fixture.setSensor(curState == State.OPENED);
            if(curState == State.OPENING){
                animator.setState(EntryObstacleAnimator.State.OPENING);
                entitiesToDamage.forEach(e -> e.addResourcesEffect(new InstantDamageEffect(50)));
            } else if(curState == State.CLOSING) {
                animator.setState(EntryObstacleAnimator.State.CLOSING);
                entitiesToDamage.forEach(e -> e.addResourcesEffect(new InstantDamageEffect(50)));
            }
        }
        animator.animate(level.game.batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);
    }

    private State getCurrentState() {
        return STATES[stateIndex % STATES.length];
    }

    @Override
    public void onCollisionEnter(GameObject other) {
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
    public void onCollisionExit(GameObject other) {
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
