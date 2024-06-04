package com.mygdx.game.entities.resources;

public class InstantHealEffect<T extends  ResourcesManager> implements ResourcesEffects<T> {
    private boolean isCompleted;
    private final float heal;
    public InstantHealEffect(float heal){
        this.heal = heal;
    }
    @Override
    public void apply(T resourcesManager, float deltaTime) {
        resourcesManager.increaseHealth(heal);
        isCompleted = true;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
