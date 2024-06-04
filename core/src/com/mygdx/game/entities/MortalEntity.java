package com.mygdx.game.entities;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.resources.ResourcesEffects;
import com.mygdx.game.entities.resources.ResourcesManager;

public abstract class MortalEntity<T extends ResourcesManager> extends Entity{
    protected T resourcesManager;
    private final Array<ResourcesEffects<T>> resourcesEffects = new Array<>();
    private final Array<Runnable> onDeathActions = new Array<>();

    public void updateResourcesManager(float deltaTime){
        for (ResourcesEffects<T> effect : resourcesEffects){
            effect.apply(resourcesManager, deltaTime);
            if (effect.isCompleted())
                resourcesEffects.removeValue(effect, true);
        }
        resourcesManager.update(deltaTime);
        if (!isAlive())
            onDeathActions.forEach(Runnable::run);
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

    public void addOnDeathAction(Runnable runnable) {
        onDeathActions.add(runnable);
    }

    public void removeOnDeathAction(Runnable runnable) {
        onDeathActions.removeValue(runnable, true);
    }

    public void clearOnDeathActions() {
        onDeathActions.clear();
    }
}
