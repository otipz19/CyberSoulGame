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

public class CarEnemy extends Enemy{
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
}
