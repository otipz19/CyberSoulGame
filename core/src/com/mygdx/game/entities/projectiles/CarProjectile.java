package com.mygdx.game.entities.projectiles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.projectiles.BatProjectileAnimator;
import com.mygdx.game.animation.concrete.projectiles.CarProjectileAnimator;
import com.mygdx.game.animation.concrete.projectiles.ProjectileAnimator;
import com.mygdx.game.entities.Entity;

public class CarProjectile extends EnemyProjectile {
    public CarProjectile(Entity owner, float x, float y, float width, float height, float initialAngle) {
        super(owner, x, y, width, height, 20, initialAngle, false);
        animator = new CarProjectileAnimator();
        animator.setDirection(body.getLinearVelocity().x > 0 ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        addOnExplosionAction(() -> animator.setState(ProjectileAnimator.State.EXPLODING));
    }

    @Override
    public float getDestructionDelay() {
        return 0.2f;
    }
}