package com.mygdx.game.entities.attacks.base;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.attacks.AttackZone;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.utils.DelayedAction;

public abstract class SideMeleeAttack extends SideAttack implements MeleeAttack, ICollisionListener, Disposable {

    protected final Entity attacker;
    protected final AttackZone leftAttackZone;
    protected final AttackZone rightAttackZone;

    public SideMeleeAttack(Entity attacker, AttackZonePosition leftAttackZonePosition, AttackZonePosition rightAttackZonePosition) {
        this.attacker = attacker;
        leftAttackZone = new AttackZone(attacker, leftAttackZonePosition, getAttackWidth(), getAttackHeight());
        leftAttackZone.setAttackHandler(this);
        rightAttackZone = new AttackZone(attacker, rightAttackZonePosition, getAttackWidth(), getAttackHeight());
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

    @Override
    public void dispose() {
        rightAttackZone.dispose();
        leftAttackZone.dispose();
    }
}
