package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.heroes.Hero;

public class AttackRangeSensor extends Sensor {
    private boolean isInRange;

    public AttackRangeSensor(Entity parent, AttackZonePosition sensorPosition, float width, float height) {
        super(parent);
        Shape shape = sensorPosition.getColliderShape(parent.getWidth(), parent.getHeight(), width, height);
        createFixture(shape);
    }

    public boolean isHeroInRange() {
        return isInRange;
    }

    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof Hero) {
            isInRange = true;
        }
    }

    @Override
    public void onTriggerExit(GameObject other) {
        if(other instanceof Hero) {
            isInRange = false;
        }
    }
}
