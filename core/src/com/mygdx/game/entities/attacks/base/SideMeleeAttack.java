package com.mygdx.game.entities.attacks.base;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.attacks.AttackZone;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.utils.DelayedAction;

/**
 * SideMeleeAttack is an abstract class representing a melee attack in a side-scrolling context.
 * It manages attack zones on both sides of an entity and handles execution and disposal of the attack.
 */
public abstract class SideMeleeAttack extends SideAttack implements MeleeAttack, ICollisionListener, Disposable {

    protected final Entity attacker;

    protected final AttackZone leftAttackZone;

    protected final AttackZone rightAttackZone;

    /**
     * Constructs a SideMeleeAttack with the specified attacker and attack zone positions.
     *
     * @param attacker The entity performing the attack.
     * @param leftAttackZonePosition Position of the left attack zone relative to the attacker.
     * @param rightAttackZonePosition Position of the right attack zone relative to the attacker.
     */
    public SideMeleeAttack(Entity attacker, AttackZonePosition leftAttackZonePosition, AttackZonePosition rightAttackZonePosition) {
        this.attacker = attacker;
        leftAttackZone = new AttackZone(attacker, leftAttackZonePosition, getAttackWidth(), getAttackHeight());
        leftAttackZone.setAttackHandler(this);
        rightAttackZone = new AttackZone(attacker, rightAttackZonePosition, getAttackWidth(), getAttackHeight());
        rightAttackZone.setAttackHandler(this);
    }

    /**
     * Executes the melee attack by enabling the appropriate attack zone based on the direction.
     * Delays the activation of the attack zone by the attack delay duration.
     */
    public void execute(){
        AttackZone attackZone;
        if (facingRight)
            attackZone = rightAttackZone;
        else
            attackZone = leftAttackZone;
        attacker.getLevel().addDelayedAction(getAttackDelay(), () -> attackZone.enable(getAttackTime() - getAttackDelay()));
    }

    /**
     * Disposes of the attack zones to free resources when the attack is no longer needed.
     */
    @Override
    public void dispose() {
        rightAttackZone.dispose();
        leftAttackZone.dispose();
    }
}
