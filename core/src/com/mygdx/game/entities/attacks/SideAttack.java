package com.mygdx.game.entities.attacks;

import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.sensors.AttackZone;
import com.mygdx.game.entities.sensors.AttackZonePosition;
import com.mygdx.game.utils.DelayedAction;

public abstract class SideAttack implements Attack {
    protected final AttackZone leftAttackZone;
    protected final AttackZone rightAttackZone;
    protected boolean facingRight;

    public SideAttack(Hero hero, AttackZonePosition leftAttackZonePosition, AttackZonePosition rightAttackZonePosition){
        leftAttackZone = new AttackZone(hero, leftAttackZonePosition, getAttackWidth(), getAttackHeight());
        leftAttackZone.setAttackHandler(this);
        rightAttackZone = new AttackZone(hero, rightAttackZonePosition, getAttackWidth(), getAttackHeight());
        rightAttackZone.setAttackHandler(this);
    }

    public void execute(){
        AttackZone attackZone;
        if (facingRight)
            attackZone = rightAttackZone;
        else
            attackZone = leftAttackZone;
        new DelayedAction(getAttackDelay(), () -> attackZone.enable(getAttackTime()-getAttackDelay()));
    }

    public void setDirection(boolean facingRight) {
        this.facingRight = facingRight;
    }

    @Override
    public void dispose() {
        rightAttackZone.dispose();
        leftAttackZone.dispose();
    }
}
