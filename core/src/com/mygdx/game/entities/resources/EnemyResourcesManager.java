package com.mygdx.game.entities.resources;

/**
 * Manages the resources specific to an enemy entity, such as health.
 *
 * <p>Extends {@link ResourcesManager}, providing enemy-specific resource management.
 */
public class EnemyResourcesManager extends ResourcesManager {

    /**
     * Constructs an enemy resources manager with specified maximum health.
     *
     * @param maxHealth The maximum health value that this enemy can have.
     */
    public EnemyResourcesManager(float maxHealth) {
        super(maxHealth, maxHealth);
    }
}
