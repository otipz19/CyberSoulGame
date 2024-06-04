package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.utils.AssetsNames;

public class EnemyAnimator extends Animator {
    public enum State implements Animator.State {
        IDLE,
        ATTACK_1,
        ATTACK_2,
        ATTACK_3,
        DEATH,
        HURT,
        WALK
    }
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap animations = new AnimationsMap();
        animations.put(State.IDLE, new AnimationBuilder(AssetsNames.MONSTER_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());
        animations.put(State.ATTACK_1, new AnimationBuilder(AssetsNames.MONSTER_ATTACK1_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.put(State.ATTACK_2, new AnimationBuilder(AssetsNames.MONSTER_ATTACK2_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.put(State.ATTACK_3, new AnimationBuilder(AssetsNames.MONSTER_ATTACK3_SHEET)
                .cols(5)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.put(State.HURT, new AnimationBuilder(AssetsNames.MONSTER_HURT_SHEET)
                .cols(3)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.DEATH, new AnimationBuilder(AssetsNames.MONSTER_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.WALK, new AnimationBuilder(AssetsNames.MONSTER_WALK_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.startAnimation = animations.get(State.IDLE);
        return animations;
    }
}
