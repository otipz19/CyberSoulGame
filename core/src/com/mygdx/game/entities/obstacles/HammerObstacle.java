package com.mygdx.game.entities.obstacles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.obstacles.HammerObstacleAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.ObstacleData;
import com.mygdx.game.physics.Collider;

/**
 * Represents a hammer obstacle in the game, extending the GateObstacle class.
 * It provides functionality specific to hammer obstacles, including animation setup.
 */
public class HammerObstacle extends GateObstacle {

    /**
     * Constructs a HammerObstacle object.
     *
     * @param level        The level where the obstacle is located.
     * @param collider     The collider defining the physical boundaries of the obstacle.
     * @param obstacleData Data specific to this obstacle, such as its dimensions and position.
     */
    public HammerObstacle(Level level, Collider collider, ObstacleData obstacleData) {
        super(level, collider, obstacleData);
    }

    /**
     * Creates and returns the animator specific to this hammer obstacle.
     *
     * @return The animator object for animating this hammer obstacle.
     */
    @Override
    protected Animator createAnimator() {
        return new HammerObstacleAnimator();
    }
}
