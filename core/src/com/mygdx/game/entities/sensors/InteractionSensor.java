package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ITriggerListener;
import com.mygdx.game.entities.InteractableEntity;

public class InteractionSensor extends GameObject implements ITriggerListener {
    private final Array<InteractableEntity> entitiesToInteract;
    private final Entity parent;
    public InteractionSensor(Entity parent){
        this.level = parent.getLevel();
        this.body = parent.getBody();
        this.parent = parent;
        entitiesToInteract = new Array<>();

        Shape colliderShape = SensorPosition.SLIM_INSIDE.getColliderShape(parent.getWidth(), parent.getHeight());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = colliderShape;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        colliderShape.dispose();
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
