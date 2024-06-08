package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;

public class CarAttack extends SideMeleeAttack {
    public CarAttack(Enemy enemy){
        super(enemy, AttackZonePosition.LEFT, AttackZonePosition.RIGHT);
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return  0.09f;
    }

    @Override
    public float getAttackWidth() {
        return 0.3f;
    }

    @Override
    public float getAttackHeight() {
        return 0.6f;
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Hero hero)
            hero.addResourcesEffect(new AbsoluteInstantDamageEffect<>(25));
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
