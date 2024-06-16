package com.mygdx.game.entities.attacks.base;

import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.heroes.Hero;

/**
 * HeroRangeAttack extends SideProjectileAttack and implements functionality for ranged attacks performed by hero entities.
 */
public abstract class HeroRangeAttack extends SideProjectileAttack {
    private final Hero hero;

    /**
     * Constructs a HeroRangeAttack with the specified hero and projectile spawn points.
     *
     * @param hero        The hero initiating the attack.
     * @param leftSpawn   The spawn point for projectiles on the left side.
     * @param rightSpawn  The spawn point for projectiles on the right side.
     */
    public HeroRangeAttack(Hero hero, ProjectileSpawnPoint leftSpawn, ProjectileSpawnPoint rightSpawn) {
        super(hero, leftSpawn, rightSpawn);
        this.hero = hero;
    }

    /**
     * Retrieves the damage multiplier applied to the hero's ranged attacks.
     *
     * @return The damage multiplier for the hero's attacks.
     */
    protected float getDamageMultiplier() {
        return hero.getResourcesManager().getDamageMultiplier();
    }

    /**
     * Returns the attack interval for the hero's ranged attack.
     * Override this method to specify the interval between consecutive attacks.
     *
     * @return The attack interval for the hero's ranged attack.
     */
    @Override
    public float getAttackInterval() {
        return 0f;
    }
}
