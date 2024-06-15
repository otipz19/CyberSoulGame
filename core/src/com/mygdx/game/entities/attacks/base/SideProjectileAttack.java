package com.mygdx.game.entities.attacks.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.utils.DelayedAction;
/**
 * SideProjectileAttack is an abstract class representing a ranged projectile attack in a side-scrolling context.
 * It manages projectile spawn points on both sides of an entity and handles execution of the attack.
 */
public abstract class SideProjectileAttack extends SideAttack implements RangeAttack {

    protected Entity attacker;

    protected ProjectileSpawnPoint leftSpawn;

    protected ProjectileSpawnPoint rightSpawn;

    /**
     * Constructs a SideProjectileAttack with the specified attacker and projectile spawn points.
     *
     * @param attacker The entity performing the attack.
     * @param leftSpawn Projectile spawn point on the left side relative to the attacker.
     * @param rightSpawn Projectile spawn point on the right side relative to the attacker.
     */
    public SideProjectileAttack(Entity attacker, ProjectileSpawnPoint leftSpawn, ProjectileSpawnPoint rightSpawn) {
        this.attacker = attacker;
        this.leftSpawn = leftSpawn;
        this.rightSpawn = rightSpawn;
    }

    /**
     * Executes the projectile attack by determining the spawn point and angle based on the direction.
     * Delays the creation of the projectile by the attack delay duration.
     */
    @Override
    public void execute() {
        Vector2 spawn;
        float angle;
        if (facingRight) {
            spawn = rightSpawn.getPoint(attacker, getProjectileWidth(), getProjectileHeight());
            angle = 0;
        } else {
            spawn = leftSpawn.getPoint(attacker, getProjectileWidth(), getProjectileHeight());
            angle = MathUtils.PI;
        }
        attacker.getLevel().addDelayedAction(getAttackDelay(), () -> createProjectile(attacker, spawn.x, spawn.y, angle));
    }

    /**
     * Creates a projectile instance based on the specified parameters.
     *
     * @param owner The entity that owns the projectile.
     * @param x The x-coordinate of the projectile's initial position.
     * @param y The y-coordinate of the projectile's initial position.
     * @param initialAngle The initial angle (in radians) of the projectile's trajectory.
     * @return The created projectile instance.
     */
    public abstract Projectile createProjectile(Entity owner, float x, float y, float initialAngle);
}
