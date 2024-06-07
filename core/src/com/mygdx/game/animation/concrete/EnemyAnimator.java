package com.mygdx.game.animation.concrete;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.utils.AssetsNames;

import java.util.Objects;

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
