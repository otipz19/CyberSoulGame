package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class SimpleActor {
    private MyGdxGame game;
    private Texture sprite;
    public Body body;
    private Fixture fixture;
    private final static float MAX_VELOCITY = 5f;
    private final Vector2 worldSize = new Vector2(1f, 1.5f);

    public SimpleActor(MyGdxGame game, float x, float y) {
        this.game = game;
        sprite = game.getAssetManager().get("hero.png");

        Collider collider = ColliderCreator.create(new Rectangle(x, y, worldSize.x, worldSize.y));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(collider.getX(), collider.getY());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.density = 1;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0;

        body = game.world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        collider.dispose();
    }

    public void render() {
        Vector2 velocity = body.getLinearVelocity();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && velocity.x > -MAX_VELOCITY) {
            applyImpulse(-0.8f, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && velocity.x < MAX_VELOCITY) {
            applyImpulse(0.8f, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && velocity.y < MAX_VELOCITY) {
            applyImpulse(0, 0.8f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && velocity.y > -MAX_VELOCITY) {
            applyImpulse(0, -0.8f);
        }

        game.getBatch().draw(sprite, body.getPosition().x, body.getPosition().y, worldSize.x, worldSize.y);
    }

    private void applyImpulse(float x, float y){
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(x, y, center.x, center.y, true);
    }

    private float delta(float value) {
        return value * Gdx.graphics.getDeltaTime();
    }
}
