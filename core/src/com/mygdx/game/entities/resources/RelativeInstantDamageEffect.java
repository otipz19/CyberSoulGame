package com.mygdx.game.entities.resources;

public class RelativeInstantDamageEffect<T extends ResourcesManager> extends InstantDamageEffect<T> {
    private final float fraction;

    /**
     * @param fraction of max durability. From 0 to 1
     */
    public RelativeInstantDamageEffect(float fraction) {
        this.fraction = Math.clamp(fraction, 0, 1);
    }

    @Override
    protected float getDamage(T resourcesManager) {
        return resourcesManager.getMaxDurability() * fraction;
    }
}
