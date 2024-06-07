package com.mygdx.game.entities.resources;

public class AbsoluteInstantDamageEffect<T extends  ResourcesManager> extends InstantDamageEffect<T> {
    private final float damage;

    public AbsoluteInstantDamageEffect(float damage){
        this.damage = damage;
    }

    @Override
    protected float getDamage(T resourcesManager) {
        return damage;
    }
}
