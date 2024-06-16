package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.heroes.PunkHeroAnimator;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.attacks.concrete.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utils.Assets;

/**
 * Represents a Punk Hero character in the game, extending the Hero class and implementing the Disposable interface.
 * Manages animations and attacks specific to the Punk Hero.
 */
public class PunkHero extends Hero implements Disposable {

    /**
     * Constructs a PunkHero object.
     *
     * @param level    The Level object in which the hero exists.
     * @param heroData The data defining initial attributes of the hero.
     * @param x        The initial x-coordinate of the hero's position.
     * @param y        The initial y-coordinate of the hero's position.
     * @param width    The width of the hero.
     * @param height   The height of the hero.
     */
    public PunkHero(Level level, HeroData heroData, float x, float y, float width, float height) {
        super(level, heroData, x, y, width, height);

        this.animator = new PunkHeroAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        attack1 = new PunkAttack1(this);
        attack2 = new PunkAttack2(this);
        attack3 = new PunkAttack3(this);
        attack4 = new PunkAttack4(this);

        attack1Sound = Assets.Sound.PUNK_ATTACK1_SOUND;
        attack2Sound = Assets.Sound.PUNK_ATTACK2_SOUND;
        attack3Sound = Assets.Sound.PUNK_ATTACK3_SOUND;
        hpLossSound = Assets.Sound.PUNK_HURT_SOUND;
        deathSound = Assets.Sound.PUNK_DEATH_SOUND;
    }

    /**
     * Disposes resources held by the PunkHero instance.
     * Specifically disposes of attack3 and attack4, which are instances of SideMeleeAttack.
     */
    @Override
    public void dispose() {
        ((SideMeleeAttack) attack3).dispose();
        ((SideMeleeAttack) attack4).dispose();
    }
}
