package com.mygdx.game.entities.obstacles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.obstacles.EntryObstacleAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.ObstacleData;
import com.mygdx.game.physics.Collider;

/**
 * Represents an entry obstacle in the game, extending the GateObstacle class.
 * It initializes with a specific animator and provides functionality related to entry points.
 */
public class EntryObstacle extends GateObstacle {

    /**
     * Constructs an EntryObstacle object.
     *
     * @param level        The level where the obstacle is located.
     * @param collider     The collider defining the physical boundaries of the obstacle.
     * @param obstacleData Data specific to this obstacle, such as its dimensions and position.
     */
    public EntryObstacle(Level level, Collider collider, ObstacleData obstacleData) {
        super(level, collider, obstacleData);
    }

    /**
     * Creates and returns the animator specific to this entry obstacle.
     * This method is overridden to provide the EntryObstacleAnimator.
     *
     * @return The animator object for animating this entry obstacle.
     */
    @Override
    protected Animator createAnimator() {
        return new EntryObstacleAnimator();
    }
}
