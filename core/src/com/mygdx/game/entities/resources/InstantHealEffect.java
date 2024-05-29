package com.mygdx.game.entities.resources;

public class InstantHealEffect implements ResourcesEffects<ResourcesManager> {
    private boolean isCompleted;
    private final float heal;
    public InstantHealEffect(float heal){
        this.heal = heal;
    }
    @Override
    public void apply(ResourcesManager resourcesManager, float deltaTime) {
        resourcesManager.increaseHealth(heal);
        isCompleted = true;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
