package com.mygdx.game.animation.concrete.enemies;


import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
public abstract class EnemyAnimator extends Animator {
    public enum State implements Animator.State {
        IDLE,
        ATTACK_1,
        ATTACK_2,
        ATTACK_3,
        DEATH,
        HURT,
        WALK
    }
    protected abstract AnimationsMap createAnimationsMap();
}
