package com.mygdx.game.entities.resources;

/**
 * This class represents a relative instant damage effect applied to a ResourcesManager,
 * where the damage is based on a fraction of the maximum durability of the resources manager.
 *
 * @param <T> The type of ResourcesManager this effect operates on.
 */
public class RelativeInstantDamageEffect<T extends ResourcesManager> extends InstantDamageEffect<T> {

    /**
     * The fraction of maximum durability to use as damage. Should be between 0 and 1.
     */
    private final float fraction;

    /**
     * Constructs a RelativeInstantDamageEffect object with the specified fraction of max durability.
     *
     * @param fraction The fraction of max durability. Should be between 0 and 1.
     */
    public RelativeInstantDamageEffect(float fraction) {
        this.fraction = Math.clamp(fraction, 0, 1);
    }

    /**
     * Retrieves the amount of damage to apply, which is calculated as a fraction of the maximum durability
     * of the resources manager.
     *
     * @param resourcesManager The resources manager to calculate damage for.
     * @return The amount of damage to apply.
     */
    @Override
    protected float getDamage(T resourcesManager) {
        return resourcesManager.getMaxDurability() * fraction;
    }
}
