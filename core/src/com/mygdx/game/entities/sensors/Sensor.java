package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ITriggerListener;
import com.mygdx.game.entities.Entity;

/**
 * Sensor is an abstract class representing a sensor attached to an Entity,
 * used for detecting interactions with other game objects.
 */
public abstract class Sensor extends GameObject implements ITriggerListener {

    /** The parent Entity to which this sensor is attached. */
    protected final Entity parent;

    /**
     * Constructs a Sensor attached to a specified parent Entity.
     *
     * @param parent The Entity to which this sensor is attached.
     */
    public Sensor(Entity parent) {
        this.parent = parent;
        this.body = parent.getBody();
        this.level = parent.getLevel();
    }

    /**
     * Creates a fixture for the sensor with the specified shape.
     * The fixture is set as a sensor (non-colliding) and is associated
     * with this sensor object.
     *
     * @param shape The shape defining the area of the sensor.
     */
    protected void createFixture(Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }
}
