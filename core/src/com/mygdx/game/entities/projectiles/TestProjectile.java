package com.mygdx.game.entities.projectiles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.projectiles.TestProjectileAnimator;
import com.mygdx.game.entities.Entity;

public class TestProjectile extends HeroProjectile {
    public TestProjectile(Entity owner, float x, float y, float width, float height, float initialAngle) {
        super(owner, x, y, width, height, 12, initialAngle, false);
        animator = new TestProjectileAnimator();
        animator.setDirection(body.getLinearVelocity().x > 0 ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        addOnExplosionAction(() -> animator.setState(TestProjectileAnimator.State.EXPLODING));
    }

    @Override
    public float getDestructionDelay() {
        return 0.3f;
    }
}
