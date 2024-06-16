package com.mygdx.game.animation.concrete.obstacles;

import com.mygdx.game.utils.Assets;

/**
 * Animator class specifically for an entry obstacle.
 * Inherits from GateObstacleAnimator.
 */
public class EntryObstacleAnimator extends GateObstacleAnimator {

    /**
     * Specifies the number of columns in the sprite sheet for the obstacle.
     *
     * @return Number of columns.
     */
    @Override
    protected int getCols() {
        return 5;
    }

    /**
     * Specifies the frame duration for each frame of animation.
     *
     * @return Frame duration in seconds.
     */
    @Override
    protected float getFrameDuration() {
        return 1 / 16f;
    }

    /**
     * Specifies the sprite sheet for the closed state of the obstacle.
     *
     * @return Path to the sprite sheet for the closed state.
     */
    @Override
    protected String getClosedStateSheet() {
        return Assets.Textures.ENTRY_OBSTACLE_CLOSED;
    }

    /**
     * Specifies the sprite sheet for the closing state of the obstacle.
     *
     * @return Path to the sprite sheet for the closing state.
     */
    @Override
    protected String getClosingStateSheet() {
        return Assets.Textures.ENTRY_OBSTACLE_CLOSING;
    }

    /**
     * Specifies the sprite sheet for the opening state of the obstacle.
     *
     * @return Path to the sprite sheet for the opening state.
     */
    @Override
    protected String getOpeningStateSheet() {
        return Assets.Textures.ENTRY_OBSTACLE_OPENING;
    }
}
