package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.levels.Level;

/**
 * GameObject is an abstract class that serves as a base for entities in the game world.
 * It encapsulates properties such as a Box2D Body and the Level context in which the entity exists.
 */
public abstract class GameObject {

    /** The Box2D Body associated with the GameObject for physics simulation. */
    protected Body body;

    /** The Level context in which the GameObject exists. */
    protected Level level;

    /**
     * Retrieves the Box2D Body associated with the GameObject.
     *
     * @return The Box2D Body associated with the GameObject.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the Box2D Body associated with the GameObject.
     *
     * @param body The Box2D Body to set for the GameObject.
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Retrieves the Level context in which the GameObject exists.
     *
     * @return The Level context in which the GameObject exists.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the Level context in which the GameObject exists.
     *
     * @param level The Level to set for the GameObject.
     */
    public void setLevel(Level level) {
        this.level = level;
    }
}