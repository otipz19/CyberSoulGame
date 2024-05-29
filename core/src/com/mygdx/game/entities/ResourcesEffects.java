package com.mygdx.game.entities;

public interface ResourcesEffects<T extends ResourcesManager> {
    void apply(T resourcesManager, float deltaTime);
    boolean isCompleted();
}
