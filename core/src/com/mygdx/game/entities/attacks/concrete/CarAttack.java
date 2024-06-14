package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.projectiles.CarProjectile;
import com.mygdx.game.entities.projectiles.EnemyProjectile;
import com.mygdx.game.entities.projectiles.Projectile;

/**
 * CarAttack represents a specific ranged attack performed by an entity using car projectiles.
 * It extends the SideProjectileAttack class and defines the behavior and attributes specific
 * to this type of attack.
 */
public class CarAttack extends SideProjectileAttack {

    /**
     * Constructs a CarAttack instance for the specified attacker entity.
     *
     * @param attacker The entity performing the ranged attack.
     */
    public CarAttack(Entity attacker) {
        super(attacker, ProjectileSpawnPoint.BOTTOM_LEFT, ProjectileSpawnPoint.BOTTOM_RIGHT);
    }

    /**
     * Retrieves the duration of the attack execution.
     *
     * @return The attack duration in seconds.
     */
    @Override
    public float getAttackTime() {
        return 0.4f;
    }

    /**
     * Retrieves the delay before the attack executes.
     *
     * @return The delay duration in seconds.
     */
    @Override
    public float getAttackDelay() {
        return 0.15f;
    }

    /**
     * Retrieves the interval between successive attacks.
     *
     * @return The interval duration in seconds.
     */
    @Override
    public float getAttackInterval() {
        return 0.8f;
    }

    /**
     * Retrieves the width of the projectile spawned during the attack.
     *
     * @return The width of the projectile in game units.
     */
    @Override
    public float getProjectileWidth() {
        return 1.2f;
    }

    /**
     * Retrieves the height of the projectile spawned during the attack.
     *
     * @return The height of the projectile in game units.
     */
    @Override
    public float getProjectileHeight() {
        return 0.3f;
    }

    /**
     * Creates a car projectile instance with specified parameters.
     *
     * @param owner The entity that owns the projectile.
     * @param x     The initial x-coordinate of the projectile.
     * @param y     The initial y-coordinate of the projectile.
     * @param initialAngle The initial angle (in radians) of the projectile.
     * @return The created projectile instance.
     */
    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        EnemyProjectile projectile = new CarProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(25);
        return projectile;
    }
}
