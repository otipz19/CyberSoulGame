package com.mygdx.game.animation.concrete.projectiles;

import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;

public abstract class ProjectileAnimator extends Animator {
    public enum State implements Animator.State {
        FLYING,
        EXPLODING,
    }

    @Override
    protected abstract AnimationsMap createAnimationsMap();
}
