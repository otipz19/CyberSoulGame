package com.mygdx.game.entities.resources;

public interface ResourcesEffects<T extends ResourcesManager> {
    void apply(T resourcesManager, float deltaTime);
    boolean isCompleted();
}
