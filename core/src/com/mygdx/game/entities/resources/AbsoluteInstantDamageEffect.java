package com.mygdx.game.entities.resources;

/**
 * Represents an absolute instant damage effect that applies a fixed amount of damage
 * when triggered.
 *
 * <p>This class extends {@link InstantDamageEffect}, specializing it by always
 * returning a constant damage value.
 *
 * @param <T> The type of the resources manager that handles the resources required
 *            for triggering this damage effect.
 */
public class AbsoluteInstantDamageEffect<T extends ResourcesManager> extends InstantDamageEffect<T> {

    private final float damage;

    /**
     * Constructs an absolute instant damage effect with a specified amount of damage.
     *
     * @param damage The amount of damage this effect will apply when triggered.
     */
    public AbsoluteInstantDamageEffect(float damage) {
        this.damage = damage;
    }

    /**
     * Retrieves the amount of damage that this effect applies when triggered.
     *
     * @param resourcesManager The resources manager, although not used in this implementation.
     * @return The fixed amount of damage specified during construction.
     */
    @Override
    protected float getDamage(T resourcesManager) {
        return damage;
    }
}
