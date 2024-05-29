package com.mygdx.game.entities.resources;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.entities.heroes.HeroData;

public class HeroResourcesManager extends ResourcesManager {

    private final float SHIELD_UNIT_RESTORE_TIME = 3f;
    protected float shield;
    protected float maxShield;
    protected float shieldRestoreUnit;
    private float shieldRestoreTimer;
    protected int souls;

    public HeroResourcesManager(HeroData heroData) {
        super(heroData.health, heroData.maxHealth);
        this.shield = heroData.shield;
        this.maxShield = heroData.maxShield;
        this.shieldRestoreUnit = heroData.shieldRestoreUnit;
        this.souls = heroData.souls;
    }

    @Override
    public void increaseHealth(float delta) {
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");
        health = Math.min(health + delta, maxHealth);
    }

    @Override
    public void decreaseHealth(float delta) {
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");
        float temp = shield - delta;
        if (temp < 0){
            health += temp;
            shield = 0;
        }
        else
            shield = temp;
    }

    @Override
    public void update(float deltaTime){
        shieldRestoreTimer -= deltaTime;
        if (shieldRestoreTimer <= 0) {
            shield = Math.min(shield + shieldRestoreUnit, maxShield);
            shieldRestoreTimer = SHIELD_UNIT_RESTORE_TIME;
        }
    }

    public int getSouls(){
        return souls;
    }

    public void changeSouls(int delta){
        souls = Math.max(souls + delta, 0);
    }

    public float getShieldPercent(){
        return shield/maxShield;
    }

    public float getShieldValue() {
        return shield;
    }

    public float getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(float maxShield) {
        if (maxShield <= 0)
            throw new RuntimeException("Max shield can not be 0 or less");
        this.maxShield = maxShield;
        shield = MathUtils.clamp(shield, 0, maxShield);
    }

    public float getShieldRestoreUnit() {
        return shieldRestoreUnit;
    }

    public void setShieldRestoreUnit(float shieldRestoreUnit) {
        this.shieldRestoreUnit = shieldRestoreUnit;
    }
}
