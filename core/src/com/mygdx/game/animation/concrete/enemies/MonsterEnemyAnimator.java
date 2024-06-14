package com.mygdx.game.animation.concrete.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.utils.Assets;

/**
 * Animator class specifically for a monster enemy.
 */
public class MonsterEnemyAnimator extends EnemyAnimator {

    /**
     * Creates and initializes a map of animations for the monster enemy.
     *
     * @return AnimationsMap containing all animations for different states of the monster enemy.
     */
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap animations = new AnimationsMap();

        // Idle animation
        animations.put(EnemyAnimator.State.IDLE, new AnimationBuilder(Assets.Textures.MONSTER_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());

        // Attack animations
        animations.put(EnemyAnimator.State.ATTACK_1, new AnimationBuilder(Assets.Textures.MONSTER_ATTACK1_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .priority(500)
                .build());
        animations.put(EnemyAnimator.State.ATTACK_2, new AnimationBuilder(Assets.Textures.MONSTER_ATTACK2_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .priority(500)
                .build());
        animations.put(EnemyAnimator.State.ATTACK_3, new AnimationBuilder(Assets.Textures.MONSTER_ATTACK3_SHEET)
                .cols(5)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .priority(500)
                .build());

        // Hurt animation
        animations.put(EnemyAnimator.State.HURT, new AnimationBuilder(Assets.Textures.MONSTER_HURT_SHEET)
                .cols(2)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(EnemyAnimator.State.WALK)
                .highPriority()
                .build());

        // Death animation
        animations.put(EnemyAnimator.State.DEATH, new AnimationBuilder(Assets.Textures.MONSTER_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .veryHighPriority()
                .build());

        // Walk animation
        animations.put(EnemyAnimator.State.WALK, new AnimationBuilder(Assets.Textures.MONSTER_WALK_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());

        // Set starting animation
        animations.startAnimation = animations.get(EnemyAnimator.State.IDLE);

        return animations;
    }
}
