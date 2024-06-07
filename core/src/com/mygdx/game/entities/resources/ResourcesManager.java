package com.mygdx.game.entities.resources;

import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;

public abstract class ResourcesManager {
    protected float health;
    protected float maxHealth;
    protected boolean isInvincible;
    protected final Array<Runnable> onDeathActions = new Array<>();
    protected final Array<Consumer<Float>> onHealthChangeActions = new Array<>();

    public ResourcesManager(float health, float maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public void increaseHealth(float delta) {
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");

        float oldHealth = health;
        health = Math.min(health + delta, maxHealth);
        float deltaHealth = health - oldHealth;
        if (deltaHealth != 0)
            onHealthChangeActions.forEach(c -> c.accept(deltaHealth));
    }

    public void decreaseHealth(float delta) {
        if (isInvincible)
            return;
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");

        float oldHealth = health;
        health = Math.max(health - delta, 0);
        float deltaHealth = health - oldHealth;
        if (deltaHealth != 0)
            onHealthChangeActions.forEach(c -> c.accept(deltaHealth));
    }

    public void update(float deltaTime) {
        if (!isAlive()) {
            onDeathActions.forEach(Runnable::run);
            onDeathActions.clear();
        }
    }

    public boolean isAlive(){
        return health > 0;
    }

    public float getHealthPercent(){
        return health/maxHealth;
    }

    public float getHealthValue() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getMaxDurability() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        if (maxHealth <= 0)
            throw new RuntimeException("Max health can not be 0 or less");
        this.maxHealth = maxHealth;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
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

    public void addOnHealthChangeAction(Consumer<Float> consumer) {
        onHealthChangeActions.add(consumer);
    }

    public void removeOnHealthChangeAction(Consumer<Float> consumer) {
        onHealthChangeActions.removeValue(consumer, true);
    }

    public void clearOnHealthChangeActions() {
        onHealthChangeActions.clear();
    }
}
