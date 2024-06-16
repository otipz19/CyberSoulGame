package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.heroes.Hero;

/**
 * AttackRangeSensor is a type of sensor that detects whether a Hero entity
 * is within its defined attack range.
 */
public class AttackRangeSensor extends Sensor {

    /** Flag indicating whether a Hero is currently within the attack range. */
    private boolean isInRange;

    /**
     * Constructs an AttackRangeSensor object.
     *
     * @param parent The parent Entity to which this sensor belongs.
     * @param sensorPosition The position of the sensor relative to the parent.
     * @param width The width of the sensor.
     * @param height The height of the sensor.
     */
    public AttackRangeSensor(Entity parent, AttackZonePosition sensorPosition, float width, float height) {
        super(parent);
        // Create the shape of the sensor based on the provided parameters
        Shape shape = sensorPosition.getColliderShape(parent.getWidth(), parent.getHeight(), width, height);
        // Create a fixture for the sensor shape
        createFixture(shape);
    }

    /**
     * Checks if a Hero entity is currently within the attack range.
     *
     * @return True if a Hero is within the attack range, otherwise false.
     */
    public boolean isHeroInRange() {
        return isInRange;
    }

    /**
     * Called when another GameObject enters the sensor's trigger area.
     * If the other GameObject is a Hero, sets {@code isInRange} to true.
     *
     * @param other The GameObject that entered the sensor's trigger area.
     */
    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof Hero) {
            isInRange = true;
        }
    }

    /**
     * Called when another GameObject exits the sensor's trigger area.
     * If the other GameObject is a Hero, sets {@code isInRange} to false.
     *
     * @param other The GameObject that exited the sensor's trigger area.
     */
    @Override
    public void onTriggerExit(GameObject other) {
        if (other instanceof Hero) {
            isInRange = false;
        }
    }
}
