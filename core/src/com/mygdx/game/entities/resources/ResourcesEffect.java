package com.mygdx.game.entities.resources;

public interface ResourcesEffect<T extends ResourcesManager> {
    void apply(T resourcesManager, float deltaTime);
    boolean isCompleted();
}
