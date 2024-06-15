package com.mygdx.game.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.GameObject;

/**
 * Utility class for creating Box2D bodies with fixtures based on provided colliders.
 * Each method creates a Box2D body of a specific type (static, dynamic, sensor, or projectile),
 * attaches a fixture defined by the collider, and disposes of the collider afterward.
 */
public class BodyCreator {

    /**
     * Creates a static Box2D body with a fixture from the provided collider.
     *
     * @param world      The Box2D world where the body will be created.
     * @param collider   The Collider defining the shape and position of the fixture.
     * @param friction   The friction coefficient of the fixture.
     * @param density    The density of the fixture.
     * @param restitution The restitution (bounciness) of the fixture.
     * @return The created Box2D body.
     */
    public static Body createStaticBody(World world, Collider collider, float friction, float density, float restitution) {
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

    /**
     * Creates a dynamic Box2D body with a fixture from the provided collider.
     *
     * @param world      The Box2D world where the body will be created.
     * @param collider   The Collider defining the shape and position of the fixture.
     * @param friction   The friction coefficient of the fixture.
     * @param density    The density of the fixture.
     * @param restitution The restitution (bounciness) of the fixture.
     * @return The created Box2D body.
     */
    public static Body createDynamicBody(World world, Collider collider, float friction, float density, float restitution) {
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

    /**
     * Creates a static Box2D body with a sensor fixture from the provided collider and attaches user data to the fixture.
     *
     * @param world    The Box2D world where the body will be created.
     * @param collider The Collider defining the shape and position of the fixture.
     * @param userData The user data to attach to the fixture.
     * @return The created Box2D body.
     */
    public static Body createSensorBody(World world, Collider collider, GameObject userData) {
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

    /**
     * Creates a dynamic Box2D body with a sensor fixture from the provided collider.
     * This method allows controlling whether the projectile is affected by gravity.
     *
     * @param world             The Box2D world where the body will be created.
     * @param collider          The Collider defining the shape and position of the fixture.
     * @param isAffectedByGravity True if the projectile should be affected by gravity, false otherwise.
     * @return The created Box2D body.
     */
    public static Body createProjectileBody(World world, Collider collider, float initialSpeedX, float initialSpeedY, boolean isAffectedByGravity) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.allowSleep = false;
        bodyDef.linearDamping = 0;
        bodyDef.linearVelocity.set(initialSpeedX, initialSpeedY);
        bodyDef.position.set(collider.getX(), collider.getY());
        if (!isAffectedByGravity)
            bodyDef.gravityScale = 0;
        bodyDef.bullet = true;
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);

        collider.dispose();

        return body;
    }
}
