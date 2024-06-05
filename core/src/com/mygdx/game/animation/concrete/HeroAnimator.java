package com.mygdx.game.animation.concrete;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.utils.AssetsNames;

public class HeroAnimator extends Animator {
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

    protected AnimationsMap createAnimationsMap() {
        AnimationsMap animations = new AnimationsMap();
        animations.put(State.IDLE, new AnimationBuilder(AssetsNames.BIKER_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());
        animations.put(State.RUN, new AnimationBuilder(AssetsNames.BIKER_RUN_SHEET)
                .cols(6)
                .rows(1)
                .frameDuration(1 / 12f)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.put(State.JUMP, new AnimationBuilder(AssetsNames.BIKER_JUMP_SHEET)
                .cols(1)
                .rows(4)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.ATTACK_1, new AnimationBuilder(AssetsNames.BIKER_ATTACK1_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.ATTACK_2, new AnimationBuilder(AssetsNames.BIKER_ATTACK2_SHEET)
                .cols(8)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.CLIMB, new AnimationBuilder(AssetsNames.BIKER_CLIMB_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.put(State.DOUBLE_JUMP, new AnimationBuilder(AssetsNames.BIKER_DOUBLEJUMP_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.HURT, new AnimationBuilder(AssetsNames.BIKER_HURT_SHEET)
                .cols(3)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.PUNCH, new AnimationBuilder(AssetsNames.BIKER_PUNCH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.RUN_ATTACK, new AnimationBuilder(AssetsNames.BIKER_RUN_ATTACK_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .frameDuration(1 / 12f)
                .build());
        animations.put(State.DASH, new AnimationBuilder(AssetsNames.BIKER_DASH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.DEATH, new AnimationBuilder(AssetsNames.BIKER_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.startAnimation = animations.get(State.IDLE);
        return animations;
    }
}
