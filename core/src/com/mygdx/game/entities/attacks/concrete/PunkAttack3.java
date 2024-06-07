package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;

import java.util.function.Consumer;

public class PunkAttack3 extends SideMeleeAttack {
    private final static float ATTACK_IMPULSE = 40f;
    private Hero hero;
    public PunkAttack3(Hero hero){
        super(hero, AttackZonePosition.LEFT_BOTTOM, AttackZonePosition.RIGHT_BOTTOM);
        this.hero = hero;
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return  0.25f;
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
        return 20f;
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Enemy enemy) {
            enemy.addResourcesEffect(new AbsoluteInstantDamageEffect<>(10));
            enemy.addOnHealthChangeAction(d -> {
                if (hero.getMovementController().isFacingRight())
                    enemy.getMovementController().applyImpulse(ATTACK_IMPULSE, 0f);
                else
                    enemy.getMovementController().applyImpulse(-ATTACK_IMPULSE, 0f);
                return true;
            });
        }
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
