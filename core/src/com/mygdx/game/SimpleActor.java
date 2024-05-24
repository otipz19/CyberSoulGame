package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.animation.HeroAnimator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class SimpleActor {
    private MyGdxGame game;
    public Body body;
    private final static float MAX_VELOCITY = 5f;
    private final Vector2 worldSize = new Vector2(1.5f, 1.5f);

    private HeroAnimator animator;

    public SimpleActor(MyGdxGame game, float x, float y) {
        this.game = game;
        animator = new HeroAnimator();

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
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        collider.dispose();

    }

    public void render() {
        Vector2 velocity = body.getLinearVelocity();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && velocity.x > -MAX_VELOCITY) {
            applyImpulse(-0.8f, 0);
            animator.setState(HeroAnimator.State.RUN);
            animator.setDirection(HeroAnimator.Direction.LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && velocity.x < MAX_VELOCITY) {
            applyImpulse(0.8f, 0);
            animator.setState(HeroAnimator.State.RUN);
            animator.setDirection(HeroAnimator.Direction.RIGHT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && velocity.y < MAX_VELOCITY) {
            applyImpulse(0, 0.8f);
            animator.setState(HeroAnimator.State.JUMP);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && velocity.y > -MAX_VELOCITY) {
            applyImpulse(0, -0.8f);
            animator.setState(HeroAnimator.State.IDLE);
        } else {
            animator.setState(HeroAnimator.State.IDLE);
        }

        animator.animate(game.getBatch(), body.getPosition().x, body.getPosition().y, worldSize.x, worldSize.y);
    }


    private void applyImpulse(float x, float y){
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(x, y, center.x, center.y, true);
    }
}
