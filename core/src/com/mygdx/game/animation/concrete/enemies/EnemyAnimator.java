package com.mygdx.game.animation.concrete.enemies;

import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;

/**
 * Abstract base class for enemy animators. Manages animations for different states of enemies.
 */
public abstract class EnemyAnimator extends Animator {

    /**
     * Enumeration of the various states an enemy can be in. Each state corresponds to a specific animation.
     */
    public enum State implements Animator.State {
        IDLE,
        ATTACK_1,
        ATTACK_2,
        ATTACK_3,
        DEATH,
        HURT,
        WALK
    }

    /**
     * Creates and returns an AnimationsMap containing all the animations for the enemy.
     * This method must be implemented by subclasses to define the specific animations for each enemy state.
     *
     * @return the AnimationsMap with the enemy animations
     */
    protected abstract AnimationsMap createAnimationsMap();
}
