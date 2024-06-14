package com.mygdx.game.entities.enemies;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.BatEnemyAnimator;
import com.mygdx.game.animation.concrete.enemies.EnemyAnimator;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.sensors.AttackRangeSensor;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.entities.attacks.concrete.BatAttack;
import com.mygdx.game.map.data.EnemyData;
import com.mygdx.game.utils.Assets;

/**
 * Represents a specific type of enemy in the game - BatEnemy.
 * Extends the {@link Enemy} class and includes specific behaviors and attributes
 * tailored for the bat enemy.
 */
public class BatEnemy extends Enemy {

    /**
     * Constructs a BatEnemy object with the specified parameters.
     *
     * @param level The level in which the enemy exists.
     * @param enemyData Data defining specific characteristics of this enemy type.
     * @param width The width of the bat enemy.
     * @param height The height of the bat enemy.
     */
    public BatEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new BatEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.attack = new BatAttack(this);
        this.detectionRange = 8f;
        this.leftAttackRange = new AttackRangeSensor(this, AttackZonePosition.LEFT_MIDDLE, 6f, height);
        this.rightAttackRange = new AttackRangeSensor(this, AttackZonePosition.RIGHT_MIDDLE, 6f, height);
        this.attackAnimation = EnemyAnimator.State.ATTACK_2;
        this.attackSound = Assets.Sound.BAT_ATTACK_SOUND;
        this.healthLossSound = Assets.Sound.BAT_HEALTH_LOSS_SOUND;
        this.deathSound = Assets.Sound.BAT_DEATH_SOUND;
    }

    /**
     * Retrieves the delay in seconds before the bat enemy's death animation completes.
     *
     * @return The death delay in seconds.
     */
    @Override
    public float getDeathDelay() {
        return 0.61f;
    }

    /**
     * Retrieves the number of souls or points awarded upon defeating the bat enemy.
     *
     * @return The number of souls awarded.
     */
    @Override
    public int getSouls() {
        return 1;
    }
}
