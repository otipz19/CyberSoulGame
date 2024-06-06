package com.mygdx.game.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.physics.Collider;

public class BodyCreator {
    public static Body createStaticBody(World world, Collider collider, float friction, float density, float restitution){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(collider.getX(), collider.getY());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.friction = friction;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        collider.dispose();
        return body;
    }

    public static Body createDynamicBody(World world, Collider collider, float friction, float density, float restitution){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(collider.getX(), collider.getY());
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.friction = friction;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        body.createFixture(fixtureDef);

        collider.dispose();

        return body;
    }

    public static Body createSensorBody(World world, Collider collider, GameObject userData){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(collider.getX(), collider.getY());
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);

        collider.dispose();

        return body;
    }
}
