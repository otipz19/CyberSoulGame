package com.mygdx.game.entities.projectiles;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;

/**
 * Abstract class representing a projectile fired by an enemy in the game.
 * Extends the Projectile class.
 */
public abstract class EnemyProjectile extends Projectile {

    private float damage;

    /**
     * Constructs an EnemyProjectile object with the specified parameters.
     *
     * @param owner             The entity that fired this projectile.
     * @param x                 The initial x-coordinate of the projectile.
     * @param y                 The initial y-coordinate of the projectile.
     * @param width             The width of the projectile's hitbox.
     * @param height            The height of the projectile's hitbox.
     * @param initialSpeed      The initial speed of the projectile.
     * @param initialAngle      The initial angle (direction) of the projectile.
     * @param isAffectedByGravity Whether the projectile is affected by gravity.
     */
    public EnemyProjectile(Entity owner, float x, float y, float width, float height, float initialSpeed, float initialAngle, boolean isAffectedByGravity) {
        super(owner, x, y, width, height, initialSpeed, initialAngle, isAffectedByGravity);
    }

    /**
     * Retrieves the damage value of this projectile.
     *
     * @return The damage value of this projectile.
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Sets the damage value of this projectile.
     *
     * @param damage The damage value to set for this projectile.
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * Handles collision with another entity.
     * If the entity is a MortalEntity, applies an AbsoluteInstantDamageEffect to it based on this projectile's damage.
     *
     * @param entity The entity with which this projectile collides.
     */
    @Override
    protected void collideWith(Entity entity) {
        if (entity instanceof MortalEntity mortalEntity) {
            mortalEntity.addResourcesEffect(new AbsoluteInstantDamageEffect<>(damage));
        }
    }
}
