package com.mygdx.game.animation.concrete.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.utils.Assets;

/**
 * Animator class specifically for the Punk S Projectile.
 * Inherits from ProjectileAnimator.
 */
public class PunkSProjectileAnimator extends ProjectileAnimator {

    /**
     * Creates and initializes a map of animations for the Punk S Projectile.
     *
     * @return AnimationsMap containing animations for different states of the Punk S Projectile.
     */
    @Override
    protected AnimationsMap createAnimationsMap() {
        var animations = new AnimationsMap();

        animations.put(State.FLYING, new AnimationBuilder(Assets.Textures.PUNK_SPROJECTILE_FLYING_SPRITESHEET)
                .rows(1)
                .cols(4)
                .frameDuration(1 / 12f)
                .playMode(Animation.PlayMode.LOOP)
                .build());

        animations.put(State.EXPLODING, new AnimationBuilder(Assets.Textures.PUNK_SPROJECTILE_EXPLODING_SPRITESHEET)
                .rows(1)
                .cols(3)
                .playMode(Animation.PlayMode.NORMAL)
                .veryHighPriority()
                .build());

        animations.startAnimation = animations.get(State.FLYING);

        return animations;
    }
}
