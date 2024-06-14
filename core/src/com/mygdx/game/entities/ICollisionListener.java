package com.mygdx.game.entities;

/**
 * ICollisionListener is an interface implemented by classes that need to handle
 * collision events between entities in the game.
 */
public interface ICollisionListener {

    /**
     * Called when a collision begins with another Entity.
     *
     * @param other The Entity with which the collision has started.
     */
    void onCollisionEnter(Entity other);

    /**
     * Called when a collision ends with another Entity.
     *
     * @param other The Entity with which the collision has ended.
     */
    void onCollisionExit(Entity other);
}
