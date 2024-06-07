package com.mygdx.game.animation.concrete.obstacles;

import com.mygdx.game.utils.Assets;

public class EntryObstacleAnimator extends GateObstacleAnimator {

    @Override
    protected int getCols() {
        return 5;
    }

    @Override
    protected float getFrameDuration() {
        return 1 / 16f;
    }

    @Override
    protected String getClosedStateSheet() {
        return Assets.Textures.ENTRY_OBSTACLE_CLOSED;
    }

    @Override
    protected String getClosingStateSheet() {
        return Assets.Textures.ENTRY_OBSTACLE_CLOSING;
    }

    @Override
    protected String getOpeningStateSheet() {
        return Assets.Textures.ENTRY_OBSTACLE_OPENING;
    }
}
