package com.mygdx.game.entities.projectiles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.projectiles.BatProjectileAnimator;
import com.mygdx.game.animation.concrete.projectiles.ProjectileAnimator;
import com.mygdx.game.animation.concrete.projectiles.PunkScytheProjectileAnimator;
import com.mygdx.game.entities.Entity;

public class BatProjectile extends EnemyProjectile {
    public BatProjectile(Entity owner, float x, float y, float width, float height, float initialAngle) {
        super(owner, x, y, width, height, 17, initialAngle, false);
        animator = new BatProjectileAnimator();
        animator.setDirection(body.getLinearVelocity().x > 0 ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        addOnExplosionAction(() -> animator.setState(ProjectileAnimator.State.EXPLODING));
    }

    @Override
    public float getDestructionDelay() {
        return 0.2f;
    }
}
