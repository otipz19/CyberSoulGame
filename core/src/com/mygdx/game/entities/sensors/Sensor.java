package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ITriggerListener;
import com.mygdx.game.entities.Entity;

public abstract class Sensor extends GameObject implements ITriggerListener {
    protected final Entity parent;

    public Sensor(Entity parent) {
        this.parent = parent;
        this.body = parent.getBody();
        this.level = parent.getLevel();
    }

    protected void createFixture(Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }
}
