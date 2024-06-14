package com.mygdx.game.entities.resources;

/**
 * This class represents an instant healing effect applied to a ResourcesManager.
 * It implements the ResourcesEffect interface, allowing it to be applied to
 * a specific type of ResourcesManager.
 *
 * @param <T> The type of ResourcesManager this effect operates on.
 */
public class InstantHealEffect<T extends ResourcesManager> implements ResourcesEffect<T> {

    /**
     * Indicates whether the healing effect has been applied completely.
     */
    private boolean isCompleted;

    /**
     * The amount of health to heal the resources manager by.
     */
    private final float heal;

    /**
     * Constructs an InstantHealEffect object with a specified amount of healing.
     *
     * @param heal The amount of health points to heal.
     */
    public InstantHealEffect(float heal) {
        this.heal = heal;
    }

    /**
     * Applies the instant healing effect to the specified {@code resourcesManager}.
     * Increases the health of the resources manager by the amount specified in the constructor.
     *
     * @param resourcesManager The resources manager to apply the effect on.
     * @param deltaTime        The time elapsed since the last frame, in seconds.
     */
    @Override
    public void apply(T resourcesManager, float deltaTime) {
        resourcesManager.increaseHealth(heal);
        isCompleted = true;
    }

    /**
     * Checks if the instant healing effect has been fully applied.
     *
     * @return {@code true} if the healing effect has been applied completely,
     * {@code false} otherwise.
     */
    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
