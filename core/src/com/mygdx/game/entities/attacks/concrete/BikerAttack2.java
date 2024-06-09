package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.base.HeroMeleeAttack;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
import com.mygdx.game.entities.attacks.AttackZonePosition;

public class BikerAttack2 extends HeroMeleeAttack {
    public BikerAttack2(Hero hero){
        super(hero, AttackZonePosition.LEFT_TOP, AttackZonePosition.RIGHT_TOP);
    }

    @Override
    public float getAttackTime() {
        return 0.8f;
    }

    @Override
    public float getAttackDelay() {
        return  0.3f;
    }

    @Override
    public float getAttackWidth() {
        return 0.4f;
    }

    @Override
    public float getAttackHeight() {
        return 0.6f;
    }

    @Override
    public float getEnergyConsumption() {
        return 20f;
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Enemy enemy)
            enemy.addResourcesEffect(new AbsoluteInstantDamageEffect<>(20 * getDamageMultiplier()));
    }

    @Override
    public void onCollisionExit(Entity other) {
        if (other instanceof Enemy enemy)
            enemy.addResourcesEffect(new AbsoluteInstantDamageEffect<>(40));
    }
}
