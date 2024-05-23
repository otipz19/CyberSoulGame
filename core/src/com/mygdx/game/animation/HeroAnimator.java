package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeroAnimator extends Animator {
    public enum State {
        IDLE,
        RUNNING,
        JUMPING,
    }

    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> jumpAnimation;

    private State curState = State.IDLE;

    private float idleTime;
    private static final float IDLE_THRESHOLD = 0.2f;
    
    public HeroAnimator() {
        idleAnimation = createAnimation("biker-idle.png", Animation.PlayMode.LOOP_PINGPONG);
        runAnimation = createAnimation("biker-run.png", Animation.PlayMode.LOOP);
        jumpAnimation = createAnimation("biker-jump.png", Animation.PlayMode.NORMAL);
    }

    public void setState(State newState) {
        if (newState == State.IDLE && curState != State.IDLE && idleTime < IDLE_THRESHOLD) {
            idleTime += Gdx.graphics.getDeltaTime();
        } else {
            idleTime = 0;
            animationChanged = newState != curState;
            curState = newState;
        }
    }

    protected Animation<TextureRegion> selectAnimation() {
        switch (curState) {
            case RUNNING:
                return runAnimation;
            case JUMPING:
                return jumpAnimation;
            default:
                return idleAnimation;
        }
    }
}
