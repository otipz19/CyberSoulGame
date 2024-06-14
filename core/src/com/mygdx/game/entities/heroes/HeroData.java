package com.mygdx.game.entities.heroes;

import com.mygdx.game.MyGdxGame;

/**
 * Represents the data associated with a hero character, including health, shield, energy, and other attributes.
 */
public class HeroData {
    public float health;
    public float maxHealth;
    public float maxShield;
    public float shieldRestoreUnit;
    public float maxEnergy;
    public float energyRestorationUnit;
    public int souls;
    public float damageMultiplier;

    /**
     * Retrieves a default instance of HeroData with typical attribute values.
     * Adjusts souls count in debug mode.
     *
     * @return A HeroData instance with default attribute values.
     */
    public static HeroData getDefault() {
        HeroData heroData = new HeroData();
        heroData.health = 100;
        heroData.maxHealth = 100;
        heroData.maxShield = 50;
        heroData.shieldRestoreUnit = 2;
        heroData.souls = 10;
        if(MyGdxGame.IS_DEBUG_MODE) {
            heroData.souls = 1000;
        }
        heroData.maxEnergy = 100;
        heroData.energyRestorationUnit = 1;
        heroData.damageMultiplier = 1f;
        return heroData;
    }
}
