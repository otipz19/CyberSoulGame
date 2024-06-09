package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.BossEnemyAnimator;
import com.mygdx.game.animation.concrete.enemies.EnemyAnimator;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.base.Attack;
import com.mygdx.game.entities.attacks.concrete.BossAttack;
import com.mygdx.game.entities.attacks.concrete.BossAttackMelee;
import com.mygdx.game.entities.sensors.AttackRangeSensor;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.EnemyData;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.DelayedAction;

public class BossEnemy extends Enemy{
    protected AttackRangeSensor leftMeleeAttackRange;
    protected AttackRangeSensor rightMeleeAttackRange;
    protected Attack attackMelee;
    protected EnemyAnimator.State attackAnimationMelee;
    protected String attackSoundMelee;
    public BossEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new BossEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.detectionRange = 8f;

        this.attack = new BossAttack(this);
        this.leftAttackRange = new AttackRangeSensor(this, AttackZonePosition.LEFT_MIDDLE, 5f, height);
        this.rightAttackRange = new AttackRangeSensor(this, AttackZonePosition.RIGHT_MIDDLE, 5f, height);
        this.attackAnimation = EnemyAnimator.State.ATTACK_3;

        BossAttackMelee bossAttack = new BossAttackMelee(this);
        this.attackMelee = bossAttack;
        this.leftMeleeAttackRange = new AttackRangeSensor(this, AttackZonePosition.LEFT_MIDDLE, bossAttack.getAttackWidth(), bossAttack.getAttackHeight());
        this.rightMeleeAttackRange = new AttackRangeSensor(this, AttackZonePosition.RIGHT_MIDDLE, bossAttack.getAttackWidth(), bossAttack.getAttackHeight());
        this.attackAnimationMelee = EnemyAnimator.State.ATTACK_2;

        this.attackSound = Assets.Sound.BOSS_RANGED_ATTACK_SOUND;
        this.attackSoundMelee= Assets.Sound.BOSS_ATTACK_SOUND;
        this.healthLossSound = Assets.Sound.BOSS_HEALTH_LOSS_SOUND;
        this.deathSound = Assets.Sound.BOSS_DEATH_SOUND;
    }

    protected void attemptAttack(Vector2 playerPosition, float distanceToPlayer) {
         if(!leftAttackRange.isHeroInRange() && !rightAttackRange.isHeroInRange()) {
            movementController.changeToAttackMode();
            movementController.tryMoveTo(playerPosition);
        }else {
            movementController.clearVelocityX();
            if (leftMeleeAttackRange.isHeroInRange() || rightMeleeAttackRange.isHeroInRange()) {
                attackDelay = attackMelee.getAttackTime() + 0.1f;
                new DelayedAction(0.1f, () -> attack(attackMelee, attackAnimationMelee, attackSoundMelee));
            }
            else {
                attackDelay = attack.getAttackTime() + 0.1f;
                new DelayedAction(0.1f, this::attack);
            }
        }
    }

    @Override
    public float getDeathDelay() {
        return 0.61f;
    }

    @Override
    public int getSouls() {
        return 3;
    }
}
