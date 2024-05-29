package com.mygdx.game.entities.resources;

public abstract class ResourcesManager {
    protected float health;
    protected float maxHealth;

    public ResourcesManager(float health, float maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public abstract void increaseHealth(float delta);
    public abstract void decreaseHealth(float delta);
    public abstract void update(float deltaTime);
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

    public void setMaxHealth(float maxHealth) {
        if (maxHealth <= 0)
            throw new RuntimeException("Max health can not be 0 or less");
        this.maxHealth = maxHealth;
    }
}
