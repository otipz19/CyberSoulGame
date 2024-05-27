package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.animation.HeroAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class Hero extends Entity {
    private HeroData heroData;
    private final static float MAX_VELOCITY = 5f;

    public Hero(Level level, HeroData heroData, float x, float y, float width, float height) {
        this.level = level;
        this.heroData = heroData;
        this.animator = new HeroAnimator();
        this.width = width;
        this.height = height;

        Collider collider = ColliderCreator.create(new Rectangle(x, y, width, height));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(collider.getX(), collider.getY());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.density = 1;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0;

        body = level.world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        body.setUserData(this);

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

        animator.animate(level.game.batch, body.getPosition().x, body.getPosition().y, width, height);
    }

    public Vector2 getCameraPosition(){
        return new Vector2(body.getPosition().x+width/2, body.getPosition().y+height/2);
    }

    private void applyImpulse(float x, float y){
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(x, y, center.x, center.y, true);
    }
}
