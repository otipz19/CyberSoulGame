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
/**
 * Represents a powerful boss enemy in the game, extending the {@link Enemy} class.
 * This enemy type has both ranged and melee attacks, with specific attack ranges,
 * animations, and sounds associated with each attack type.
 */
public class BossEnemy extends Enemy {

    /**
     * Sensor for detecting melee attack range on the left side of the boss enemy.
     */
    protected AttackRangeSensor leftMeleeAttackRange;

    /**
     * Sensor for detecting melee attack range on the right side of the boss enemy.
     */
    protected AttackRangeSensor rightMeleeAttackRange;

    /**
     * The ranged attack of the boss enemy.
     */
    protected Attack attack;

    /**
     * The melee attack of the boss enemy.
     */
    protected Attack attackMelee;

    /**
     * Animation state for the melee attack of the boss enemy.
     */
    protected EnemyAnimator.State attackAnimationMelee;

    /**
     * Sound played during the melee attack of the boss enemy.
     */
    protected String attackSoundMelee;

    /**
     * Constructs a BossEnemy object with specified parameters.
     *
     * @param level The level in which the boss enemy exists.
     * @param enemyData Data defining specific characteristics of this boss enemy type.
     * @param width The width of the boss enemy.
     * @param height The height of the boss enemy.
     */
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
        this.attackSoundMelee = Assets.Sound.BOSS_ATTACK_SOUND;
        this.healthLossSound = Assets.Sound.BOSS_HEALTH_LOSS_SOUND;
        this.deathSound = Assets.Sound.BOSS_DEATH_SOUND;
    }

    /**
     * Attempts to perform an attack based on the player's position and distance.
     * If the player is within melee attack range, initiates a melee attack; otherwise,
     * switches to attack mode and attempts to move towards the player for ranged attacks.
     *
     * @param playerPosition The position of the player.
     * @param distanceToPlayer The distance between the boss enemy and the player.
     */
    protected void attemptAttack(Vector2 playerPosition, float distanceToPlayer) {
        if (!leftAttackRange.isHeroInRange() && !rightAttackRange.isHeroInRange()) {
            movementController.changeToAttackMode();
            movementController.tryMoveTo(playerPosition);
        } else {
            if (attackInterval > 0) {
                animator.setState(EnemyAnimator.State.IDLE);
                movementController.setFacingRight(player.getBody().getPosition().x > body.getPosition().x);
                return;
            }

            movementController.clearVelocityX();
            if (leftMeleeAttackRange.isHeroInRange() || rightMeleeAttackRange.isHeroInRange()) {
                attackDelay = attackMelee.getAttackTime() + 0.1f;
                attackInterval = attackMelee.getAttackInterval() + attackDelay;
                level.addDelayedAction(0.1f, () -> attack(attackMelee, attackAnimationMelee, attackSoundMelee));
            } else {
                attackDelay = attack.getAttackTime() + 0.1f;
                attackInterval = attack.getAttackInterval() + attackDelay;
                level.addDelayedAction(0.1f, this::attack);
            }
        }
    }

    /**
     * Retrieves the delay in seconds before the boss enemy's death animation completes.
     *
     * @return The death delay in seconds.
     */
    @Override
    public float getDeathDelay() {
        return 0.61f;
    }

    /**
     * Retrieves the number of souls or points awarded upon defeating the boss enemy.
     *
     * @return The number of souls awarded.
     */
    @Override
    public int getSouls() {
        return 3;
    }
}
