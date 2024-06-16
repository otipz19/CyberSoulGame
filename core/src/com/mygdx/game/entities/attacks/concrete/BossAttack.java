package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.projectiles.*;
/**
 * BossAttack represents a specific ranged attack where a boss entity launches a projectile
 * towards its target.
 */
public class BossAttack extends SideProjectileAttack {

    /**
     * Constructs a BossAttack for the specified attacker entity.
     *
     * @param attacker The entity performing the boss attack.
     */
    public BossAttack(Entity attacker) {
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
        return 0.3f;
    }

    /**
     * Retrieves the interval between successive attacks.
     *
     * @return The interval duration in seconds.
     */
    @Override
    public float getAttackInterval() {
        return 0.9f;
    }

    /**
     * Retrieves the width of the boss projectile.
     *
     * @return The width of the projectile in game units.
     */
    @Override
    public float getProjectileWidth() {
        return 1.5f;
    }

    /**
     * Retrieves the height of the boss projectile.
     *
     * @return The height of the projectile in game units.
     */
    @Override
    public float getProjectileHeight() {
        return 0.75f;
    }

    /**
     * Creates a boss projectile instance with specified parameters.
     *
     * @param owner The entity that owns the projectile.
     * @param x The x-coordinate of the projectile's initial position.
     * @param y The y-coordinate of the projectile's initial position.
     * @param initialAngle The initial angle (in radians) of the projectile's trajectory.
     * @return The created boss projectile instance.
     */
    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        EnemyProjectile projectile = new BossProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(30); // Sets the damage inflicted by the boss projectile
        return projectile;
    }
}
