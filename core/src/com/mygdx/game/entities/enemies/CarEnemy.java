package com.mygdx.game.entities.enemies;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.CarEnemyAnimator;
import com.mygdx.game.animation.concrete.enemies.EnemyAnimator;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.concrete.CarAttack;
import com.mygdx.game.entities.sensors.AttackRangeSensor;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.EnemyData;
import com.mygdx.game.utils.Assets;
/**
 * Represents an enemy type in the game - CarEnemy.
 * Extends the {@link Enemy} class and includes specific behaviors and attributes
 * tailored for the car enemy.
 */
public class CarEnemy extends Enemy {

    /**
     * Constructs a CarEnemy object with specified parameters.
     *
     * @param level The level in which the car enemy exists.
     * @param enemyData Data defining specific characteristics of this car enemy type.
     * @param width The width of the car enemy.
     * @param height The height of the car enemy.
     */
    public CarEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new CarEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.attack = new CarAttack(this);
        this.detectionRange = 9f;
        this.leftAttackRange = new AttackRangeSensor(this, AttackZonePosition.LEFT_MIDDLE, 7f, height);
        this.rightAttackRange = new AttackRangeSensor(this, AttackZonePosition.RIGHT_MIDDLE, 7f, height);
        this.attackAnimation = EnemyAnimator.State.ATTACK_2;
        this.attackSound = Assets.Sound.CAR_ATTACK_SOUND;
        this.healthLossSound = Assets.Sound.CAR_HEALTH_LOSS_SOUND;
        this.deathSound = Assets.Sound.CAR_DEATH_SOUND;
    }

    /**
     * Retrieves the delay in seconds before the car enemy's death animation completes.
     *
     * @return The death delay in seconds.
     */
    @Override
    public float getDeathDelay() {
        return 0.61f;
    }

    /**
     * Retrieves the number of souls or points awarded upon defeating the car enemy.
     *
     * @return The number of souls awarded.
     */
    @Override
    public int getSouls() {
        return 1;
    }
}
