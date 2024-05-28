package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.animation.EntryObstacleAnimator;
import com.mygdx.game.levels.Level;

public class EntryObstacle extends Entity {
    private enum State {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING,
    }

    private static final State[] STATES = {State.CLOSED, State.OPENING, State.OPENED, State.CLOSING};
    private int stateIndex;

    private static final float PERIOD = 0.5f;
    private float elapsedTime;

    private Fixture fixture;


    public EntryObstacle(Level level, Body body) {
        this.level = level;
        this.body = body;
        animator = new EntryObstacleAnimator();
        width = 2;
        height = 3;
        fixture = body.getFixtureList().first();
    }

    @Override
    public void render() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (elapsedTime > PERIOD) {
            elapsedTime = 0;
            stateIndex++;
            State curState = STATES[stateIndex % STATES.length];
            fixture.setSensor(curState == State.OPENED);
            if(curState == State.OPENING){
                animator.setState(EntryObstacleAnimator.State.OPENING);
            } else if(curState == State.CLOSING) {
                animator.setState(EntryObstacleAnimator.State.CLOSING);
            }
        }
        animator.animate(level.game.batch, body.getPosition().x, body.getPosition().y, width, height);
    }
}
