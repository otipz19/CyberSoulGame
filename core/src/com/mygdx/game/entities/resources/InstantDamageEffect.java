package com.mygdx.game.entities.resources;

public abstract class InstantDamageEffect<T extends ResourcesManager> implements ResourcesEffect<T>{
    private boolean isCompleted;

    @Override
    public void apply(T resourcesManager, float deltaTime) {
        resourcesManager.decreaseHealth(getDamage(resourcesManager));
        isCompleted = true;
    }

    protected abstract float getDamage(T resourcesManager);

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
