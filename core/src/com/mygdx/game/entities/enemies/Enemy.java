package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.Animator;
import com.mygdx.game.animation.EnemyAnimator;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.attacks.EnemyAttack;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.EnemyResourcesManager;
import com.mygdx.game.entities.resources.ResourcesManager;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.EnemyData;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.utils.DelayedAction;

public class Enemy extends MortalEntity<ResourcesManager> {
    private EnemyData enemyData;
    private float minX, maxX;
    private float attackRange = 1.5f;
    private Hero player;
    private float patrolSpeed = 2.0f;
    private float attackSpeed = 3.0f;
    private float detectionRange = 4f;
    private boolean movingRight = true;
    private int healthLossCount;
    private EnemyAttack attack;
    private float attackDelay;

    public Enemy(Level level, EnemyData enemyData, float x, float y, float width, float height, float minX, float maxX) {
        super(new EnemyResourcesManager(100));

        this.level = level;
        this.enemyData = enemyData;
        this.animator = new EnemyAnimator();
        this.width = width;
        this.height = height;
        this.minX = minX;
        this.maxX = maxX;
        this.player = level.hero;

        Collider collider = ColliderCreator.create(new Rectangle(x, y, width, height));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = level.world.createBody(bodyDef);
        body.setFixedRotation(true);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0;

        Fixture fixture = body.createFixture(fixtureDef);

        fixture.setUserData(this);
        collider.dispose();

        attack = new EnemyAttack(this);
    }


    public void render(float deltaTime) {
        if (healthLossCount == 0 && attackDelay == 0)
            moveTowardsPlayer(deltaTime);
        attackDelay = Math.max(0, attackDelay - deltaTime);
        updateResourcesManager(deltaTime);
        animator.animate(MyGdxGame.getInstance().batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);
    }


    private void moveTowardsPlayer(float deltaTime) {
        Vector2 playerPosition = player.getBody().getPosition();
        Vector2 enemyPosition = body.getPosition();
        float distanceToPlayer = playerPosition.dst(enemyPosition);

        if (distanceToPlayer <= detectionRange) {
            attemptAttack(playerPosition,enemyPosition);
        } else {
            if(body.getLinearVelocity().x>0)
                animator.setDirection(EnemyAnimator.Direction.RIGHT);
            else
                animator.setDirection(EnemyAnimator.Direction.LEFT);

            animator.setState(EnemyAnimator.State.WALK);
            if (movingRight) {
                body.setLinearVelocity(patrolSpeed, 0);
                if (enemyPosition.x >= maxX) {
                    movingRight = false;
                    animator.setDirection(EnemyAnimator.Direction.LEFT);
                }
            } else {
                body.setLinearVelocity(-patrolSpeed, 0);
                if (enemyPosition.x <= minX) {
                    movingRight = true;
                    animator.setDirection(EnemyAnimator.Direction.RIGHT);
                }
            }
        }

        if (enemyPosition.x < minX) body.setTransform(minX, enemyPosition.y, 0);
        if (enemyPosition.x > maxX) body.setTransform(maxX, enemyPosition.y, 0);
    }

    private void attemptAttack(Vector2 playerPosition,Vector2 enemyPosition) {
        Vector2 direction = new Vector2((playerPosition.x - enemyPosition.x)/2, 0).nor();
        float distanceToPlayer = playerPosition.dst(enemyPosition);
        if(distanceToPlayer > 1.1f) {
            animator.setState(EnemyAnimator.State.WALK);
            body.setLinearVelocity(direction.scl(attackSpeed));
        }
        else
            new DelayedAction(0.1f, this::attack);
        if(direction.x>0)
            animator.setDirection(EnemyAnimator.Direction.RIGHT);
        else
            animator.setDirection(EnemyAnimator.Direction.LEFT);
    }

    private void attack() {
        if (healthLossCount != 0)
            return;
        animator.setState(EnemyAnimator.State.ATTACK_2);
        attackDelay = attack.getAttackTime();
        attack.setDirection(animator.getDirection() == Animator.Direction.RIGHT);
        attack.execute();
        clearVelocityX();
    }

    @Override
    protected void onNonKillingHealthLoss() {
        animator.setState(EnemyAnimator.State.HURT);
        animator.blockAnimationReset();
        healthLossCount++;
        clearVelocityX();
        new DelayedAction(0.4f, () -> { healthLossCount--; animator.setState(EnemyAnimator.State.WALK); });
    }

    private void clearVelocityX() {
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(0, oldVelocity.y);
    }

    @Override
    public float getDeathDelay() {
        return 0.55f;
    }

    @Override
    protected void onDeath() {
        animator.setState(EnemyAnimator.State.DEATH);
        animator.blockAnimationReset();
        healthLossCount = Integer.MAX_VALUE;
        clearVelocityX();
        new DelayedAction(getDeathDelay(), () -> level.world.destroyBody(body));
    }

}
