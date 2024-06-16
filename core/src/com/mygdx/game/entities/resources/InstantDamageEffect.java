package com.mygdx.game.entities.resources;

/**
 * This abstract class represents an instant damage effect applied to a ResourcesManager.
 * Subclasses must implement the {@code getDamage} method to define how much damage
 * should be applied based on the current state of the resources managed by the
 * {@code ResourcesManager}.
 *
 * @param <T> The type of ResourcesManager this effect operates on.
 */
public abstract class InstantDamageEffect<T extends ResourcesManager> implements ResourcesEffect<T> {

    /**
     * Indicates whether the damage effect has been applied completely.
     */
    private boolean isCompleted;

    /**
     * Applies the instant damage effect to the specified {@code resourcesManager}.
     * Decreases the health of the resources manager based on the damage calculated
     * by the {@code getDamage} method.
     *
     * @param resourcesManager The resources manager to apply the effect on.
     * @param deltaTime        The time elapsed since the last frame, in seconds.
     */
    @Override
    public void apply(T resourcesManager, float deltaTime) {
        resourcesManager.decreaseHealth(getDamage(resourcesManager));
        isCompleted = true;
    }

    /**
     * Retrieves the amount of damage that should be applied to the {@code resourcesManager}.
     * Subclasses must implement this method to define the specific damage calculation logic.
     *
     * @param resourcesManager The resources manager to calculate damage for.
     * @return The amount of damage to apply.
     */
    protected abstract float getDamage(T resourcesManager);

    /**
     * Checks if the instant damage effect has been fully applied.
     *
     * @return {@code true} if the damage effect has been applied completely,
     * {@code false} otherwise.
     */
    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
