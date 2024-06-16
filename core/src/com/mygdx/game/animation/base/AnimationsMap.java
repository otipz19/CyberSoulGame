package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * A specialized HashMap that maps Animator states to MyAnimation instances.
 * Provides utility methods for adding default animations and setting a start state.
 */
public class AnimationsMap extends HashMap<Animator.State, MyAnimation> {
    public MyAnimation startAnimation;

    /**
     * Adds a default animation for the given state using the specified asset name and play mode.
     *
     * @param state the animator state
     * @param assetName the name of the asset
     * @param playMode the play mode for the animation
     */
    public void putDefaultAnimation(Animator.State state, String assetName, Animation.PlayMode playMode){
        put(state, new AnimationBuilder(assetName).playMode(playMode).build());
    }

    /**
     * Sets the start animation to the animation associated with the given state.
     *
     * @param state the animator state to be set as the start state
     */
    public void setStartState(Animator.State state) {
        startAnimation = get(state);
    }
}
