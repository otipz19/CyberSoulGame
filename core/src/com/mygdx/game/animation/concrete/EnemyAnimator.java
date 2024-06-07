package com.mygdx.game.animation.concrete;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.utils.AssetsNames;

import java.util.Objects;

public class EnemyAnimator extends Animator {
    public enum EnemyType {
        MONSTER1,
        MONSTER2,
        MONSTER3
    }
    public static Animator createAnimator(EnemyType type) {
        switch (type) {
            case MONSTER1:
                return new Animator(createAnimationsMap1()) {
                    @Override
                    protected AnimationsMap createAnimationsMap() {
                        return null;
                    }
                };
            case MONSTER2:
                return new Animator(createAnimationsMap2()) {
                    @Override
                    protected AnimationsMap createAnimationsMap() {
                        return null;
                    }
                };
            case MONSTER3:
                return new Animator(createAnimationsMap3()) {
                    @Override
                    protected AnimationsMap createAnimationsMap() {
                        return null;
                    }
                };
            default:
                throw new IllegalArgumentException("Unknown EnemyType: " + type);
        }
    }
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
        return null;
    }
    protected static AnimationsMap createAnimationsMap1() {
        AnimationsMap animations = new AnimationsMap();
        animations.put(State.IDLE, new AnimationBuilder(AssetsNames.MONSTER_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());
        animations.put(State.ATTACK_1, new AnimationBuilder(AssetsNames.MONSTER2_ATTACK1_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.ATTACK_2, new AnimationBuilder(AssetsNames.MONSTER2_ATTACK2_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.ATTACK_3, new AnimationBuilder(AssetsNames.MONSTER2_ATTACK3_SHEET)
                .cols(5)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.HURT, new AnimationBuilder(AssetsNames.MONSTER2_HURT_SHEET)
                .cols(2)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .highPriority()
                .build());
        animations.put(State.DEATH, new AnimationBuilder(AssetsNames.MONSTER2_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .veryHighPriority()
                .build());
        animations.put(State.WALK, new AnimationBuilder(AssetsNames.MONSTER2_WALK_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.startAnimation = animations.get(State.IDLE);
        return animations;
    }
    protected static AnimationsMap createAnimationsMap2() {
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
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.ATTACK_2, new AnimationBuilder(AssetsNames.MONSTER_ATTACK2_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.ATTACK_3, new AnimationBuilder(AssetsNames.MONSTER_ATTACK3_SHEET)
                .cols(5)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.HURT, new AnimationBuilder(AssetsNames.MONSTER_HURT_SHEET)
                .cols(2)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .highPriority()
                .build());
        animations.put(State.DEATH, new AnimationBuilder(AssetsNames.MONSTER_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .veryHighPriority()
                .build());
        animations.put(State.WALK, new AnimationBuilder(AssetsNames.MONSTER_WALK_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.startAnimation = animations.get(State.IDLE);
        return animations;
    }
    protected static AnimationsMap createAnimationsMap3() {
        AnimationsMap animations = new AnimationsMap();
        animations.put(State.IDLE, new AnimationBuilder(AssetsNames.MONSTER3_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());
        animations.put(State.ATTACK_1, new AnimationBuilder(AssetsNames.MONSTER3_ATTACK1_SHEET)
                .cols(4)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.ATTACK_2, new AnimationBuilder(AssetsNames.MONSTER3_ATTACK2_SHEET)
                .cols(4)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.ATTACK_3, new AnimationBuilder(AssetsNames.MONSTER3_ATTACK3_SHEET)
                .cols(4)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .priority(500)
                .build());
        animations.put(State.HURT, new AnimationBuilder(AssetsNames.MONSTER3_HURT_SHEET)
                .cols(2)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.WALK)
                .highPriority()
                .build());
        animations.put(State.DEATH, new AnimationBuilder(AssetsNames.MONSTER3_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .veryHighPriority()
                .build());
        animations.put(State.WALK, new AnimationBuilder(AssetsNames.MONSTER3_WALK_SHEET)
                .cols(4)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.startAnimation = animations.get(State.IDLE);
        return animations;
    }
}
