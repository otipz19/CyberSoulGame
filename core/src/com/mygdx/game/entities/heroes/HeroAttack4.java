package com.mygdx.game.entities.heroes;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.sensors.AttackZonePosition;

public class HeroAttack4 extends HeroSideAttack  {
    public HeroAttack4(Hero hero){
        super(hero, AttackZonePosition.LEFT_MIDDLE, AttackZonePosition.RIGHT_MIDDLE);
    }

    @Override
    public float getAttackTime() {
        return 0.5f;
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
    public void onCollisionEnter(Entity other) {
        System.out.println("Attack 4");
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
