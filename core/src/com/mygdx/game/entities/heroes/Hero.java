package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.Animator;
import com.mygdx.game.animation.HeroAnimator;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.sensors.InteractionSensor;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.entities.sensors.SurfaceTouchSensor;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class Hero extends MortalEntity<HeroResourcesManager> implements Disposable {
    private final static float MAX_VELOCITY = 5f;
    private final static float MIN_NOT_IDLE_VELOCITY = MAX_VELOCITY * 0.6f;
    private final static float DASH_COOLDOWN_TIME = 2f;
    private final static float JUMP_DELAY = 0.5f;
    private final HeroAttack1 attack1;
    private final HeroAttack2 attack2;
    private final HeroAttack3 attack3;
    private final HeroAttack4 attack4;
    private final SurfaceTouchSensor groundTouchListener;
    private final SurfaceTouchSensor leftWallTouchListener;
    private final SurfaceTouchSensor rightWallTouchListener;
    private final InteractionSensor interactionSensor;
    private boolean canDoubleJump;
    private float dashCooldown;
    private float jumpDelay;
    private float attackDelay;

    public Hero(Level level, HeroData heroData, float x, float y, float width, float height) {
        this.level = level;
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

        groundTouchListener = new SurfaceTouchSensor(this, SensorPosition.BOTTOM);
        leftWallTouchListener = new SurfaceTouchSensor(this, SensorPosition.LEFT);
        rightWallTouchListener = new SurfaceTouchSensor(this, SensorPosition.RIGHT);
        interactionSensor = new InteractionSensor(this);

        attack1 = new HeroAttack1(this);
        attack2 = new HeroAttack2(this);
        attack3 = new HeroAttack3(this);
        attack4 = new HeroAttack4(this);

        resourcesManager = new HeroResourcesManager(heroData);
    }

    public void render(float deltaTime) {
        if (deltaTime != 0) {
            jumpDelay = Math.max(0, jumpDelay - deltaTime);
            dashCooldown = Math.max(0, dashCooldown - deltaTime);
            attackDelay = Math.max(0, attackDelay - deltaTime);
            handleAttack();
            if (attackDelay == 0) {
                handleInteraction();
                updateDirection();
                handeRunning();
                handleJumping();
                handleFalling();
                handleDashing();
                handleIdle();
            }
            updateResourcesManager(deltaTime);
        }
        animator.animate(MyGdxGame.getInstance().batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);

        // Should be changed later
        if (body.getPosition().y < -10)
            resourcesManager.decreaseHealth(10);
    }

    private void handleAttack() {
        if (attackDelay == 0 && groundTouchListener.isOnSurface()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                animator.setState(HeroAnimator.State.ATTACK_1);
                attackDelay = attack1.getAttackTime();
                attack1.setDirection(animator.getDirection() == Animator.Direction.RIGHT);
                attack1.execute();
                clearVelocityX();
            } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                animator.setState(HeroAnimator.State.ATTACK_2);
                attackDelay = attack2.getAttackTime();
                attack2.setDirection(animator.getDirection() == Animator.Direction.RIGHT);
                attack2.execute();
                clearVelocityX();
            }
            else if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE)) {
                animator.setState(HeroAnimator.State.PUNCH);
                attackDelay = attack3.getAttackTime();
                attack3.setDirection(animator.getDirection() == Animator.Direction.RIGHT);
                attack3.execute();
                clearVelocityX();
            }
        }
    }

    private void handleInteraction() {
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            interactionSensor.interact();
        }
    }

    private void updateDirection() {
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
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (body.getLinearVelocity().x <= MAX_VELOCITY)
                applyImpulse(0.6f, 0);
            if (groundTouchListener.isOnSurface())
                animator.setState(HeroAnimator.State.RUN);
        }
    }

    private void handleJumping() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (groundTouchListener.isOnSurface() && jumpDelay == 0) {
                clearVelocityY();
                applyImpulse(0, 7f);
                canDoubleJump = true;
                animator.setState(HeroAnimator.State.JUMP);
                jumpDelay = JUMP_DELAY;
            } else if (canDoubleJump && jumpDelay == 0) {
                clearVelocityY();
                applyImpulse(0, 8f);
                canDoubleJump = false;
                animator.setState(HeroAnimator.State.DOUBLE_JUMP);
                jumpDelay = JUMP_DELAY;
            }
        }
    }

    private void handleFalling() {
        if (!groundTouchListener.isOnSurface()) {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
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

            if (animator.getState() == HeroAnimator.State.RUN || animator.getState() == HeroAnimator.State.RUN_ATTACK)
                animator.setState(HeroAnimator.State.JUMP);
        }
    }

    private void handleDashing() {
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && dashCooldown == 0) {
            if (animator.getDirection() == Animator.Direction.LEFT) {
                body.setLinearVelocity(-MAX_VELOCITY, body.getLinearVelocity().y);
                applyImpulse(-4f, 0);
            } else {
                body.setLinearVelocity(MAX_VELOCITY, body.getLinearVelocity().y);
                applyImpulse(4f, 0);
            }
            animator.setState(HeroAnimator.State.RUN_ATTACK);
            attackDelay = attack4.getAttackTime();
            attack4.setDirection(animator.getDirection() == Animator.Direction.RIGHT);
            attack4.execute();
            dashCooldown = DASH_COOLDOWN_TIME;
        }
    }

    private void handleIdle() {
        if (groundTouchListener.isOnSurface() && noInput() && Math.abs(body.getLinearVelocity().x) < MIN_NOT_IDLE_VELOCITY) {
            body.setLinearVelocity(0, level.world.getGravity().y * 0.4f);
            animator.setState(HeroAnimator.State.IDLE);
        }
    }

    private boolean noInput() {
        return !Gdx.input.isKeyPressed(Input.Keys.A) &&
                !Gdx.input.isKeyPressed(Input.Keys.S) &&
                !Gdx.input.isKeyPressed(Input.Keys.D) &&
                !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) &&
                !Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }


    private void clearVelocityX(){
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(0, oldVelocity.y);
    }

    private void clearVelocityY(){
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(oldVelocity.x, Math.max(0, oldVelocity.y));
    }

    private void applyImpulse(float x, float y){
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(x, y, center.x, center.y, true);
    }

    public Vector2 getCameraPosition() {
        return new Vector2(body.getPosition().x + width / 2, body.getPosition().y + height / 2);
    }

    public HeroData getData() {
        return resourcesManager.getHeroData();
    }

    @Override
    public void dispose() {
        attack1.dispose();
        attack2.dispose();
        attack3.dispose();
        attack4.dispose();
    }
}
