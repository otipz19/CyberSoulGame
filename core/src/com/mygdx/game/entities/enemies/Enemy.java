package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.EnemyAnimator;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.attacks.base.Attack;
import com.mygdx.game.entities.attacks.base.SideAttack;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.movement.GroundEnemyMovementController;
import com.mygdx.game.entities.particles.SoulParticles;
import com.mygdx.game.entities.projectiles.ProjectileCollidable;
import com.mygdx.game.entities.resources.EnemyResourcesManager;
import com.mygdx.game.entities.resources.ResourcesManager;
import com.mygdx.game.entities.sensors.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.EnemyData;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.DelayedAction;

public abstract class Enemy extends MortalEntity<ResourcesManager> implements ProjectileCollidable {
    //for 1x1 size
    private static final float BASE_DENSITY = 10f;

    protected final Hero player;
    protected final GroundEnemyMovementController movementController;
    protected float detectionRange;
    protected float attackDelay;
    protected Attack attack;
    protected AttackRangeSensor leftAttackRange;
    protected AttackRangeSensor rightAttackRange;
    protected EnemyAnimator.State attackAnimation;
    protected String attackSound;
    protected String healthLossSound;
    protected String deathSound;
    protected int healthLossCount;

    public Enemy(Level level, EnemyData enemyData, float width, float height) {
        super(new EnemyResourcesManager(100));

        this.level = level;
        this.width = width;
        this.height = height;
        this.player = level.hero;

        Rectangle spawnPointInWorldCoordinates = level.getCoordinatesProjector().unproject(enemyData.getSpawnPoint());
        Rectangle travelAreaInWorldCoordinates = level.getCoordinatesProjector().unproject(enemyData.getTravelArea());
        Collider collider = ColliderCreator.create(spawnPointInWorldCoordinates.x, spawnPointInWorldCoordinates.y, width, height);
        float density = BASE_DENSITY / (width * height);
        body = BodyCreator.createDynamicBody(level.world, collider, 0.3f, density, 0);
        body.setFixedRotation(true);
        body.getFixtureList().first().setUserData(this);

        movementController = new GroundEnemyMovementController(body,
                travelAreaInWorldCoordinates.x,
                travelAreaInWorldCoordinates.x + travelAreaInWorldCoordinates.width - width);

        new DefaultEnemyHeadSensor(this);
    }

    public void render(float deltaTime) {
        attackDelay = Math.max(0, attackDelay - deltaTime);
        if (healthLossCount == 0 && attackDelay == 0)
            move();
        updateResourcesManager(deltaTime);
        animator.animate(MyGdxGame.getInstance().batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);
    }

    private void move() {
        Vector2 playerPosition = player.getBody().getPosition();
        Vector2 enemyPosition = body.getPosition();
        float distanceToPlayer = playerPosition.dst(enemyPosition);

        if (distanceToPlayer <= detectionRange)
            attemptAttack(playerPosition, distanceToPlayer);
        else
            movementController.patrol();

        animator.setState(EnemyAnimator.State.WALK);
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);
    }

    private void attemptAttack(Vector2 playerPosition, float distanceToPlayer) {
        if(!leftAttackRange.isHeroInRange() && !rightAttackRange.isHeroInRange()) {
            movementController.changeToAttackMode();
            movementController.tryMoveTo(playerPosition);
        }
        else {
            movementController.clearVelocityX();
            attackDelay = attack.getAttackTime() + 0.1f;
            new DelayedAction(0.1f, this::attack);
        }
    }

    protected void attack() {
        if (healthLossCount != 0)
            return;

        SideAttack sideAttack = (SideAttack)attack;
        animator.setState(attackAnimation);
        SoundPlayer.getInstance().playSound(attackSound);
        sideAttack.setDirection(movementController.isFacingRight());
        sideAttack.execute();
    }

    @Override
    protected void onNonKillingHealthLoss() {
        animator.setState(EnemyAnimator.State.HURT);
        SoundPlayer.getInstance().playSound(healthLossSound);
        healthLossCount++;
        movementController.clearVelocityX();
        new DelayedAction(0.4f, () -> healthLossCount--);
    }

    @Override
    public float getDeathDelay() {
        return 0.55f;
    }

    @Override
    protected void onDeath() {
        animator.setState(EnemyAnimator.State.DEATH);
        SoundPlayer.getInstance().playSound(deathSound);
        healthLossCount = Integer.MAX_VALUE;
        movementController.clearVelocityX();
        new DelayedAction(getDeathDelay(), () ->
            {
                Vector2 middlePosition = getMiddlePosition();
                new SoulParticles(level, middlePosition.x, middlePosition.y, 1);
                level.world.destroyBody(body);
            });
    }

    public Vector2 getMiddlePosition() {
        return new Vector2(body.getPosition().x + width / 2, body.getPosition().y + height / 2);
    }

    public GroundEnemyMovementController getMovementController() {
        return movementController;
    }
}
