package com.mygdx.game.entities.resources;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Abstract class representing a resources manager that manages health-related operations
 * and provides hooks for handling death and health changes.
 */
public abstract class ResourcesManager {
    /** The current health of the resources manager. */
    protected float health;

    /** The maximum health of the resources manager. */
    protected float maxHealth;

    /** Flag indicating if the resources manager is invincible (immune to damage). */
    protected boolean isInvincible;

    /** Actions to be executed upon the death of the resources manager. */
    protected final Array<Runnable> onDeathActions = new Array<>();

    /** Actions to be executed when the health of the resources manager changes. */
    protected final Array<Function<Float, Boolean>> onHealthChangeActions = new Array<>();

    /**
     * Constructs a ResourcesManager with the specified initial health and maximum health.
     *
     * @param health The initial health of the resources manager.
     * @param maxHealth The maximum health of the resources manager.
     */
    public ResourcesManager(float health, float maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    /**
     * Increases the health of the resources manager by the specified amount.
     *
     * @param delta The amount by which to increase the health. Must be non-negative.
     * @throws RuntimeException if delta is negative.
     */
    public void increaseHealth(float delta) {
        if (delta < 0)
            throw new RuntimeException("Delta cannot be negative");

        float oldHealth = health;
        health = Math.min(health + delta, maxHealth);
        float deltaHealth = health - oldHealth;
        if (deltaHealth != 0)
            fireOnHealthChangeEvent(deltaHealth);
    }

    /**
     * Decreases the health of the resources manager by the specified amount,
     * unless the resources manager is invincible.
     *
     * @param delta The amount by which to decrease the health. Must be non-negative.
     * @throws RuntimeException if delta is negative.
     */
    public void decreaseHealth(float delta) {
        if (isInvincible)
            return;
        if (delta < 0)
            throw new RuntimeException("Delta cannot be negative");

        float oldHealth = health;
        health = Math.max(health - delta, 0);
        float deltaHealth = health - oldHealth;
        if (deltaHealth != 0)
            fireOnHealthChangeEvent(deltaHealth);
    }

    /**
     * Notifies all registered listeners about a change in health.
     *
     * @param deltaHealth The amount by which the health changed.
     */
    protected void fireOnHealthChangeEvent(float deltaHealth) {
        onHealthChangeActions.forEach(action -> {
            boolean isHandled = action.apply(deltaHealth);
            if (isHandled)
                onHealthChangeActions.removeValue(action, true);
        });
    }

    /**
     * Updates the resources manager, typically called once per frame or time step.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    public void update(float deltaTime) {
        if (!isAlive()) {
            onDeathActions.forEach(Runnable::run);
            onDeathActions.clear();
        }
    }

    /**
     * Checks if the resources manager is alive (health > 0).
     *
     * @return {@code true} if the resources manager is alive, {@code false} otherwise.
     */
    public boolean isAlive(){
        return health > 0;
    }

    /**
     * Retrieves the current health of the resources manager.
     *
     * @return The current health value.
     */
    public float getHealthValue() {
        return health;
    }

    /**
     * Retrieves the maximum health of the resources manager.
     *
     * @return The maximum health value.
     */
    public float getMaxHealth() {
        return maxHealth;
    }

    /**
     * Retrieves the percentage of health remaining (current health / max health).
     *
     * @return The health percentage.
     */
    public float getHealthPercent(){
        return health / maxHealth;
    }

    /**
     * Retrieves the maximum durability of the resources manager.
     * This method is equivalent to {@link #getMaxHealth()}.
     *
     * @return The maximum durability value.
     */
    public float getMaxDurability() {
        return maxHealth;
    }

    /**
     * Sets the maximum health of the resources manager.
     * Throws a RuntimeException if the specified max health is zero or negative.
     *
     * @param maxHealth The new maximum health value.
     * @throws RuntimeException if maxHealth is zero or negative.
     */
    public void setMaxHealth(float maxHealth) {
        if (maxHealth <= 0)
            throw new RuntimeException("Max health cannot be 0 or less");
        this.maxHealth = maxHealth;
        this.health = MathUtils.clamp(health, 0, maxHealth);
    }

    /**
     * Checks if the resources manager is invincible (immune to damage).
     *
     * @return {@code true} if the resources manager is invincible, {@code false} otherwise.
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Sets whether the resources manager is invincible (immune to damage).
     *
     * @param invincible {@code true} to make the resources manager invincible, {@code false} otherwise.
     */
    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    /**
     * Adds an action to be executed upon the death of the resources manager.
     *
     * @param runnable The action to be executed upon death.
     */
    public void addOnDeathAction(Runnable runnable) {
        onDeathActions.add(runnable);
    }

    /**
     * Removes an action from the list of actions to be executed upon the death of the resources manager.
     *
     * @param runnable The action to remove from the death actions.
     */
    public void removeOnDeathAction(Runnable runnable) {
        onDeathActions.removeValue(runnable, true);
    }

    /**
     * Clears all actions to be executed upon the death of the resources manager.
     */
    public void clearOnDeathActions() {
        onDeathActions.clear();
    }

    /**
     * Adds an action to be executed when the health of the resources manager changes.
     *
     * @param action The action to be executed upon health change.
     */
    public void addOnHealthChangeAction(Function<Float, Boolean> action) {
        onHealthChangeActions.add(action);
    }

    /**
     * Removes an action from the list of actions to be executed when the health of the resources manager changes.
     *
     * @param action The action to remove from the health change actions.
     */
    public void removeOnHealthChangeAction(Function<Float, Boolean> action) {
        onHealthChangeActions.removeValue(action, true);
    }

    /**
     * Clears all actions to be executed when the health of the resources manager changes.
     */
    public void clearOnHealthChangeActions() {
        onHealthChangeActions.clear();
    }
}
