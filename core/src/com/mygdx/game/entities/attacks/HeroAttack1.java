package com.mygdx.game.entities.attacks;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.InstantDamageEffect;
import com.mygdx.game.entities.resources.ResourcesManager;
import com.mygdx.game.entities.sensors.AttackZonePosition;

public class HeroAttack1 extends SideAttack {
    public HeroAttack1(Hero hero){
        super(hero, AttackZonePosition.LEFT_TOP, AttackZonePosition.RIGHT_TOP);
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
    public float getEnergyConsumption() {
        return 10f;
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Enemy enemy)
            enemy.addResourcesEffect(new InstantDamageEffect<>(25));
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}