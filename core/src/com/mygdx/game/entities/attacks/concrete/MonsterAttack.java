package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
import com.mygdx.game.entities.attacks.AttackZonePosition;
/**
 * MonsterAttack represents a melee attack performed by a monster entity.
 * It extends the SideMeleeAttack class and defines the behavior and attributes specific
 * to this type of attack.
 */
public class MonsterAttack extends SideMeleeAttack {

    /**
     * Constructs a MonsterAttack instance for the specified attacker entity.
     *
     * @param attacker The entity performing the melee attack.
     */
    public MonsterAttack(Enemy attacker){
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
        return 0.09f;
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
        return 0.3f;
    }

    /**
     * Retrieves the height of the attack zone.
     *
     * @return The height of the attack zone in game units.
     */
    @Override
    public float getAttackHeight() {
        return attacker.getHeight();
    }

    /**
     * Handles collision enter events with other entities.
     * If the collided entity is a Hero, it applies an absolute instant damage effect.
     *
     * @param other The entity with which collision occurred.
     */
    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Hero hero)
            hero.addResourcesEffect(new AbsoluteInstantDamageEffect<>(40));
    }

    /**
     * Handles collision exit events with other entities.
     *
     * @param other The entity with which collision stopped.
     */
    @Override
    public void onCollisionExit(Entity other) {
    }
}
