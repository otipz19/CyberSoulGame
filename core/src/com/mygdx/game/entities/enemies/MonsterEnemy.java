package com.mygdx.game.entities.enemies;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.EnemyAnimator;
import com.mygdx.game.animation.concrete.enemies.MonsterEnemyAnimator;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.sensors.AttackRangeSensor;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.entities.attacks.concrete.MonsterAttack;
import com.mygdx.game.map.data.EnemyData;
import com.mygdx.game.utils.Assets;

public class MonsterEnemy extends Enemy{
    public MonsterEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new MonsterEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        MonsterAttack monsterAttack = new MonsterAttack(this);
        this.attack = monsterAttack;
        this.detectionRange = 5f;
        this.leftAttackRange = new AttackRangeSensor(this, AttackZonePosition.LEFT_MIDDLE, monsterAttack.getAttackWidth(), monsterAttack.getAttackHeight());
        this.rightAttackRange = new AttackRangeSensor(this, AttackZonePosition.RIGHT_MIDDLE, monsterAttack.getAttackWidth(), monsterAttack.getAttackHeight());
        this.attackAnimation = EnemyAnimator.State.ATTACK_2;
        this.attackSound = Assets.Sound.MONSTER_ATTACK_SOUND;
        this.healthLossSound = Assets.Sound.MONSTER_HEALTH_LOSS_SOUND;
        this.deathSound = Assets.Sound.MONSTER_DEATH_SOUND;
    }
}
