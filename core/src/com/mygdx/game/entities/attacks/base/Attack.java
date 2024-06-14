package com.mygdx.game.entities.attacks.base;
/**
 * Attack is an interface representing an attack action in the game.
 * It defines methods to execute the attack, retrieve attack timing information,
 * and optionally obtain energy consumption details.
 */
public interface Attack {
    void execute();
    float getAttackTime();
    float getAttackInterval();
    float getAttackDelay();
    default float getEnergyConsumption() {
        return 0;
    }
}
