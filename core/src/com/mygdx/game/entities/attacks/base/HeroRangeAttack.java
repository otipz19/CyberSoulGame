package com.mygdx.game.entities.attacks.base;

import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.heroes.Hero;

public abstract class HeroRangeAttack extends SideProjectileAttack {
    private final Hero hero;

    public HeroRangeAttack(Hero hero, ProjectileSpawnPoint leftSpawn, ProjectileSpawnPoint rightSpawn) {
        super(hero, leftSpawn, rightSpawn);
        this.hero = hero;
    }

    protected float getDamageMultiplier() {
        return hero.getResourcesManager().getDamageMultiplier();
    }

    @Override
    public float getAttackInterval() {
        return 0f;
    }
}
