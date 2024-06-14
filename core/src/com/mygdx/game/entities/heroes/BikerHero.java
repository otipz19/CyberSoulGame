package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.heroes.BikerHeroAnimator;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.attacks.concrete.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utils.Assets;
/**
 * Represents the BikerHero, a specific type of hero character in the game.
 * Extends {@link Hero} and implements {@link Disposable} for resource management.
 */
public class BikerHero extends Hero implements Disposable {

    /**
     * Constructs a BikerHero object with the specified parameters.
     *
     * @param level The level in which the hero exists.
     * @param heroData Data defining specific characteristics of this hero type.
     * @param x The initial x-coordinate position of the hero.
     * @param y The initial y-coordinate position of the hero.
     * @param width The width of the hero.
     * @param height The height of the hero.
     */
    public BikerHero(Level level, HeroData heroData, float x, float y, float width, float height) {
        super(level, heroData, x, y, width, height);

        this.animator = new BikerHeroAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        attack1 = new BikerAttack1(this);
        attack2 = new BikerAttack2(this);
        attack3 = new BikerAttack3(this);
        attack4 = new BikerAttack4(this);

        attack1Sound = Assets.Sound.BIKER_ATTACK1_SOUND;
        attack2Sound = Assets.Sound.BIKER_ATTACK2_SOUND;
        attack3Sound = Assets.Sound.BIKER_ATTACK3_SOUND;
        hpLossSound = Assets.Sound.BIKER_HURT_SOUND;
        deathSound = Assets.Sound.BIKER_DEATH_SOUND;
    }

    /**
     * Disposes of resources used by the BikerHero, including attacks.
     * Implements {@link Disposable#dispose()}.
     */
    @Override
    public void dispose() {
        ((SideMeleeAttack) attack1).dispose();
        ((SideMeleeAttack) attack2).dispose();
        ((SideMeleeAttack) attack3).dispose();
        ((SideMeleeAttack) attack4).dispose();
    }
}
