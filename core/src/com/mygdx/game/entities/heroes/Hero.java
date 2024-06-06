package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.HeroAnimator;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.attacks.concrete.*;
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
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.DelayedAction;

public class Hero extends MortalEntity<HeroResourcesManager> implements Disposable, ProjectileCollidable {
    private final HeroMovementController movementController;
    private final BikerAttack1 attack1;
    private final BikerAttack2 attack2;
    private final BikerAttack3 attack3;
    private final BikerAttack4 attack4;
    private final TestProjectileAttack testProjectileAttack;
    private final SurfaceTouchSensor groundTouchListener;
    private final SurfaceTouchSensor leftWallTouchListener;
    private final SurfaceTouchSensor rightWallTouchListener;
    private final InteractionSensor interactionSensor;
    private boolean canDoubleJump;
    private float attackDelay;
    private int healthLossCount;

    public Hero(Level level, HeroData heroData, float x, float y, float width, float height) {
        super(new HeroResourcesManager(heroData));

        this.level = level;
        this.animator = new HeroAnimator();
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

        attack1 = new BikerAttack1(this);
        attack2 = new BikerAttack2(this);
        attack3 = new BikerAttack3(this);
        attack4 = new BikerAttack4(this);
        testProjectileAttack = new TestProjectileAttack(this);

        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);
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

        // Should be changed later
        if (body.getPosition().y < -10)
            resourcesManager.decreaseHealth(10);
    }

    private void handleAttack() {
        if (groundTouchListener.isOnSurface()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                attack(attack1, AssetsNames.ATTACK_SOUND, HeroAnimator.State.ATTACK_1);
                movementController.clearVelocityX();
            } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                attack(attack2, AssetsNames.ATTACK_COMBO_SOUND, HeroAnimator.State.ATTACK_2);
                movementController.clearVelocityX();
            }
            else if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE)) {
                attack(attack3, AssetsNames.ATTACK_KICK_SOUND, HeroAnimator.State.PUNCH);
                movementController.clearVelocityX();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            testProjectileAttack.setDirection(movementController.isFacingRight());
            testProjectileAttack.execute();
        }
    }

    private void attack(SideMeleeAttack attack, String soundName, HeroAnimator.State animation) {
        if (resourcesManager.tryConsumeEnergy(attack.getEnergyConsumption())) {
            animator.setState(animation);
            new DelayedAction(attack.getAttackDelay(), () -> SoundPlayer.getInstance().playSound(soundName));
//            animator.blockAnimationReset();
            attackDelay = attack.getAttackTime();
            attack.setDirection(movementController.isFacingRight());
            attack.execute();
        }
    }

    private void handleInteraction() {
        if (Gdx.input.isKeyPressed(Input.Keys.E))
            interactionSensor.interact();
    }

    private void updateDirection() {
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            animator.setDirection(HeroAnimator.Direction.LEFT);
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            animator.setDirection(HeroAnimator.Direction.RIGHT);
    }

    private void handeRunning() {
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

    private void handleJumping() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (groundTouchListener.isOnSurface()) {
                boolean hasJumped = movementController.tryJump();
                if (hasJumped) {
                    canDoubleJump = true;
                    animator.setState(HeroAnimator.State.JUMP);
                    SoundPlayer.getInstance().playSound(AssetsNames.JUMP_SOUND);
                }
            } else if (canDoubleJump) {
                boolean hasJumped = movementController.tryJump();
                if (hasJumped) {
                    canDoubleJump = false;
                    animator.setState(HeroAnimator.State.DOUBLE_JUMP);
                    SoundPlayer.getInstance().playSound(AssetsNames.JUMP_SOUND);
                }
            }
        }
    }

    private void handleFalling() {
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

    private void handleDashing() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && resourcesManager.hasEnergy(attack4.getEnergyConsumption())) {
            boolean hasDashed = movementController.tryDash();
            if (hasDashed)
                attack(attack4,AssetsNames.DASH_SOUND, HeroAnimator.State.DASH);
        }
    }

    private void handleIdle() {
        if (groundTouchListener.isOnSurface() && noInput() && movementController.isBodyEffectivelyIdle()) {
            body.setLinearVelocity(0, level.world.getGravity().y * 0.4f);
            animator.setState(HeroAnimator.State.IDLE);
        }
    }

    private boolean noInput() {
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
//        animator.blockAnimationReset();
        SoundPlayer.getInstance().playSound(AssetsNames.HERO_HURT_SOUND);
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
//        animator.blockAnimationReset();
        SoundPlayer.getInstance().playSound(AssetsNames.HERO_HURT_SOUND);
        healthLossCount = Integer.MAX_VALUE;
        new DelayedAction(getDeathDelay(), MyGdxGame.getInstance()::levelFailed);
    }

    @Override
    public void dispose() {
        attack1.dispose();
        attack2.dispose();
        attack3.dispose();
        attack4.dispose();
    }
}