package com.mygdx.game.entities.projectiles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.projectiles.CarProjectileAnimator;
import com.mygdx.game.animation.concrete.projectiles.ProjectileAnimator;
import com.mygdx.game.entities.Entity;

/**
 * Represents a projectile fired by an enemy resembling a car in the game.
 * Extends the EnemyProjectile class.
 */
public class CarProjectile extends EnemyProjectile {

    /**
     * Constructs a CarProjectile object with the specified parameters.
     *
     * @param owner        The entity that fired this projectile.
     * @param x            The initial x-coordinate of the projectile.
     * @param y            The initial y-coordinate of the projectile.
     * @param width        The width of the projectile's hitbox.
     * @param height       The height of the projectile's hitbox.
     * @param initialAngle The initial angle (direction) of the projectile.
     */
    public CarProjectile(Entity owner, float x, float y, float width, float height, float initialAngle) {
        super(owner, x, y, width, height, 20, initialAngle, false);
        animator = new CarProjectileAnimator();
        animator.setDirection(body.getLinearVelocity().x > 0 ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        addOnExplosionAction(() -> animator.setState(ProjectileAnimator.State.EXPLODING));
    }

    /**
     * Retrieves the destruction delay of this car projectile.
     * Specifies the time interval after collision before the projectile is destroyed.
     *
     * @return The destruction delay in seconds.
     */
    @Override
    public float getDestructionDelay() {
        return 0.2f;
    }
}
