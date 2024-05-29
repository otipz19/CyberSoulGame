package com.mygdx.game.entities;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.resources.ResourcesEffects;
import com.mygdx.game.entities.resources.ResourcesManager;

public abstract class MortalEntity<T extends ResourcesManager> extends Entity{
    protected T resourcesManager;
    private Array<ResourcesEffects<T>> resourcesEffects = new Array<>();

    public void updateResourcesManager(float deltaTime){
        for (ResourcesEffects<T> effect : resourcesEffects){
            effect.apply(resourcesManager, deltaTime);
            if (effect.isCompleted())
                resourcesEffects.removeValue(effect, true);
        }
        resourcesManager.update(deltaTime);
    }

    public boolean isAlive(){
        return resourcesManager.isAlive();
    }

    public T getResourcesManager() {
        return resourcesManager;
    }

    public void setResourcesManager(T resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public void addResourcesEffect(ResourcesEffects<T> effect){
        resourcesEffects.add(effect);
    }
}
