package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.concrete.heroes.HeroAnimator;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.attacks.base.Attack;
import com.mygdx.game.entities.attacks.base.SideAttack;
import com.mygdx.game.entities.movement.HeroMovementController;
import com.mygdx.game.entities.projectiles.ProjectileCollidable;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.entities.sensors.InteractionSensor;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.entities.sensors.SurfaceTouchSensor;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.DelayedAction;

public abstract class Hero extends MortalEntity<HeroResourcesManager> implements ProjectileCollidable {
    protected final HeroMovementController movementController;
    protected Attack attack1;
    protected Attack attack2;
    protected Attack attack3;
    protected Attack attack4;
    protected final SurfaceTouchSensor groundTouchListener;
    protected final SurfaceTouchSensor leftWallTouchListener;
    protected final SurfaceTouchSensor rightWallTouchListener;
    protected final InteractionSensor interactionSensor;
    protected boolean canDoubleJump;
    protected float attackDelay;
    protected int healthLossCount;
    protected String attack1Sound;
    protected String attack2Sound;
    protected String attack3Sound;
    protected String dashSound;
    protected String jumpSound;
    protected String hpLossSound;

    public Hero(Level level, HeroData heroData, float x, float y, float width, float height) {
        super(new HeroResourcesManager(heroData));

        this.level = level;
        this.width = width;
        this.height = height;

        body = BodyCreator.createDynamicBody(level.world, ColliderCreator.create(x, y, width, height), 0.3f, 1, 0);
        body.setFixedRotation(true);
        body.getFixtureList().first().setUserData(this);

        movementController = new HeroMovementController(body);
        groundTouchListener = new SurfaceTouchSensor(this, SensorPosition.BOTTOM);
        leftWallTouchListener = new SurfaceTouchSensor(this, SensorPosition.LEFT);
        rightWallTouchListener = new SurfaceTouchSensor(this, SensorPosition.RIGHT);
        interactionSensor = new InteractionSensor(this);

        dashSound = Assets.Sound.HERO_DASH_SOUND;
        jumpSound = Assets.Sound.HERO_JUMP_SOUND;
    }

    public void render(float deltaTime) {
        if (deltaTime != 0) {
            movementController.update(deltaTime);
            attackDelay = Math.max(0, attackDelay - deltaTime);
            if (healthLossCount == 0 && attackDelay == 0) {
                handleAttack();
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
    }

    protected void handleAttack() {
        if (groundTouchListener.isOnSurface()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                attack(attack1, attack1Sound, HeroAnimator.State.ATTACK_1);
                movementController.clearVelocityX();
            } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                attack(attack2, attack2Sound, HeroAnimator.State.ATTACK_2);
                movementController.clearVelocityX();
            }
            else if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE)) {
                attack(attack3, attack3Sound, HeroAnimator.State.PUNCH);
                movementController.clearVelocityX();
            }
        }
    }

    protected void attack(Attack attack, String soundName, HeroAnimator.State animation) {
        SideAttack sideAttack = (SideAttack)attack;
        if (resourcesManager.tryConsumeEnergy(attack.getEnergyConsumption())) {
            animator.setState(animation);
            new DelayedAction(sideAttack.getAttackDelay(), () -> SoundPlayer.getInstance().playSound(soundName));
            attackDelay = sideAttack.getAttackTime();
            sideAttack.setDirection(movementController.isFacingRight());
            sideAttack.execute();
        }
    }

    protected void handleInteraction() {
        if (Gdx.input.isKeyPressed(Input.Keys.E))
            interactionSensor.interact();
    }

    protected void updateDirection() {
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            animator.setDirection(HeroAnimator.Direction.LEFT);
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            animator.setDirection(HeroAnimator.Direction.RIGHT);
    }

    protected void handeRunning() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movementController.moveLeft();
            if (groundTouchListener.isOnSurface())
                animator.setState(HeroAnimator.State.RUN);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movementController.moveRight();
            if (groundTouchListener.isOnSurface())
                animator.setState(HeroAnimator.State.RUN);
        }
    }

    protected void handleJumping() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (groundTouchListener.isOnSurface()) {
                boolean hasJumped = movementController.tryJump();
                if (hasJumped) {
                    canDoubleJump = true;
                    animator.setState(HeroAnimator.State.JUMP);
                    SoundPlayer.getInstance().playSound(jumpSound);
                }
            } else if (canDoubleJump) {
                boolean hasJumped = movementController.tryJump();
                if (hasJumped) {
                    canDoubleJump = false;
                    animator.setState(HeroAnimator.State.DOUBLE_JUMP);
                    SoundPlayer.getInstance().playSound(jumpSound);
                }
            }
        }
    }

    protected void handleFalling() {
        if (!groundTouchListener.isOnSurface()) {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                movementController.accelerateFall();
                animator.setState(HeroAnimator.State.JUMP);
            }

            movementController.clampVelocityNearWalls(leftWallTouchListener.isOnSurface(), rightWallTouchListener.isOnSurface());

            if (animator.getState() == HeroAnimator.State.RUN || animator.getState() == HeroAnimator.State.DASH || animator.getState() == HeroAnimator.State.IDLE)
                animator.setState(HeroAnimator.State.JUMP);
        }
    }

    protected void handleDashing() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && resourcesManager.hasEnergy(attack4.getEnergyConsumption())) {
            boolean hasDashed = movementController.tryDash();
            if (hasDashed) {
                resourcesManager.setInvincible(true);
                new DelayedAction(attack4.getAttackTime(), () -> resourcesManager.setInvincible(false));
                attack(attack4, dashSound, HeroAnimator.State.DASH);
            }
        }
    }

    protected void handleIdle() {
        if (groundTouchListener.isOnSurface() && noInput() && movementController.isBodyEffectivelyIdle()) {
            body.setLinearVelocity(0, level.world.getGravity().y * 0.4f);
            animator.setState(HeroAnimator.State.IDLE);
        }
    }

    protected boolean noInput() {
        return !Gdx.input.isKeyPressed(Input.Keys.A) &&
                !Gdx.input.isKeyPressed(Input.Keys.D) &&
                !Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) &&
                !Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    public Vector2 getCameraPosition() {
        return new Vector2(body.getPosition().x + width / 2, body.getPosition().y + height / 2);
    }

    public HeroData getData() {
        return resourcesManager.getHeroData();
    }

    @Override
    protected void onNonKillingHealthLoss() {
        animator.setState(HeroAnimator.State.HURT);
        SoundPlayer.getInstance().playSound(hpLossSound);
        healthLossCount++;
        new DelayedAction(0.3f, () -> healthLossCount--);
    }

    @Override
    public float getDeathDelay() {
        return 0.6f;
    }

    @Override
    public void onDeath() {
        animator.setState(HeroAnimator.State.DEATH);
        SoundPlayer.getInstance().playSound(hpLossSound);
        healthLossCount = Integer.MAX_VALUE;
        new DelayedAction(getDeathDelay(), MyGdxGame.getInstance()::levelFailed);
    }

    public HeroMovementController getMovementController() {
        return movementController;
    }
}