package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.Surface;

/**
 * SurfaceTouchSensor is a type of sensor that detects interactions with Surface
 * objects, indicating whether the attached Entity is currently on a surface.
 */
public class SurfaceTouchSensor extends Sensor {

    private int surfaceTouchesNumber;

    /**
     * Constructs a SurfaceTouchSensor attached to a specified parent Entity
     * at a given position.
     *
     * @param parent The Entity to which this sensor is attached.
     * @param position The position relative to the parent Entity where the sensor is located.
     */
    public SurfaceTouchSensor(Entity parent, SensorPosition position) {
        super(parent);
        // Create the shape of the sensor based on the specified position
        Shape colliderShape = position.getColliderShape(parent.getWidth(), parent.getHeight());
        // Create a fixture for the sensor shape
        createFixture(colliderShape);
    }

    /**
     * Checks whether the parent Entity is currently on a Surface.
     *
     * @return True if the parent Entity is on a Surface, otherwise false.
     */
    public boolean isOnSurface() {
        return surfaceTouchesNumber > 0;
    }

    /**
     * Called when another GameObject enters the sensor's trigger area.
     * If the GameObject is a Surface, increments the count of surface touches.
     *
     * @param other The GameObject that entered the sensor's trigger area.
     */
    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof Surface)
            surfaceTouchesNumber++;
    }

    /**
     * Called when another GameObject exits the sensor's trigger area.
     * If the GameObject is a Surface, decrements the count of surface touches.
     *
     * @param other The GameObject that exited the sensor's trigger area.
     */
    @Override
    public void onTriggerExit(GameObject other) {
        if (other instanceof Surface)
            surfaceTouchesNumber--;
    }

}
