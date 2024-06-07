package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.heroes.Hero;

public class DefaultEnemyHeadSensor extends Sensor {
    public DefaultEnemyHeadSensor(Entity parent) {
        super(parent);
        Shape shape = SensorPosition.TOP.getColliderShape(parent.getWidth(), parent.getHeight());
        createFixture(shape);
    }

    @Override
    public void onTriggerEnter(GameObject other) {
        if(other instanceof Hero hero) {
            hero.getMovementController().clearVelocityY();
            hero.getMovementController().applyImpulse(0, 5f);
        }
    }

    @Override
    public void onTriggerExit(GameObject other) {

    }
}
