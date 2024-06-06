package com.mygdx.game.entities.attacks.base;

import com.mygdx.game.entities.ICollisionListener;

public interface Attack {
    void execute();
    float getAttackTime();
    float getAttackDelay();
    default float getEnergyConsumption() {
        return 0;
    }
}
