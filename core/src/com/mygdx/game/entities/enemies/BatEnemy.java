package com.mygdx.game.entities.enemies;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.BatEnemyAnimator;
import com.mygdx.game.animation.concrete.enemies.EnemyAnimator;
import com.mygdx.game.entities.sensors.AttackRangeSensor;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.entities.attacks.concrete.BatAttack;
import com.mygdx.game.map.data.EnemyData;
import com.mygdx.game.utils.Assets;

public class BatEnemy extends Enemy{

    public BatEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new BatEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.attack = new BatAttack(this);
        this.leftAttackRange = new AttackRangeSensor(this, SensorPosition.LEFT);
        this.rightAttackRange = new AttackRangeSensor(this, SensorPosition.RIGHT);
        this.attackAnimation = EnemyAnimator.State.ATTACK_2;
        this.attackSound = Assets.Sound.BAT_ATTACK_SOUND;
        this.healthLossSound = Assets.Sound.BAT_HEALTH_LOSS_SOUND;
        this.deathSound = Assets.Sound.BAT_DEATH_SOUND;
    }
}
