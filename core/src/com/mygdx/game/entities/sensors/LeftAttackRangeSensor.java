package com.mygdx.game.entities.sensors;

import com.mygdx.game.entities.Entity;

public class LeftAttackRangeSensor extends AttackRangeSensor {
    public LeftAttackRangeSensor(Entity parent) {
        super(parent, SensorPosition.LEFT);
    }
}
