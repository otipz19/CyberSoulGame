package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AnimationsMap extends HashMap<Animator.State, MyAnimation> {
    public MyAnimation startAnimation;

    public void putDefaultAnimation(Animator.State state, String assetName, Animation.PlayMode playMode){
        put(state, new AnimationBuilder(assetName).playMode(playMode).build());
    }

    public void setStartState(Animator.State state) {
        startAnimation = get(state);
    }
}