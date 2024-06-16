package com.mygdx.game.entities.attacks.base;

import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.heroes.Hero;

/**
 * HeroMeleeAttack extends SideMeleeAttack and implements functionality for melee attacks performed by hero entities.
 */
public abstract class HeroMeleeAttack extends SideMeleeAttack {
    private final Hero hero;

    /**
     * Constructs a HeroMeleeAttack with the specified hero and attack zone positions.
     *
     * @param hero                    The hero initiating the attack.
     * @param leftAttackZonePosition  The position of the left attack zone.
     * @param rightAttackZonePosition The position of the right attack zone.
     */
    public HeroMeleeAttack(Hero hero, AttackZonePosition leftAttackZonePosition, AttackZonePosition rightAttackZonePosition) {
        super(hero, leftAttackZonePosition, rightAttackZonePosition);
        this.hero = hero;
    }

    /**
     * Retrieves the damage multiplier applied to the hero's melee attacks.
     *
     * @return The damage multiplier for the hero's attacks.
     */
    protected float getDamageMultiplier() {
        return hero.getResourcesManager().getDamageMultiplier();
    }

    /**
     * Returns the attack interval for the hero's melee attack.
     * Override this method to specify the interval between consecutive attacks.
     *
     * @return The attack interval for the hero's melee attack.
     */
    @Override
    public float getAttackInterval() {
        return 0f;
    }
}
