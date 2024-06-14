package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.HeroRangeAttack;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.entities.projectiles.PunkSProjectile;
/**
 * PunkAttack1 represents a ranged attack performed by a Punk hero entity.
 * It extends the HeroRangeAttack class and defines the behavior and attributes specific
 * to this type of ranged attack.
 */
public class PunkAttack1 extends HeroRangeAttack {

    /**
     * Constructs a PunkAttack1 instance for the specified attacker hero entity.
     *
     * @param attacker The hero entity performing the ranged attack.
     */
    public PunkAttack1(Hero attacker) {
        super(attacker, ProjectileSpawnPoint.MIDDLE_LEFT, ProjectileSpawnPoint.MIDDLE_RIGHT);
    }

    /**
     * Retrieves the duration of the attack execution.
     *
     * @return The attack duration in seconds.
     */
    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    /**
     * Retrieves the delay before the attack executes.
     *
     * @return The delay duration in seconds.
     */
    @Override
    public float getAttackDelay() {
        return 0.35f;
    }

    /**
     * Retrieves the width of the projectile fired during the attack.
     *
     * @return The width of the projectile in game units.
     */
    @Override
    public float getProjectileWidth() {
        return 0.5f;
    }

    /**
     * Retrieves the height of the projectile fired during the attack.
     *
     * @return The height of the projectile in game units.
     */
    @Override
    public float getProjectileHeight() {
        return 0.5f;
    }

    /**
     * Retrieves the energy consumption required for executing the attack.
     * Overrides the default implementation in HeroRangeAttack to specify a custom energy consumption value.
     *
     * @return The energy consumption value in game units.
     */
    @Override
    public float getEnergyConsumption() {
        return 10f;
    }

    /**
     * Creates and returns a PunkSProjectile instance with specified parameters.
     * Sets the damage of the projectile based on the damage multiplier of the attacking hero.
     *
     * @param owner        The entity that owns or initiates the projectile.
     * @param x            The initial x-coordinate position of the projectile.
     * @param y            The initial y-coordinate position of the projectile.
     * @param initialAngle The initial angle or direction of the projectile.
     * @return A Projectile object representing the projectile created.
     */
    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        PunkSProjectile projectile = new PunkSProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(15 * getDamageMultiplier());
        return projectile;
    }
}
