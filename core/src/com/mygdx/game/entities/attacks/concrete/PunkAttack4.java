package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.base.HeroMeleeAttack;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
/**
 * PunkAttack4 represents a specific melee attack performed by a Punk hero entity.
 * It extends the HeroMeleeAttack class and defines the attributes and behavior
 * specific to this type of melee attack.
 */
public class PunkAttack4 extends HeroMeleeAttack {

    /**
     * Constructs a PunkAttack4 instance for the specified hero entity.
     *
     * @param hero The hero entity performing the attack.
     */
    public PunkAttack4(Hero hero) {
        super(hero, AttackZonePosition.LEFT_MIDDLE, AttackZonePosition.RIGHT_MIDDLE);
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
        return 0f;
    }

    /**
     * Retrieves the width of the attack zone for collision detection.
     *
     * @return The width of the attack zone in game units.
     */
    @Override
    public float getAttackWidth() {
        return 0.3f;
    }

    /**
     * Retrieves the height of the attack zone for collision detection.
     *
     * @return The height of the attack zone in game units.
     */
    @Override
    public float getAttackHeight() {
        return 1f;
    }

    /**
     * Retrieves the energy consumption required for executing the attack.
     * Overrides the default implementation in HeroMeleeAttack to specify a custom energy consumption value.
     *
     * @return The energy consumption value in game units.
     */
    @Override
    public float getEnergyConsumption() {
        return 20f;
    }

    /**
     * Handles actions to perform when colliding with another entity.
     * If the other entity is an Enemy, applies instant damage based on the damage multiplier of the hero.
     *
     * @param other The entity with which collision occurs.
     */
    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Enemy enemy)
            enemy.addResourcesEffect(new AbsoluteInstantDamageEffect<>(15 * getDamageMultiplier()));
    }

    /**
     * Handles actions to perform when collision with another entity ends.
     * Currently does nothing for this specific attack.
     *
     * @param other The entity with which collision has ended.
     */
    @Override
    public void onCollisionExit(Entity other) {
        // No action needed on collision exit for this attack
    }
}
