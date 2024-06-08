package com.mygdx.game.animation.concrete.heroes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.utils.Assets;

public class PunkHeroAnimator extends HeroAnimator {
    @Override
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap animations = new AnimationsMap();
        animations.put(State.IDLE, new AnimationBuilder(Assets.Textures.PUNK_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());
        animations.put(State.RUN, new AnimationBuilder(Assets.Textures.PUNK_RUN_SHEET)
                .cols(3)
                .rows(2)
                .frameDuration(1 / 12f)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.put(State.JUMP, new AnimationBuilder(Assets.Textures.PUNK_JUMP_SHEET)
                .cols(4)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.ATTACK_1, new AnimationBuilder(Assets.Textures.PUNK_ATTACK1_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .priority(500)
                .build());
        animations.put(State.ATTACK_2, new AnimationBuilder(Assets.Textures.PUNK_ATTACK2_SHEET)
                .cols(8)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .priority(500)
                .build());
        animations.put(State.CLIMB, new AnimationBuilder(Assets.Textures.PUNK_CLIMB_SHEET)
                .cols(3)
                .rows(2)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.put(State.DOUBLE_JUMP, new AnimationBuilder(Assets.Textures.PUNK_DOUBLEJUMP_SHEET)
                .cols(5)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.HURT, new AnimationBuilder(Assets.Textures.PUNK_HURT_SHEET)
                .cols(2)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .highPriority()
                .build());
        animations.put(State.PUNCH, new AnimationBuilder(Assets.Textures.PUNK_PUNCH_SHEET)
                .cols(6)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .priority(500)
                .build());
        animations.put(State.RUN_ATTACK, new AnimationBuilder(Assets.Textures.PUNK_RUN_ATTACK_SHEET)
                .cols(3)
                .rows(2)
                .playMode(Animation.PlayMode.LOOP)
                .frameDuration(1 / 12f)
                .build());
        animations.put(State.DASH, new AnimationBuilder(Assets.Textures.PUNK_DASH_SHEET)
                .cols(3)
                .rows(2)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.RUN)
                .priority(500)
                .build());
        animations.put(State.DEATH, new AnimationBuilder(Assets.Textures.PUNK_DEATH_SHEET)
                .cols(7)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .frameDuration(1 / 12f)
                .veryHighPriority()
                .build());
        animations.startAnimation = animations.get(State.IDLE);
        return animations;
    }
}
