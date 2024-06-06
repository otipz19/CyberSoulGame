package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Null;

public class MyAnimation extends Animation<TextureRegion> {
    private final boolean isBlocked;
    private final int priority;
    private final Animator.State fallbackState;

    public MyAnimation(float frameDuration, int priority, boolean isBlocked, Animator.State fallbackState, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
        this.isBlocked = isBlocked;
        this.priority = priority;
        this.fallbackState = fallbackState;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public int getPriority() {
        return priority;
    }

    @Null
    public Animator.State getFallbackState() {
        return fallbackState;
    }
}
