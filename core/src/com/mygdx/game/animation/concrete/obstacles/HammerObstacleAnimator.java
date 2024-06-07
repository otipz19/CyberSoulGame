package com.mygdx.game.animation.concrete.obstacles;

import com.mygdx.game.utils.Assets;

public class HammerObstacleAnimator extends GateObstacleAnimator {
    @Override
    protected int getCols() {
        return 6;
    }

    @Override
    protected float getFrameDuration() {
        return 1 / 18f;
    }

    @Override
    protected String getClosedStateSheet() {
        return Assets.Textures.HAMMER_OBSTACLE_CLOSED;
    }

    @Override
    protected String getClosingStateSheet() {
        return Assets.Textures.HAMMER_OBSTACLE_CLOSING;
    }

    @Override
    protected String getOpeningStateSheet() {
        return Assets.Textures.HAMMER_OBSTACLE_OPENING;
    }
}
