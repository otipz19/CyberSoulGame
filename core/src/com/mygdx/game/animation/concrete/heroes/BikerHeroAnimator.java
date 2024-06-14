package com.mygdx.game.animation.concrete.heroes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.utils.Assets;

/**
 * Animator class specifically for a biker hero character.
 */
public class BikerHeroAnimator extends HeroAnimator {

    /**
     * Creates and initializes a map of animations for the biker hero character.
     *
     * @return AnimationsMap containing all animations for different states of the biker hero.
     */
    @Override
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap animations = new AnimationsMap();

        animations.put(State.IDLE, new AnimationBuilder(Assets.Textures.BIKER_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());

        animations.put(State.RUN, new AnimationBuilder(Assets.Textures.BIKER_RUN_SHEET)
                .cols(6)
                .rows(1)
                .frameDuration(1 / 12f)
                .playMode(Animation.PlayMode.LOOP)
                .build());

        animations.put(State.JUMP, new AnimationBuilder(Assets.Textures.BIKER_JUMP_SHEET)
                .cols(1)
                .rows(4)
                .playMode(Animation.PlayMode.NORMAL)
                .build());

        animations.put(State.ATTACK_1, new AnimationBuilder(Assets.Textures.BIKER_ATTACK1_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .priority(500)
                .build());
        animations.put(State.ATTACK_2, new AnimationBuilder(Assets.Textures.BIKER_ATTACK2_SHEET)
                .cols(8)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .priority(500)
                .build());

        animations.put(State.CLIMB, new AnimationBuilder(Assets.Textures.BIKER_CLIMB_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .build());

        animations.put(State.DOUBLE_JUMP, new AnimationBuilder(Assets.Textures.BIKER_DOUBLEJUMP_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());

        animations.put(State.HURT, new AnimationBuilder(Assets.Textures.BIKER_HURT_SHEET)
                .cols(3)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .highPriority()
                .build());

        animations.put(State.PUNCH, new AnimationBuilder(Assets.Textures.BIKER_PUNCH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .priority(500)
                .build());

        animations.put(State.RUN_ATTACK, new AnimationBuilder(Assets.Textures.BIKER_RUN_ATTACK_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.LOOP)
                .frameDuration(1 / 12f)
                .build());

        animations.put(State.DASH, new AnimationBuilder(Assets.Textures.BIKER_DASH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.RUN)
                .priority(500)
                .build());

        animations.put(State.DEATH, new AnimationBuilder(Assets.Textures.BIKER_DEATH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .veryHighPriority()
                .build());

        animations.startAnimation = animations.get(State.IDLE);

        return animations;
    }
}
