package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Null;

/**
 * Custom animation class that extends the Animation class from libGDX.
 * Adds properties for priority, blocking, and fallback state.
 */
public class MyAnimation extends Animation<TextureRegion> {
    private final boolean isBlocked;
    private final int priority;
    private final Animator.State fallbackState;

    /**
     * Constructs a MyAnimation with the specified frame duration, priority, blocking state, fallback state, and key frames.
     *
     * @param frameDuration the time between frames in seconds
     * @param priority the priority of the animation
     * @param isBlocked whether the animation blocks state changes until it is finished
     * @param fallbackState the state to revert to when the animation is finished, if blocked
     * @param keyFrames the frames of the animation
     */
    public MyAnimation(float frameDuration, int priority, boolean isBlocked, Animator.State fallbackState, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
        this.isBlocked = isBlocked;
        this.priority = priority;
        this.fallbackState = fallbackState;
    }

    /**
     * Checks if the animation is blocked.
     *
     * @return true if the animation is blocked, false otherwise
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * Gets the priority of the animation.
     *
     * @return the priority of the animation
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the fallback state of the animation.
     *
     * @return the fallback state, or null if there is no fallback state
     */
    @Null
    public Animator.State getFallbackState() {
        return fallbackState;
    }
}
