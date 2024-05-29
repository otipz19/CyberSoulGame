package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.animation.Animator;
import com.mygdx.game.animation.HeroAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class Hero extends MortalEntity<HeroResourcesManager> {
    private final static float MAX_VELOCITY = 5f;
    private final static float MIN_NOT_IDLE_VELOCITY = MAX_VELOCITY*0.6f;
    private final static float DASH_COOLDOWN_TIME = 2f;
    private final static float DOUBLEJUMP_DELAY = 0.5f;
    private HeroData heroData;
    private final SurfaceTouchListener groundTouchListener;
    private final SurfaceTouchListener leftWallTouchListener;
    private final SurfaceTouchListener rightWallTouchListener;
    private boolean canDoubleJump;
    private float jumpCooldown;
    private float dashCooldown;

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
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0;

        body = level.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        fixture.setUserData(this);

        collider.dispose();

        groundTouchListener = new SurfaceTouchListener(this, SurfaceTouchListener.ListenerPosition.BOTTOM);
        leftWallTouchListener = new SurfaceTouchListener(this, SurfaceTouchListener.ListenerPosition.LEFT);
        rightWallTouchListener = new SurfaceTouchListener(this, SurfaceTouchListener.ListenerPosition.RIGHT);

        resourcesManager = new HeroResourcesManager(heroData);
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        jumpCooldown -= deltaTime;
        dashCooldown -= deltaTime;
        updateDirection();
        handeRunning();
        handleJumping();
        handleFalling();
        handleDashing();
        handleIdle();
        updateResourcesManager(deltaTime);
        animator.animate(level.game.batch, body.getPosition().x, body.getPosition().y, width, height);
    }

    private void updateDirection(){
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            animator.setDirection(HeroAnimator.Direction.LEFT);
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            animator.setDirection(HeroAnimator.Direction.RIGHT);
    }

    private void handeRunning() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (body.getLinearVelocity().x >= -MAX_VELOCITY)
                applyImpulse(-0.6f, 0);
            if (groundTouchListener.isOnSurface())
                animator.setState(HeroAnimator.State.RUN);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)){
            if (body.getLinearVelocity().x <= MAX_VELOCITY)
                applyImpulse(0.6f, 0);
            if (groundTouchListener.isOnSurface())
                animator.setState(HeroAnimator.State.RUN);
        }

        if (!groundTouchListener.isOnSurface() && animator.getState() == HeroAnimator.State.RUN)
            animator.setState(HeroAnimator.State.JUMP);
    }

    private void handleJumping() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (groundTouchListener.isOnSurface() && jumpCooldown <= 0){
                clearVelocityY();
                applyImpulse(0, 7f);
                canDoubleJump = true;
                animator.setState(HeroAnimator.State.JUMP);
                jumpCooldown = DOUBLEJUMP_DELAY;
            }
            else if (canDoubleJump && jumpCooldown <= 0){
                clearVelocityY();
                applyImpulse(0, 8f);
                canDoubleJump = false;
                animator.setState(HeroAnimator.State.DOUBLE_JUMP);
            }
        }
    }

    private void clearVelocityY(){
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(oldVelocity.x, Math.max(0, oldVelocity.y));
    }

    private void handleFalling() {
        if (!groundTouchListener.isOnSurface()){
            if (Gdx.input.isKeyPressed(Input.Keys.S)){
                if (body.getLinearVelocity().y >= -MAX_VELOCITY)
                    applyImpulse(0, -0.6f);
                animator.setState(HeroAnimator.State.JUMP);
            }

            Vector2 velocity = body.getLinearVelocity();
            if (rightWallTouchListener.isOnSurface())
                velocity.x = Math.min(velocity.x, 0);
            if (leftWallTouchListener.isOnSurface())
                velocity.x = Math.max(velocity.x, 0);
            body.setLinearVelocity(velocity);
        }
    }

    private void handleDashing() {
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && dashCooldown <= 0) {
            if (animator.getDirection() == Animator.Direction.LEFT){
                body.setLinearVelocity(-MAX_VELOCITY, body.getLinearVelocity().y);
                applyImpulse(-4f, 0);
            }
            else {
                body.setLinearVelocity(MAX_VELOCITY, body.getLinearVelocity().y);
                applyImpulse(4f, 0);
            }
            animator.setState(HeroAnimator.State.RUN);
            dashCooldown = DASH_COOLDOWN_TIME;
        }
    }

    private void handleIdle() {
        if (groundTouchListener.isOnSurface() && noInput() && Math.abs(body.getLinearVelocity().x) < MIN_NOT_IDLE_VELOCITY){
            body.setLinearVelocity(0, level.world.getGravity().y*0.4f);
            animator.setState(HeroAnimator.State.IDLE);
        }
    }

    private boolean noInput(){
        return  !Gdx.input.isKeyPressed(Input.Keys.A) &&
                !Gdx.input.isKeyPressed(Input.Keys.S) &&
                !Gdx.input.isKeyPressed(Input.Keys.D) &&
                !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) &&
                !Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    private void applyImpulse(float x, float y){
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(x, y, center.x, center.y, true);
    }

    public Vector2 getCameraPosition() {
        return new Vector2(body.getPosition().x + width / 2, body.getPosition().y + height / 2);
    }
}
