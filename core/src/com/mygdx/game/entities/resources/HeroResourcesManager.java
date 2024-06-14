package com.mygdx.game.entities.resources;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.heroes.HeroData;

import java.util.function.Function;
/**
 * Manages the resources (health, shield, energy, souls, etc.) for a hero entity.
 *
 * <p>Extends {@link ResourcesManager}, providing specific management for hero resources.
 */
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
    protected float damageMultiplier;
    protected final Array<Function<Float, Boolean>> onShieldChangeActions = new Array<>();
    /**
     * Constructs a hero resources manager with data from the provided {@link HeroData}.
     *
     * @param heroData The data defining the initial state of the hero's resources.
     */
    public HeroResourcesManager(HeroData heroData) {
        super(heroData.health, heroData.maxHealth);
        this.shield = heroData.maxShield;
        this.maxShield = heroData.maxShield;
        this.shieldRestoreUnit = heroData.shieldRestoreUnit;
        this.energy = heroData.maxEnergy;
        this.maxEnergy = heroData.maxEnergy;
        this.energyRestoreUnit = heroData.energyRestorationUnit;
        this.souls = heroData.souls;
        this.damageMultiplier = heroData.damageMultiplier;
    }
    /**
     * Decreases the hero's health, considering the shield protection if applicable.
     *
     * @param delta The amount of health to decrease.
     */
    @Override
    public void decreaseHealth(float delta) {
        if (isInvincible)
            return;
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");

        float oldShield = shield;
        float temp = shield - delta;
        if (temp < 0){
            health += temp;
            shield = 0;
            fireOnHealthChangeEvent(temp);
        }
        else
            shield = temp;

        if (shield != oldShield)
            fireOnShieldChangeEvent(shield - oldShield);
    }

    protected void fireOnShieldChangeEvent(float deltaShield) {
        onShieldChangeActions.forEach(c -> {
            boolean isHandled = c.apply(deltaShield);
            if (isHandled)
                onShieldChangeActions.removeValue(c, true);
        });
    }
    /**
     * Updates the hero's resources over time, restoring shield and energy.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime){
        if(isAlive()) {
            shieldRestoreTimer = Math.max(shieldRestoreTimer - deltaTime, 0);
            energyRestoreTimer = Math.max(energyRestoreTimer - deltaTime, 0);
            if (shieldRestoreTimer == 0) {
                float oldShield = shield;
                shield = Math.min(shield + shieldRestoreUnit, maxShield);
                shieldRestoreTimer = SHIELD_UNIT_RESTORE_TIME;
                if (shield != oldShield)
                    fireOnShieldChangeEvent(shield - oldShield);
            }
            if (energyRestoreTimer == 0) {
                energy = Math.min(energy + energyRestoreUnit, maxEnergy);
                energyRestoreTimer = ENERGY_UNIT_RESTORE_TIME;
            }
        }
        super.update(deltaTime);
    }
    /**
     * Retrieves the maximum durability of the hero, which is the sum of max health and max shield.
     *
     * @return The maximum durability of the hero.
     */
    @Override
    public float getMaxDurability() {
        return getMaxHealth() + getMaxShield();
    }
    /**
     * Retrieves the current number of souls possessed by the hero.
     *
     * @return The current number of souls.
     */
    public int getSouls(){
        return souls;
    }
    /**
     * Changes the number of souls possessed by the hero by the specified delta amount.
     *
     * @param delta The amount by which to change the souls count.
     */
    public void changeSouls(int delta){
        souls = Math.max(souls + delta, 0);
    }
    /**
     * Retrieves the percentage of current shield relative to maximum shield.
     *
     * @return The percentage of current shield.
     */
    public float getShieldPercent(){
        return shield/maxShield;
    }
    /**
     * Retrieves the current shield value.
     *
     * @return The current shield value.
     */
    public float getShieldValue() {
        return shield;
    }
    /**
     * Retrieves the maximum shield value.
     *
     * @return The maximum shield value.
     */
    public float getMaxShield() {
        return maxShield;
    }
    /**
     * Sets the maximum shield value. If the specified value is less than or equal to 0,
     * a {@link RuntimeException} is thrown.
     *
     * @param maxShield The new maximum shield value.
     * @throws RuntimeException If the specified maxShield is less than or equal to 0.
     */
    public void setMaxShield(float maxShield) {
        if (maxShield <= 0)
            throw new RuntimeException("Max shield can not be 0 or less");
        this.maxShield = maxShield;
        setShield(shield);
    }
    /**
     * Sets the current shield value, clamping it between 0 and the maximum shield value.
     *
     * @param value The new shield value to set.
     */
    public void setShield(float value) {
        if(value < 0) {
            throw new RuntimeException("Shield can not be negative");
        }
        this.shield = MathUtils.clamp(value, 0, maxShield);
    }
    /**
     * Retrieves the amount of shield to restore per unit time.
     *
     * @return The shield restore unit.
     */
    public float getShieldRestoreUnit() {
        return shieldRestoreUnit;
    }
    /**
     * Sets the amount of shield to restore per unit time.
     *
     * @param shieldRestoreUnit The new shield restore unit.
     */
    public void setShieldRestoreUnit(float shieldRestoreUnit) {
        this.shieldRestoreUnit = shieldRestoreUnit;
    }
    /**
     * Retrieves the percentage of current energy relative to maximum energy.
     *
     * @return The percentage of current energy.
     */
    public float getEnergyPercent() {
        return energy/maxEnergy;
    }
    /**
     * Retrieves the current energy value.
     *
     * @return The current energy value.
     */
    public float getEnergyValue() {
        return energy;
    }
    /**
     * Increases the current energy by the specified delta amount. If delta is negative,
     * a {@link RuntimeException} is thrown.
     *
     * @param delta The amount of energy to increase.
     * @throws RuntimeException If the specified delta is negative.
     */
    public void increaseEnergy(float delta) {
        if (delta < 0)
            throw new RuntimeException("Delta can not be negative");
        energy = Math.min(energy + delta, maxEnergy);
    }
    /**
     * Attempts to consume the specified amount of energy. Returns true if the energy was
     * successfully consumed (i.e., the hero had enough energy), false otherwise. If delta
     * is negative, a {@link RuntimeException} is thrown.
     *
     * @param delta The amount of energy to consume.
     * @return true if the energy was successfully consumed, false otherwise.
     * @throws RuntimeException If the specified delta is negative.
     */
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
    /**
     * Checks if the hero has at least the specified amount of energy.
     *
     * @param energy The amount of energy to check.
     * @return true if the hero has at least the specified amount of energy, false otherwise.
     */
    public boolean hasEnergy(float energy) {
        return this.energy - energy >= 0;
    }

    public float getMaxEnergy() {
        return maxEnergy;
    }
    /**
     * Sets the maximum energy value. If the specified value is less than or equal to 0,
     * a {@link RuntimeException} is thrown.
     *
     * @param maxEnergy The new maximum energy value.
     * @throws RuntimeException If the specified maxEnergy is less than or equal to 0.
     */
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

    public void addOnShieldChangeAction(Function<Float, Boolean> action) {
        onShieldChangeActions.add(action);
    }

    public void removeOnShieldChangeAction(Function<Float, Boolean> action) {
        onShieldChangeActions.removeValue(action, true);
    }

    public void setDamageMultiplier(float value) {
        if(value < 0) {
            throw new RuntimeException("Damage multiplier can not be negative!");
        }
        this.damageMultiplier = value;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public void clearOnShieldChangeActions() {
        onShieldChangeActions.clear();
    }
    /**
     * Retrieves the hero's data encapsulated in a {@link HeroData} object.
     *
     * @return A {@link HeroData} object containing the hero's current state.
     */
    public HeroData getHeroData(){
        HeroData heroData = new HeroData();
        heroData.health = health;
        heroData.maxHealth = maxHealth;
        heroData.souls = souls;
        heroData.maxShield = maxShield;
        heroData.shieldRestoreUnit = shieldRestoreUnit;
        heroData.maxEnergy = maxEnergy;
        heroData.energyRestorationUnit = energyRestoreUnit;
        heroData.damageMultiplier = damageMultiplier;
        return heroData;
    }
}
