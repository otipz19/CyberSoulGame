package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.heroes.PunkHeroAnimator;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.attacks.concrete.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utils.Assets;

public class PunkHero extends Hero implements Disposable {
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
        shieldImpactSound= Assets.Sound.SHIELD_IMPACT_SOUND;
        deathSound=Assets.Sound.PUNK_DEATH_SOUND;
    }

    @Override
    public void dispose() {
        ((SideMeleeAttack)attack3).dispose();
        ((SideMeleeAttack)attack4).dispose();
    }
}
