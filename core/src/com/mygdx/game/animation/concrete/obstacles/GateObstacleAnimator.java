package com.mygdx.game.animation.concrete.obstacles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;

/**
 * Abstract animator class specifically for gate obstacles.
 * Extends Animator and defines states such as CLOSED, CLOSING, and OPENING.
 */
public abstract class GateObstacleAnimator extends Animator {

    /**
     * Enumeration of possible states for the gate obstacle animator.
     */
    public enum State implements Animator.State {
        CLOSED,
        CLOSING,
        OPENING,
    }

    /**
     * Creates and initializes a map of animations for the gate obstacle.
     *
     * @return AnimationsMap containing animations for different states of the gate obstacle.
     */
    @Override
    protected AnimationsMap createAnimationsMap() {
        var animations = new AnimationsMap();

        animations.put(State.CLOSED, new AnimationBuilder(getClosedStateSheet())
                .rows(1)
                .cols(1)
                .frameDuration(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());

        animations.put(State.CLOSING, new AnimationBuilder(getClosingStateSheet())
                .rows(1)
                .cols(getCols())
                .frameDuration(getFrameDuration())
                .playMode(Animation.PlayMode.NORMAL)
                .build());

        animations.put(State.OPENING, new AnimationBuilder(getOpeningStateSheet())
                .rows(1)
                .cols(getCols())
                .frameDuration(getFrameDuration())
                .playMode(Animation.PlayMode.NORMAL)
                .build());

        animations.startAnimation = animations.get(State.CLOSED);

        return animations;
    }

    /**
     * Abstract method to be implemented by subclasses to provide the number of columns
     * in the sprite sheet for the obstacle.
     *
     * @return Number of columns in the sprite sheet.
     */
    protected abstract int getCols();

    /**
     * Abstract method to be implemented by subclasses to provide the frame duration
     * for each frame of animation.
     *
     * @return Frame duration in seconds.
     */
    protected abstract float getFrameDuration();

    /**
     * Abstract method to be implemented by subclasses to provide the path to the sprite sheet
     * for the closed state of the obstacle.
     *
     * @return Path to the sprite sheet for the closed state.
     */
    protected abstract String getClosedStateSheet();

    /**
     * Abstract method to be implemented by subclasses to provide the path to the sprite sheet
     * for the closing state of the obstacle.
     *
     * @return Path to the sprite sheet for the closing state.
     */
    protected abstract String getClosingStateSheet();

    /**
     * Abstract method to be implemented by subclasses to provide the path to the sprite sheet
     * for the opening state of the obstacle.
     *
     * @return Path to the sprite sheet for the opening state.
     */
    protected abstract String getOpeningStateSheet();
}
