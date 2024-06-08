package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
import com.mygdx.game.entities.attacks.AttackZonePosition;

public class MonsterAttack extends SideMeleeAttack {
    public MonsterAttack(Enemy attacker){
        super(attacker, AttackZonePosition.LEFT_MIDDLE, AttackZonePosition.RIGHT_MIDDLE);
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return 0.09f;
    }

    @Override
    public float getAttackWidth() {
        return 0.3f;
    }

    @Override
    public float getAttackHeight() {
        return attacker.getHeight();
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Hero hero)
            hero.addResourcesEffect(new AbsoluteInstantDamageEffect<>(40));
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
