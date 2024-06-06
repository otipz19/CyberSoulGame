package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.InstantDamageEffect;
import com.mygdx.game.entities.attacks.AttackZonePosition;

public class BikerAttack3 extends SideMeleeAttack {
    public BikerAttack3(Hero hero){
        super(hero, AttackZonePosition.LEFT_BOTTOM, AttackZonePosition.RIGHT_BOTTOM);
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return  0.2f;
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
    public float getEnergyConsumption() {
        return 15f;
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Enemy enemy)
            enemy.addResourcesEffect(new InstantDamageEffect<>(30));
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
