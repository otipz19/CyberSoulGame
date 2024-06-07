package com.mygdx.game.entities.projectiles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.projectiles.PunkSProjectileAnimator;
import com.mygdx.game.entities.Entity;

public class PunkSProjectile extends HeroProjectile {
    public PunkSProjectile(Entity owner, float x, float y, float width, float height, float initialAngle) {
        super(owner, x, y, width, height, 12, initialAngle, false);
        animator = new PunkSProjectileAnimator();
        animator.setDirection(body.getLinearVelocity().x > 0 ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        addOnExplosionAction(() -> animator.setState(PunkSProjectileAnimator.State.EXPLODING));
    }

    @Override
    public float getDestructionDelay() {
        return 0.3f;
    }
}
