package com.mygdx.game.entities;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.resources.ResourcesEffect;
import com.mygdx.game.entities.resources.ResourcesManager;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MortalEntity<T extends ResourcesManager> extends Entity{
    protected T resourcesManager;
    private final Array<ResourcesEffect<T>> resourcesEffects;

    public MortalEntity(T resourcesManager){
        this.resourcesManager = resourcesManager;
        resourcesEffects = new Array<>();
        resourcesManager.addOnDeathAction(this::onDeath);
        addOnHealthChangeAction(delta -> {
            if (delta < 0 && resourcesManager.isAlive())
                onNonKillingHealthLoss();
            return false;
        });
    }

    public void updateResourcesManager(float deltaTime){
        for (ResourcesEffect<T> effect : resourcesEffects){
            effect.apply(resourcesManager, deltaTime);
            if (effect.isCompleted())
                resourcesEffects.removeValue(effect, true);
        }
        resourcesManager.update(deltaTime);
    }


    public boolean isAlive(){
        return resourcesManager.isAlive();
    }
    protected abstract void onDeath();
    protected abstract void onNonKillingHealthLoss();

    public abstract float getDeathDelay();

    public T getResourcesManager() {
        return resourcesManager;
    }

    public void setResourcesManager(T resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public void addResourcesEffect(ResourcesEffect<T> effect){
        resourcesEffects.add(effect);
    }

    public void addOnDeathAction(Runnable runnable) {
        resourcesManager.addOnDeathAction(runnable);
    }

    public void removeOnDeathAction(Runnable runnable) {
        resourcesManager.addOnDeathAction(runnable);
    }

    public void clearOnDeathActions() {
        resourcesManager.clearOnDeathActions();
    }

    public void addOnHealthChangeAction(Function<Float, Boolean> action) {
        resourcesManager.addOnHealthChangeAction(action);
    }

    public void removeOnHealthChangeAction(Function<Float, Boolean> action) {
        resourcesManager.removeOnHealthChangeAction(action);
    }

    public void clearOnHealthChangeActions() {
        resourcesManager.clearOnHealthChangeActions();
    }
}
