package com.mygdx.game.entities.heroes;

import com.mygdx.game.MyGdxGame;

public class HeroData {
    public float health;
    public float maxHealth;
    public float maxShield;
    public float shieldRestoreUnit;
    public float maxEnergy;
    public float energyRestorationUnit;
    public int souls;
    public float damageMultiplier;

    public static HeroData getDefault() {
        HeroData heroData = new HeroData();
        heroData.health = 100;
        heroData.maxHealth = 100;
        heroData.maxShield = 50;
        heroData.shieldRestoreUnit = 2;
        heroData.souls = 0;
        if(MyGdxGame.IS_DEBUG_MODE) {
            heroData.souls = 1000;
        }
        heroData.maxEnergy = 100;
        heroData.energyRestorationUnit = 1;
        heroData.damageMultiplier = 1f;
        return heroData;
    }
}
