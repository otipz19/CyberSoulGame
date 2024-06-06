package com.mygdx.game.entities.projectiles;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.projectiles.TestProjectileAnimator;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.resources.InstantDamageEffect;

public class TestProjectile extends HeroProjectile {
    public TestProjectile(Entity owner, float x, float y, float initialAngle) {
        super(owner, x, y, 0.5f, 0.25f, 12, initialAngle, false);
        animator = new TestProjectileAnimator();

        addOnExplosionAction(() -> {
                animator.setDirection(body.getLinearVelocity().x > 0 ? Animator.Direction.RIGHT : Animator.Direction.LEFT);
                animator.setState(TestProjectileAnimator.State.EXPLODING)
            ;});
    }

    @Override
    public float getDestructionDelay() {
        return 0.3f;
    }
}
