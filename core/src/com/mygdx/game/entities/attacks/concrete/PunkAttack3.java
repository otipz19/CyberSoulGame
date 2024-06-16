package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.base.HeroMeleeAttack;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;

import java.util.function.Consumer;

/**
 * PunkAttack3 represents a specific melee attack performed by a Punk hero entity.
 * It extends the HeroMeleeAttack class and defines the attributes and behavior
 * specific to this type of melee attack.
 */
public class PunkAttack3 extends HeroMeleeAttack {

    /**
     * The impulse strength applied to enemies upon collision during this attack.
     */
    private final static float ATTACK_IMPULSE = 40f;

    /**
     * The hero entity performing the attack.
     */
    private Hero hero;

    /**
     * Constructs a PunkAttack3 instance for the specified hero entity.
     *
     * @param hero The hero entity performing the attack.
     */
    public PunkAttack3(Hero hero) {
        super(hero, AttackZonePosition.LEFT_BOTTOM, AttackZonePosition.RIGHT_BOTTOM);
        this.hero = hero;
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
        return 0.25f;
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
        return 0.6f;
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
     * If the other entity is an Enemy, applies instant damage and an impulse force based on the attack direction.
     *
     * @param other The entity with which collision occurs.
     */
    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Enemy enemy) {
            // Apply instant damage to the enemy based on the damage multiplier of the hero
            enemy.addResourcesEffect(new AbsoluteInstantDamageEffect<>(10 * getDamageMultiplier()));

            // Add an action to apply an impulse force to the enemy upon health change
            enemy.addOnHealthChangeAction(d -> {
                if (hero.getMovementController().isFacingRight())
                    enemy.getMovementController().applyImpulse(ATTACK_IMPULSE, 0f);
                else
                    enemy.getMovementController().applyImpulse(-ATTACK_IMPULSE, 0f);
                return true;
            });
        }
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
