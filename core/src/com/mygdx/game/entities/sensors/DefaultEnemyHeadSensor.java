package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.heroes.Hero;

/**
 * DefaultEnemyHeadSensor is a type of sensor that detects when a Hero entity
 * interacts with the head of an enemy entity.
 */
public class DefaultEnemyHeadSensor extends Sensor {

    /**
     * Constructs a DefaultEnemyHeadSensor attached to a specified parent Entity.
     *
     * @param parent The Entity to which this sensor is attached, typically the enemy entity.
     */
    public DefaultEnemyHeadSensor(Entity parent) {
        super(parent);
        // Define the shape of the sensor positioned at the top of the parent entity
        Shape shape = SensorPosition.TOP.getColliderShape(parent.getWidth(), parent.getHeight());
        // Create a fixture for the sensor shape
        createFixture(shape);
    }

    /**
     * Called when a GameObject enters the sensor's trigger area.
     * If the GameObject is a Hero, applies a vertical impulse to clear its Y velocity
     * and give it an upward impulse to simulate interaction with the enemy's head.
     *
     * @param other The GameObject that entered the sensor's trigger area.
     */
    @Override
    public void onTriggerEnter(GameObject other) {
        if(other instanceof Hero hero) {
            hero.getMovementController().clearVelocityY();
            hero.getMovementController().applyImpulse(0, 5f);
        }
    }

    /**
     * Called when a GameObject exits the sensor's trigger area.
     * Currently not implemented for this sensor type.
     *
     * @param other The GameObject that exited the sensor's trigger area.
     */
    @Override
    public void onTriggerExit(GameObject other) {
        // Not implemented for this sensor type
    }
}
