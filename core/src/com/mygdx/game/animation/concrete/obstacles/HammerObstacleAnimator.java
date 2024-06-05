package com.mygdx.game.animation.concrete.obstacles;

import com.mygdx.game.utils.AssetsNames;

public class HammerObstacleAnimator extends GateObstacleAnimator {
    @Override
    protected String getClosedStateSheet() {
        return AssetsNames.HAMMER_OBSTACLE_CLOSED;
    }

    @Override
    protected String getClosingStateSheet() {
        return AssetsNames.HAMMER_OBSTACLE_CLOSING;
    }

    @Override
    protected String getOpeningStateSheet() {
        return AssetsNames.HAMMER_OBSTACLE_OPENING;
    }
}
