package com.mygdx.game.animation.concrete.projectiles;

import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
/**
 * Abstract class defining the behavior of projectile animators in the game.
 * Extends Animator and specifies the creation of animations for different states.
 */
public abstract class ProjectileAnimator extends Animator {
    public enum State implements Animator.State {
        FLYING,
        EXPLODING,
    }
    /**
     * Abstract method to be implemented by subclasses to create and initialize
     * animations for different states of the projectile.
     *
     * @return AnimationsMap containing animations for different states of the projectile.
     */
    @Override
    protected abstract AnimationsMap createAnimationsMap();
}
