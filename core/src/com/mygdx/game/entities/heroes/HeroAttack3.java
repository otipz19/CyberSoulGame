package com.mygdx.game.entities.heroes;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.sensors.AttackZonePosition;

public class HeroAttack3 extends HeroSideAttack  {
    public HeroAttack3(Hero hero){
        super(hero, AttackZonePosition.LEFT_BOTTOM, AttackZonePosition.RIGHT_BOTTOM);
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return  0.1f;
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
        System.out.println("Attack 3");
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
