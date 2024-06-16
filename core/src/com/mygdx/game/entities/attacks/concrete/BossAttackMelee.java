package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
import com.mygdx.game.entities.attacks.AttackZonePosition;
/**
 * BossAttackMelee represents a specific melee attack performed by a boss enemy entity.
 * It extends the SideMeleeAttack class and defines the behavior and attributes specific
 * to this type of attack.
 */
public class BossAttackMelee extends SideMeleeAttack {

    /**
     * Constructs a BossAttackMelee instance for the specified attacker enemy entity.
     *
     * @param attacker The enemy entity performing the melee attack.
     */
    public BossAttackMelee(Enemy attacker){
        super(attacker, AttackZonePosition.LEFT_MIDDLE, AttackZonePosition.RIGHT_MIDDLE);
    }

    /**
     * Retrieves the duration of the attack execution.
     *
     * @return The attack duration in seconds.
     */
    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    /**
     * Retrieves the delay before the attack executes.
     *
     * @return The delay duration in seconds.
     */
    @Override
    public float getAttackDelay() {
        return 0.2f;
    }

    /**
     * Retrieves the interval between successive attacks.
     *
     * @return The interval duration in seconds.
     */
    @Override
    public float getAttackInterval() {
        return 0.3f;
    }

    /**
     * Retrieves the width of the attack zone.
     *
     * @return The width of the attack zone in game units.
     */
    @Override
    public float getAttackWidth() {
        return 0.5f;
    }

    /**
     * Retrieves the height of the attack zone, which matches the height of the attacker.
     *
     * @return The height of the attack zone in game units.
     */
    @Override
    public float getAttackHeight() {
        return attacker.getHeight();
    }

    /**
     * Handles the collision event when another entity enters the attack zone.
     * If the colliding entity is a Hero, applies an absolute instant damage effect to it.
     *
     * @param other The entity that collided with the attack zone.
     */
    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Hero hero)
            hero.addResourcesEffect(new AbsoluteInstantDamageEffect<>(40));
    }

    /**
     * Handles the collision event when another entity exits the attack zone.
     *
     * @param other The entity that exited the attack zone.
     */
    @Override
    public void onCollisionExit(Entity other) {
    }
}
