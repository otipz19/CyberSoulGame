package com.mygdx.game.entities.resources;

/**
 * This interface represents an effect that can be applied to a ResourcesManager.
 * Implementing classes define specific behaviors for applying the effect and
 * checking if the effect has been completed.
 *
 * @param <T> The type of ResourcesManager this effect operates on.
 */
public interface ResourcesEffect<T extends ResourcesManager> {

    /**
     * Applies the effect to the specified {@code resourcesManager}.
     *
     * @param resourcesManager The resources manager to apply the effect on.
     * @param deltaTime        The time elapsed since the last frame, in seconds.
     */
    void apply(T resourcesManager, float deltaTime);

    /**
     * Checks if the effect has been completed.
     *
     * @return {@code true} if the effect has been completed, {@code false} otherwise.
     */
    boolean isCompleted();
}
