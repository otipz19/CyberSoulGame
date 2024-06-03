package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.animation.Animator;
import com.mygdx.game.animation.EnemyAnimator;
import com.mygdx.game.animation.HeroAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class Enemy extends Entity {
    private EnemyData enemyData;
    private float minX, maxX;
    private float attackRange = 1.5f;
    private Hero player;
    private float patrolSpeed = 2.0f;
    private float attackSpeed = 3.0f;
    private float detectionRange = 4f;
    private boolean movingRight = true;
    public Enemy(Level level, EnemyData enemyData, float x, float y, float width, float height, float minX, float maxX, Hero player) {
        this.level = level;
        this.enemyData = enemyData;
        this.animator = new EnemyAnimator();
        this.width = width;
        this.height = height;
        this.minX = minX;
        this.maxX = maxX;
        this.player = player;

        Collider collider = ColliderCreator.create(new Rectangle(x, y, width, height));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;

        body = level.world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        body.setUserData(this);
        collider.dispose();
    }


    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        moveTowardsPlayer(deltaTime);
        animator.animate(level.game.batch, body.getPosition().x, body.getPosition().y, width, height);
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
        if(distanceToPlayer>1.1f)
            body.setLinearVelocity(direction.scl(attackSpeed));
        else {
            body.setLinearVelocity(0, 0);
            animator.setState(EnemyAnimator.State.ATTACK_2);
        }
        if(direction.x>0)
            animator.setDirection(EnemyAnimator.Direction.RIGHT);
        else
            animator.setDirection(EnemyAnimator.Direction.LEFT);
    }

}
