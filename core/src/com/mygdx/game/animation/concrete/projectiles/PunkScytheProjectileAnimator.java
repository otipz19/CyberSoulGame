package com.mygdx.game.animation.concrete.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.utils.Assets;

/**
 * Animator class specifically for the punk scythe projectile.
 * Inherits from ProjectileAnimator.
 */
public class PunkScytheProjectileAnimator extends ProjectileAnimator {

    /**
     * Creates and initializes a map of animations for the punk scythe projectile.
     *
     * @return AnimationsMap containing animations for different states of the punk scythe projectile.
     */
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
