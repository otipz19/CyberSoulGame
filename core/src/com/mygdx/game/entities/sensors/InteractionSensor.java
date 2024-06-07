package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ITriggerListener;
import com.mygdx.game.entities.InteractableEntity;

public class InteractionSensor extends Sensor {
    private final Array<InteractableEntity> entitiesToInteract;

    public InteractionSensor(Entity parent){
        super(parent);
        entitiesToInteract = new Array<>();
        Shape colliderShape = SensorPosition.SLIM_INSIDE.getColliderShape(parent.getWidth(), parent.getHeight());
        createFixture(colliderShape);
    }

    public void interact() {
        entitiesToInteract.forEach(e -> e.interact(parent));
    }

    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof InteractableEntity interactableEntity) {
            entitiesToInteract.add(interactableEntity);
        }
    }

    @Override
    public void onTriggerExit(GameObject other) {
        if (other instanceof InteractableEntity interactableEntity) {
            entitiesToInteract.removeValue(interactableEntity, true);
        }
    }
}
