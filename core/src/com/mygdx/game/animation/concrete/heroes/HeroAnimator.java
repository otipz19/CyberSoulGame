package com.mygdx.game.animation.concrete.heroes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.utils.AssetsNames;

public abstract class HeroAnimator extends Animator {
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

    protected abstract AnimationsMap createAnimationsMap();
}
