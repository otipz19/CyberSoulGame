package com.mygdx.game.animation.concrete.obstacles;

import com.mygdx.game.utils.AssetsNames;

public class EntryObstacleAnimator extends GateObstacleAnimator {

    @Override
    protected String getClosedStateSheet() {
        return AssetsNames.ENTRY_OBSTACLE_CLOSED;
    }

    @Override
    protected String getClosingStateSheet() {
        return AssetsNames.ENTRY_OBSTACLE_CLOSING;
    }

    @Override
    protected String getOpeningStateSheet() {
        return AssetsNames.ENTRY_OBSTACLE_OPENING;
    }
}
