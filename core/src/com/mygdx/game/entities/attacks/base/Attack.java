package com.mygdx.game.entities.attacks.base;

public interface Attack {
    void execute();
    float getAttackTime();
    float getAttackDelay();
    default float getEnergyConsumption() {
        return 0;
    }
}
