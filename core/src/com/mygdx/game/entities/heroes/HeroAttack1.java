package com.mygdx.game.entities.heroes;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.sensors.AttackZonePosition;

public class HeroAttack1 extends HeroSideAttack  {
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
    public void onCollisionEnter(Entity other) {
        System.out.println("Attack 1");
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
