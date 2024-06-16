package com.mygdx.game.entities;

/**
 * InteractableEntity is an abstract class that extends Entity, defining entities
 * in the game that can be interacted with by other entities.
 */
public abstract class InteractableEntity extends Entity {
    public abstract void interact(Entity interactionCause);
}
