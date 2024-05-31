package com.mygdx.game.entities.heroes;

public class HeroData {
    public float health;
    public float maxHealth;
    public float maxShield;
    public float shieldRestoreUnit;
    public int souls;

    public static HeroData getDefault() {
        HeroData heroData = new HeroData();
        heroData.health = 100;
        heroData.maxHealth = 100;
        heroData.maxShield = 50;
        heroData.souls = 0;
        heroData.shieldRestoreUnit = 2;
        return heroData;
    }
}
