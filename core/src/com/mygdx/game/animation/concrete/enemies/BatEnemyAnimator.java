package com.mygdx.game.animation.concrete.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.utils.Assets;

/**
 * Animator for the Bat enemy. Creates and manages animations for different states of the Bat enemy.
 */
public class BatEnemyAnimator extends EnemyAnimator {

    /**
     * Creates and returns an AnimationsMap containing all the animations for the Bat enemy.
     * Initializes the animations for various states such as IDLE, ATTACK_1, ATTACK_2, ATTACK_3, HURT, DEATH, and WALK.
     *
     * @return the AnimationsMap with the Bat enemy animations
     */
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap animations = new AnimationsMap();
        animations.put(EnemyAnimator.State.IDLE, new AnimationBuilder(Assets.Textures.BAT_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());
        animations.put(EnemyAnimator.State.ATTACK_1, new AnimationBuilder(Assets.Textures.BAT_ATTACK1_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .priority(500)
                .build());
        animations.put(EnemyAnimator.State.ATTACK_2, new AnimationBuilder(Assets.Textures.BAT_ATTACK2_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .priority(500)
                .build());
        animations.put(EnemyAnimator.State.ATTACK_3, new AnimationBuilder(Assets.Textures.BAT_ATTACK3_SHEET)
                .cols(5)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .priority(500)
                .build());
        animations.put(EnemyAnimator.State.HURT, new AnimationBuilder(Assets.Textures.BAT_HURT_SHEET)
                .cols(2)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .highPriority()
                .build());
        animations.put(EnemyAnimator.State.DEATH, new AnimationBuilder(Assets.Textures.BAT_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .veryHighPriority()
                .build());
        animations.put(EnemyAnimator.State.WALK, new AnimationBuilder(Assets.Textures.BAT_WALK_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.startAnimation = animations.get(EnemyAnimator.State.IDLE);
        return animations;
    }
}
