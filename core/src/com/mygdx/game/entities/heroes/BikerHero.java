package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.heroes.BikerHeroAnimator;
import com.mygdx.game.animation.concrete.heroes.HeroAnimator;
import com.mygdx.game.entities.attacks.base.Attack;
import com.mygdx.game.entities.attacks.base.SideAttack;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.attacks.concrete.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.DelayedAction;

public class BikerHero extends Hero implements Disposable {
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
        shieldImpactSound= Assets.Sound.SHIELD_IMPACT_SOUND;
        deathSound=Assets.Sound.BIKER_DEATH_SOUND;
    }

    @Override
    protected void attack(Attack attack, String soundName, HeroAnimator.State animation) {
        SideAttack sideMeleeAttack = (SideAttack)attack;
        if (resourcesManager.tryConsumeEnergy(attack.getEnergyConsumption())) {
            animator.setState(animation);
            new DelayedAction(sideMeleeAttack.getAttackDelay(), () -> SoundPlayer.getInstance().playSound(soundName));
            attackDelay = sideMeleeAttack.getAttackTime();
            sideMeleeAttack.setDirection(movementController.isFacingRight());
            sideMeleeAttack.execute();
        }
    }

    @Override
    public void dispose() {
        ((SideMeleeAttack)attack1).dispose();
        ((SideMeleeAttack)attack2).dispose();
        ((SideMeleeAttack)attack3).dispose();
        ((SideMeleeAttack)attack4).dispose();
    }
}
