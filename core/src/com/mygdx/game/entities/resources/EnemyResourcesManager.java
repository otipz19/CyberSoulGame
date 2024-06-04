package com.mygdx.game.entities.resources;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.entities.heroes.HeroData;

public class EnemyResourcesManager extends ResourcesManager {
    public EnemyResourcesManager(float maxHealth) {
        super(maxHealth, maxHealth);
    }
}
