package com.mygdx.game.entities.obstacles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.obstacles.HammerObstacleAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.ObstacleData;
import com.mygdx.game.physics.Collider;

public class HammerObstacle extends GateObstacle {
    public HammerObstacle(Level level, Collider collider, ObstacleData obstacleData) {
        super(level, collider, obstacleData);
    }

    @Override
    protected Animator createAnimator() {
        return new HammerObstacleAnimator();
    }
}
