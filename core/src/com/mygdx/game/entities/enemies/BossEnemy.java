package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.BossEnemyAnimator;
import com.mygdx.game.animation.concrete.enemies.EnemyAnimator;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.base.Attack;
import com.mygdx.game.entities.attacks.base.SideAttack;
import com.mygdx.game.entities.attacks.concrete.BossAttack;
import com.mygdx.game.entities.attacks.concrete.BossAttackMelee;
import com.mygdx.game.entities.attacks.concrete.MonsterAttack;
import com.mygdx.game.entities.sensors.AttackRangeSensor;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.EnemyData;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.DelayedAction;

public class BossEnemy extends Enemy{
    protected AttackRangeSensor leftAttack;
    protected AttackRangeSensor rightAttack;
    protected Attack attackMelee;
    protected EnemyAnimator.State attackAnimationMelee;
    protected String attackSoundMelee;
    public BossEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new BossEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.attack = new BossAttack(this);
        this.detectionRange = 8f;
        this.leftAttackRange = new AttackRangeSensor(this, AttackZonePosition.LEFT_MIDDLE, 6f, height);
        this.rightAttackRange = new AttackRangeSensor(this, AttackZonePosition.RIGHT_MIDDLE, 6f, height);

        BossAttackMelee bossAttack = new BossAttackMelee(this);
        this.attackMelee = bossAttack;
        this.leftAttack = new AttackRangeSensor(this, AttackZonePosition.LEFT_MIDDLE, bossAttack.getAttackWidth(), bossAttack.getAttackHeight());
        this.rightAttack = new AttackRangeSensor(this, AttackZonePosition.RIGHT_MIDDLE, bossAttack.getAttackWidth(), bossAttack.getAttackHeight());
        this.attackAnimationMelee = EnemyAnimator.State.ATTACK_2;
        this.attackSoundMelee= Assets.Sound.BOSS_ATTACK_SOUND;

        this.attackAnimation = EnemyAnimator.State.ATTACK_3;
        this.attackSound = Assets.Sound.BOSS_RANGED_ATTACK_SOUND;
        this.healthLossSound = Assets.Sound.BOSS_HEALTH_LOSS_SOUND;
        this.deathSound = Assets.Sound.BOSS_DEATH_SOUND;
    }
    protected void attemptAttack(Vector2 playerPosition, float distanceToPlayer) {
         if(((distanceToPlayer>1.3f&&distanceToPlayer<4f))||!leftAttackRange.isHeroInRange() && !rightAttackRange.isHeroInRange()) {
            movementController.changeToAttackMode();
            movementController.tryMoveTo(playerPosition);
        }else {
            movementController.clearVelocityX();
            if(distanceToPlayer > 4f) {
                attackDelay = attack.getAttackTime() + 0.1f;
                new DelayedAction(0.1f, this::attack);
            }
            else {
                attackDelay = attackMelee.getAttackTime() + 0.1f;
                new DelayedAction(0.1f, this::attack2);
            }
        }
    }
    protected void attack2() {
        if (healthLossCount != 0)
            return;

        SideAttack sideAttack = (SideAttack)attackMelee;
        animator.setState(attackAnimationMelee);
        SoundPlayer.getInstance().playSound(attackSoundMelee);
        sideAttack.setDirection(movementController.isFacingRight());
        sideAttack.execute();
    }
}
