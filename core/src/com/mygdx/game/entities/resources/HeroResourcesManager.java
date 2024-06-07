package com.mygdx.game.entities.resources;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.entities.heroes.HeroData;

public class HeroResourcesManager extends ResourcesManager {
    private final float SHIELD_UNIT_RESTORE_TIME = 3f;
    private final float ENERGY_UNIT_RESTORE_TIME = 0.1f;
    protected float shield;
    protected float maxShield;
    protected float shieldRestoreUnit;
    private float shieldRestoreTimer;
    protected float energy;
    protected float maxEnergy;
    protected float energyRestoreUnit;
    private float energyRestoreTimer;
    protected int souls;

    public HeroResourcesManager(HeroData heroData) {
        super(heroData.health, heroData.maxHealth);
        this.shield = heroData.maxShield;
        this.maxShield = heroData.maxShield;
        this.shieldRestoreUnit = heroData.shieldRestoreUnit;
        this.energy = heroData.maxEnergy;
        this.maxEnergy = heroData.maxEnergy;
        this.energyRestoreUnit = heroData.energyRestorationUnit;
        this.souls = heroData.souls;
    }

    @Override
    public void decreaseHealth(float delta) {
        if (isInvincible)
            return;
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");

        float temp = shield - delta;
        if (temp < 0){
            health += temp;
            shield = 0;
            fireOnHealthChangeEvent(temp);
        }
        else
            shield = temp;
    }

    @Override
    public void update(float deltaTime){
        shieldRestoreTimer = Math.max(shieldRestoreTimer - deltaTime, 0);
        energyRestoreTimer = Math.max(energyRestoreTimer - deltaTime, 0);
        if (shieldRestoreTimer == 0) {
            shield = Math.min(shield + shieldRestoreUnit, maxShield);
            shieldRestoreTimer = SHIELD_UNIT_RESTORE_TIME;
        }
        if (energyRestoreTimer == 0) {
            energy = Math.min(energy + energyRestoreUnit, maxEnergy);
            energyRestoreTimer = ENERGY_UNIT_RESTORE_TIME;
        }
        super.update(deltaTime);
    }

    @Override
    public float getMaxDurability() {
        return getMaxHealth() + getMaxShield();
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

    public float getEnergyPercent() {
        return energy/maxEnergy;
    }

    public float getEnergyValue() {
        return energy;
    }

    public void increaseEnergy(float delta) {
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");
        energy = Math.min(energy + delta, maxEnergy);
    }

    public boolean tryConsumeEnergy(float delta) {
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");
        float newEnergy = energy - delta;
        if (newEnergy < 0)
            return false;
        else {
            energy = newEnergy;
            return true;
        }
    }

    public boolean hasEnergy(float energy) {
        return this.energy - energy >= 0;
    }

    public float getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(float maxEnergy) {
        if (maxEnergy <= 0)
            throw new RuntimeException("Max shield can not be 0 or less");
        this.maxEnergy = maxEnergy;
        energy = MathUtils.clamp(energy, 0, maxEnergy);
    }

    public float getEnergyRestoreUnit() {
        return energyRestoreUnit;
    }

    public void setEnergyRestoreUnit(float energyRestoreUnit) {
        this.energyRestoreUnit = energyRestoreUnit;
    }

    public HeroData getHeroData(){
        HeroData heroData = new HeroData();
        heroData.health = health;
        heroData.maxHealth = maxHealth;
        heroData.souls = souls;
        heroData.maxShield = maxShield;
        heroData.shieldRestoreUnit = shieldRestoreUnit;
        heroData.maxEnergy = maxEnergy;
        heroData.energyRestorationUnit = energyRestoreUnit;
        return heroData;
    }
}
