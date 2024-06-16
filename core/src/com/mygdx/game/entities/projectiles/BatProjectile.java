package com.mygdx.game.entities.projectiles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.projectiles.BatProjectileAnimator;
import com.mygdx.game.animation.concrete.projectiles.ProjectileAnimator;
import com.mygdx.game.animation.concrete.projectiles.PunkScytheProjectileAnimator;
import com.mygdx.game.entities.Entity;

/**
 * Represents a projectile fired by an enemy, specifically a BatProjectile.
 * Extends the EnemyProjectile class and implements projectile behavior.
 */
public class BatProjectile extends EnemyProjectile {

    /**
     * Constructs a BatProjectile with the specified parameters.
     *
     * @param owner        The Entity that owns or fired this projectile.
     * @param x            The initial x-coordinate of the projectile's position.
     * @param y            The initial y-coordinate of the projectile's position.
     * @param width        The width of the projectile.
     * @param height       The height of the projectile.
     * @param initialAngle The initial angle of the projectile's movement.
     */
    public BatProjectile(Entity owner, float x, float y, float width, float height, float initialAngle) {
        super(owner, x, y, width, height, 17, initialAngle, false);
        animator = new BatProjectileAnimator();
        animator.setDirection(body.getLinearVelocity().x > 0 ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        addOnExplosionAction(() -> animator.setState(ProjectileAnimator.State.EXPLODING));
    }

    /**
     * Returns the delay before the projectile is destroyed after its explosion.
     *
     * @return The destruction delay of the projectile after explosion.
     */
    @Override
    public float getDestructionDelay() {
        return 0.2f;
    }
}
