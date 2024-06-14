package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ITriggerListener;
import com.mygdx.game.entities.InteractableEntity;

/**
 * InteractionSensor is a sensor used to detect and manage interactions with
 * entities that implement the InteractableEntity interface.
 */
public class InteractionSensor extends Sensor {

    /** Array of entities that this sensor can interact with. */
    private final Array<InteractableEntity> entitiesToInteract;

    /**
     * Constructs an InteractionSensor attached to a specified parent Entity.
     *
     * @param parent The Entity to which this sensor is attached.
     */
    public InteractionSensor(Entity parent) {
        super(parent);
        entitiesToInteract = new Array<>();
        // Define the shape of the sensor positioned inside the parent entity
        Shape colliderShape = SensorPosition.SLIM_INSIDE.getColliderShape(parent.getWidth(), parent.getHeight());
        // Create a fixture for the sensor shape
        createFixture(colliderShape);
    }

    /**
     * Initiates interaction with all entities currently within the sensor's range.
     * Calls the interact method on each InteractableEntity.
     */
    public void interact() {
        entitiesToInteract.forEach(e -> e.interact(parent));
    }

    /**
     * Called when a GameObject enters the sensor's trigger area.
     * If the GameObject is an InteractableEntity, adds it to the list of entities
     * that can be interacted with.
     *
     * @param other The GameObject that entered the sensor's trigger area.
     */
    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof InteractableEntity interactableEntity) {
            entitiesToInteract.add(interactableEntity);
        }
    }

    /**
     * Called when a GameObject exits the sensor's trigger area.
     * If the GameObject is an InteractableEntity, removes it from the list of entities
     * that can be interacted with.
     *
     * @param other The GameObject that exited the sensor's trigger area.
     */
    @Override
    public void onTriggerExit(GameObject other) {
        if (other instanceof InteractableEntity interactableEntity) {
            entitiesToInteract.removeValue(interactableEntity, true);
        }
    }
}
