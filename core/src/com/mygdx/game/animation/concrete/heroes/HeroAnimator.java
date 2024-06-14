package com.mygdx.game.animation.concrete.heroes;

import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;

/**
 * Abstract class representing an animator for a hero character.
 */
public abstract class HeroAnimator extends Animator {

    /**
     * Enumeration of possible states for the hero animator.
     */
    public enum State implements Animator.State {
        IDLE,
        RUN,
        JUMP,
        ATTACK_1,
        ATTACK_2,
        CLIMB,
        DEATH,
        DOUBLE_JUMP,
        HURT,
        PUNCH,
        RUN_ATTACK,
        DASH
    }

    /**
     * Abstract method to be implemented by subclasses to create and initialize
     * a map of animations for the hero character.
     *
     * @return AnimationsMap containing animations for different states of the hero.
     */
    protected abstract AnimationsMap createAnimationsMap();
}
