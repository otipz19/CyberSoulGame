package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.base.Animator;
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
import com.mygdx.game.utils.PlayerDataManager;
/**
 * Represents an abstract class defining the behavior and attributes of a Hero entity in the game.
 * Extends {@link MortalEntity} and implements {@link ProjectileCollidable}.
 */
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
    protected String shieldImpactSound;
    protected String hpLossSound;
    protected String deathSound;

    /**
     * Constructs a Hero object with the specified parameters.
     *
     * @param level The level in which the hero exists.
     * @param heroData Data defining specific characteristics of this hero type.
     * @param x The initial x-coordinate position of the hero.
     * @param y The initial y-coordinate position of the hero.
     * @param width The width of the hero.
     * @param height The height of the hero.
     */
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
        shieldImpactSound = Assets.Sound.SHIELD_IMPACT_SOUND;

        resourcesManager.addOnShieldChangeAction(delta -> {
            if (delta < 0)
                SoundPlayer.getInstance().playSound(shieldImpactSound);
            return false;
        });
    }

    /**
     * Renders the hero in the game world.
     *
     * @param deltaTime Time passed since the last frame in seconds.
     */
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

    /**
     * Handles the logic for executing attacks based on player input.
     */
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

    /**
     * Executes a specific attack action with sound and animation.
     *
     * @param attack The attack to execute.
     * @param soundName The name of the sound to play during the attack.
     * @param animation The animation state to set during the attack.
     */
    protected void attack(Attack attack, String soundName, HeroAnimator.State animation) {
        SideAttack sideAttack = (SideAttack)attack;
        if (resourcesManager.tryConsumeEnergy(attack.getEnergyConsumption())) {
            animator.setState(animation);
            level.addDelayedAction(sideAttack.getAttackDelay(), () -> SoundPlayer.getInstance().playSound(soundName));
            attackDelay = sideAttack.getAttackTime();
            sideAttack.setDirection(animator.getDirection() == Animator.Direction.RIGHT);
            sideAttack.execute();
        }
    }

    /**
     * Handles interactions based on player input.
     */
    protected void handleInteraction() {
        if (Gdx.input.isKeyPressed(Input.Keys.E))
            interactionSensor.interact();
    }

    /**
     * Updates the facing direction of the hero based on player input.
     */
    protected void updateDirection() {
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            animator.setDirection(HeroAnimator.Direction.LEFT);
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            animator.setDirection(HeroAnimator.Direction.RIGHT);
    }

    /**
     * Handles running movement based on player input.
     */
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

    /**
     * Handles jumping mechanics based on player input.
     */
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

    /**
     * Handles falling mechanics and collision checks.
     */
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

    /**
     * Handles dashing mechanics based on player input.
     */
    protected void handleDashing() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && resourcesManager.hasEnergy(attack4.getEnergyConsumption())) {
            boolean hasDashed = movementController.tryDash();
            if (hasDashed) {
                resourcesManager.setInvincible(true);
                level.addDelayedAction(attack4.getAttackTime(), () -> resourcesManager.setInvincible(false));
                attack(attack4, dashSound, HeroAnimator.State.DASH);
            }
        }
    }

    /**
     * Handles transitioning to idle state when no input is detected.
     */
    protected void handleIdle() {
        if (groundTouchListener.isOnSurface() && noInput() && movementController.isBodyEffectivelyIdle()) {
            body.setLinearVelocity(0, level.world.getGravity().y * 0.4f);
            animator.setState(HeroAnimator.State.IDLE);
        }
    }

    /**
     * Checks if no directional input is detected.
     *
     * @return True if no directional input keys are pressed, false otherwise.
     */
    protected boolean noInput() {
        return !Gdx.input.isKeyPressed(Input.Keys.A) &&
                !Gdx.input.isKeyPressed(Input.Keys.D) &&
                !Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) &&
                !Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    /**
     * Retrieves the data defining specific characteristics of this hero.
     *
     * @return The HeroData object associated with this hero.
     */
    public HeroData getData() {
        return resourcesManager.getHeroData();
    }

    /**
     * Handles non-fatal health loss events.
     * Overrides {@link MortalEntity#onNonKillingHealthLoss()}.
     */
    @Override
    protected void onNonKillingHealthLoss() {
        animator.setState(HeroAnimator.State.HURT);
        SoundPlayer.getInstance().playSound(hpLossSound);
        healthLossCount++;
        level.addDelayedAction(0.3f, () -> healthLossCount--);
    }

    /**
     * Retrieves the delay before executing death-related actions.
     * Overrides {@link MortalEntity#getDeathDelay()}.
     *
     * @return The delay time in seconds before executing death actions.
     */
    @Override
    public float getDeathDelay() {
        return 0.6f;
    }

    /**
     * Executes actions specific to
     * Executes actions specific to the hero upon death.
     * Overrides {@link MortalEntity#onDeath()}.
     */
    @Override
    public void onDeath() {
        SoundPlayer.getInstance().playSound(deathSound);
        animator.setState(HeroAnimator.State.DEATH);
        healthLossCount = Integer.MAX_VALUE;
        PlayerDataManager.getInstance().resetData();
    }

    /**
     * Retrieves the movement controller responsible for controlling hero movement.
     *
     * @return The HeroMovementController instance associated with this hero.
     */
    public HeroMovementController getMovementController() {
        return movementController;
    }
}