package com.mygdx.game.entities;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.resources.ResourcesEffect;
import com.mygdx.game.entities.resources.ResourcesManager;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * MortalEntity is an abstract class that extends Entity and manages mortal aspects
 * such as health, death events, and resource effects.
 *
 * @param <T> The type of ResourcesManager associated with the entity.
 */
public abstract class MortalEntity<T extends ResourcesManager> extends Entity {

    /** The resources manager responsible for managing the entity's resources. */
    protected T resourcesManager;

    /** Array of active resource effects affecting the entity. */
    private final Array<ResourcesEffect<T>> resourcesEffects;

    /**
     * Constructs a MortalEntity with a specified resources manager.
     *
     * @param resourcesManager The resources manager associated with the entity.
     */
    public MortalEntity(T resourcesManager){
        this.resourcesManager = resourcesManager;
        resourcesEffects = new Array<>();
        resourcesManager.addOnDeathAction(this::onDeath);
        addOnHealthChangeAction(delta -> {
            if (delta < 0 && resourcesManager.isAlive())
                onNonKillingHealthLoss();
            return false;
        });
    }

    /**
     * Updates the resources manager and applies active resource effects.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    public void updateResourcesManager(float deltaTime){
        for (ResourcesEffect<T> effect : resourcesEffects){
            effect.apply(resourcesManager, deltaTime);
            if (effect.isCompleted())
                resourcesEffects.removeValue(effect, true);
        }
        resourcesManager.update(deltaTime);
    }

    /**
     * Checks if the entity is currently alive based on its resources manager.
     *
     * @return {@code true} if the entity is alive, {@code false} otherwise.
     */
    public boolean isAlive(){
        return resourcesManager.isAlive();
    }

    /**
     * Abstract method to be implemented for handling actions when the entity dies.
     */
    protected abstract void onDeath();

    /**
     * Abstract method to be implemented for handling non-lethal health loss events.
     */
    protected abstract void onNonKillingHealthLoss();

    /**
     * Retrieves the delay before the entity's death occurs.
     *
     * @return The delay before the entity's death in seconds.
     */
    public abstract float getDeathDelay();

    /**
     * Retrieves the resources manager associated with the entity.
     *
     * @return The resources manager associated with the entity.
     */
    public T getResourcesManager() {
        return resourcesManager;
    }

    /**
     * Sets the resources manager associated with the entity.
     *
     * @param resourcesManager The resources manager to set for the entity.
     */
    public void setResourcesManager(T resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    /**
     * Adds a resource effect to the entity, applying it to the resources manager.
     *
     * @param effect The resource effect to add to the entity.
     */
    public void addResourcesEffect(ResourcesEffect<T> effect){
        resourcesEffects.add(effect);
    }

    /**
     * Adds an action to be executed upon the entity's death.
     *
     * @param runnable The action to be executed upon the entity's death.
     */
    public void addOnDeathAction(Runnable runnable) {
        resourcesManager.addOnDeathAction(runnable);
    }

    /**
     * Removes an action that was set to be executed upon the entity's death.
     *
     * @param runnable The action to remove from the death actions.
     */
    public void removeOnDeathAction(Runnable runnable) {
        resourcesManager.addOnDeathAction(runnable);
    }

    /**
     * Clears all actions set to be executed upon the entity's death.
     */
    public void clearOnDeathActions() {
        resourcesManager.clearOnDeathActions();
    }

    /**
     * Adds an action to be executed upon a change in the entity's health.
     *
     * @param action The function defining the action to be executed.
     */
    public void addOnHealthChangeAction(Function<Float, Boolean> action) {
        resourcesManager.addOnHealthChangeAction(action);
    }

    /**
     * Removes an action that was set to be executed upon a change in the entity's health.
     *
     * @param action The function defining the action to remove.
     */
    public void removeOnHealthChangeAction(Function<Float, Boolean> action) {
        resourcesManager.removeOnHealthChangeAction(action);
    }

    /**
     * Clears all actions set to be executed upon a change in the entity's health.
     */
    public void clearOnHealthChangeActions() {
        resourcesManager.clearOnHealthChangeActions();
    }
}
