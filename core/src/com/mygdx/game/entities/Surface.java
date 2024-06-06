package com.mygdx.game.entities;

import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.projectiles.ProjectileCollidable;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.BodyCreator;

public class Surface extends GameObject implements ProjectileCollidable {
    public Surface(Level level, Collider collider) {
        this.level = level;
        body = BodyCreator.createStaticBody(level.world, collider, 1, 1, 0);
        body.getFixtureList().first().setUserData(this);
    }
}
