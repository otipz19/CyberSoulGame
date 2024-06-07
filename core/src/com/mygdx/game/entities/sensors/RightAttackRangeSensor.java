package com.mygdx.game.entities.sensors;

import com.mygdx.game.entities.Entity;

public class RightAttackRangeSensor extends AttackRangeSensor {
    public RightAttackRangeSensor(Entity parent) {
        super(parent, SensorPosition.RIGHT);
    }
}
