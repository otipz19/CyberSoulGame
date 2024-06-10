package com.mygdx.game.entities.attacks.base;

import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.heroes.Hero;

public abstract class HeroMeleeAttack extends SideMeleeAttack {
    private final Hero hero;

    public HeroMeleeAttack(Hero hero, AttackZonePosition leftAttackZonePosition, AttackZonePosition rightAttackZonePosition) {
        super(hero, leftAttackZonePosition, rightAttackZonePosition);
        this.hero = hero;
    }

    protected float getDamageMultiplier() {
        return hero.getResourcesManager().getDamageMultiplier();
    }

    @Override
    public float getAttackInterval() {
        return 0f;
    }
}
