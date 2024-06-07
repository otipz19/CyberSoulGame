package com.mygdx.game.animation.concrete.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.utils.Assets;

public class PunkScytheProjectileAnimator extends ProjectileAnimator {
    @Override
    protected AnimationsMap createAnimationsMap() {
        var animations = new AnimationsMap();
        animations.put(State.FLYING, new AnimationBuilder(Assets.Textures.PUNK_SCYTHEPROJECTILE_FLYING_SPRITESHEET)
                .rows(1)
                .cols(1)
                .frameDuration(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.EXPLODING, new AnimationBuilder(Assets.Textures.PUNK_SCYTHEPROJECTILE_EXPLODING_SPRITESHEET)
                .rows(1)
                .cols(2)
                .playMode(Animation.PlayMode.NORMAL)
                .veryHighPriority()
                .build());
        animations.startAnimation = animations.get(State.FLYING);
        return animations;
    }
}
