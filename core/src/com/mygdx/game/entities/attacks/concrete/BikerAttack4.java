package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.base.HeroMeleeAttack;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
import com.mygdx.game.entities.attacks.AttackZonePosition;
/**
 * BatAttack represents a specific ranged attack where a bat projectile is launched from an entity,
 * targeting enemies within the game world.
 */
public class BikerAttack4 extends HeroMeleeAttack {
    public BikerAttack4(Hero hero){
        super(hero, AttackZonePosition.LEFT_MIDDLE, AttackZonePosition.RIGHT_MIDDLE);
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return 0f;
    }

    @Override
    public float getAttackWidth() {
        return 0.3f;
    }

    @Override
    public float getAttackHeight() {
        return 1f;
    }

    @Override
    public float getEnergyConsumption() {
        return 30f;
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Enemy enemy)
            enemy.addResourcesEffect(new AbsoluteInstantDamageEffect<>(25 * getDamageMultiplier()));
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
