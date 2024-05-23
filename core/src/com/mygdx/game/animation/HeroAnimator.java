package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.utils.AssetNames;

public class HeroAnimator extends Animator {
    public enum State implements Animator.State {
        IDLE,
        RUNNING,
        JUMPING,
    }

    private float idleTime;
    private static final float IDLE_THRESHOLD = 0.2f;
    
    public HeroAnimator() {
        super(createAnimationsMap());
    }

    private static AnimationsMap createAnimationsMap(){
        AnimationsMap animations = new AnimationsMap();
        animations.putDefaultAnimation(State.IDLE, AssetNames.BIKER_IDLE_SHEET, Animation.PlayMode.LOOP_PINGPONG);
        animations.putDefaultAnimation(State.RUNNING, AssetNames.BIKER_RUN_SHEET, Animation.PlayMode.LOOP);
        animations.putDefaultAnimation(State.JUMPING, AssetNames.BIKER_JUMP_SHEET, Animation.PlayMode.NORMAL);
        animations.startAnimation = animations.get(State.IDLE);
        return animations;
    }

    @Override
    public void setState(Animator.State newState){
        if (newState == HeroAnimator.State.IDLE && curState != HeroAnimator.State.IDLE && idleTime < IDLE_THRESHOLD) {
            idleTime += Gdx.graphics.getDeltaTime();
        } else {
            idleTime = 0;
            super.setState(newState);
        }
    }
}
