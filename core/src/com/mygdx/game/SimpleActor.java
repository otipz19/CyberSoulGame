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
    private boolean canDoubleJump;
    private float jumpCooldown=0;
    private final static float JUMP_COOLDOWN_TIME = 0.5f;
    private float dashCooldown=0;
    private final static float DASH_COOLDOWN_TIME = 1f;

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

        canDoubleJump = true;
        jumpCooldown = 0f;
        dashCooldown = 0f;

    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        jumpCooldown -= deltaTime;
        dashCooldown -= deltaTime;
        Vector2 velocity = body.getLinearVelocity();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.getFixtureList().forEach(b->b.setFriction(0));
            if(velocity.x > -MAX_VELOCITY) {
                applyImpulse(-0.8f, 0);
            }
            if(isOnGround())
                animator.setState(HeroAnimator.State.RUN);
            animator.setDirection(HeroAnimator.Direction.LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.getFixtureList().forEach(b->b.setFriction(0));
            if(velocity.x < MAX_VELOCITY) {
                applyImpulse(0.8f, 0);
            }
            if(isOnGround())
                animator.setState(HeroAnimator.State.RUN);
            animator.setDirection(HeroAnimator.Direction.RIGHT);
        }else if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && (dashCooldown <= 0)) {
            if(animator.getDirection()==HeroAnimator.Direction.RIGHT) {
                applyImpulse(13f, 0);
            }
            else {
                applyImpulse(-13f, 0);
            }
            animator.setState(HeroAnimator.State.RUN);
            dashCooldown = DASH_COOLDOWN_TIME;
        }  else if (Gdx.input.isKeyPressed(Input.Keys.UP) && (isOnGround()||(canDoubleJump && jumpCooldown <= 0))) {
            if (isOnGround()) {
                applyImpulse(0, 10f);
                canDoubleJump = true;
                animator.setState(HeroAnimator.State.JUMP);
            } else if (canDoubleJump) {
                body.setLinearVelocity(body.getLinearVelocity().x,0);
                applyImpulse(0, 18f);
                canDoubleJump = false;
                animator.setState(HeroAnimator.State.DOUBLE_JUMP);
            }
            jumpCooldown = JUMP_COOLDOWN_TIME;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && velocity.y > -MAX_VELOCITY) {
            applyImpulse(0, -0.8f);
            animator.setState(HeroAnimator.State.IDLE);
        } else {
            if (isOnGround()) {
                animator.setState(HeroAnimator.State.IDLE);
                canDoubleJump = true;
            }
            body.getFixtureList().forEach(b->b.setFriction(1));
        }
        animator.animate(game.getBatch(), body.getPosition().x, body.getPosition().y, worldSize.x, worldSize.y);
    }

    public Vector2 getCameraPosition() {
        return new Vector2(body.getPosition().x + worldSize.x / 2, body.getPosition().y + worldSize.y / 2);
    }

    private void applyImpulse(float x, float y) {
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(new Vector2(x, y), center, true);
    }

    private boolean isOnGround() {
        for (Contact contact : game.world.getContactList()) {
            if (contact.isTouching()) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();
                if ((a.getBody() == body || b.getBody() == body)) {
                    WorldManifold worldManifold = contact.getWorldManifold();
                    for (int i = 0; i < worldManifold.getNumberOfContactPoints(); i++) {
                        Vector2 normal = worldManifold.getNormal();
                        if (normal.y > 0.5f) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
