package com.mygdx.game.entities.resources;

public class InstantDamageEffect implements ResourcesEffects<ResourcesManager> {
    private boolean isCompleted;
    private final float damage;
    public InstantDamageEffect(float damage){
        this.damage = damage;
    }
    @Override
    public void apply(ResourcesManager resourcesManager, float deltaTime) {
        resourcesManager.decreaseHealth(damage);
        isCompleted = true;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
