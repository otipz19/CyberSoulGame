package com.mygdx.game.entities;

import com.mygdx.game.entities.projectiles.ProjectileCollidable;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.BodyCreator;

/**
 * Surface is a class that extends GameObject and implements ProjectileCollidable,
 * representing a static surface entity in the game world.
 */
public class Surface extends GameObject implements ProjectileCollidable {

    /**
     * Constructs a Surface object with the specified level and collider.
     *
     * @param level The level in which the surface exists.
     * @param collider The collider defining the shape and size of the surface.
     */
    public Surface(Level level, Collider collider) {
        this.level = level;
        body = BodyCreator.createStaticBody(level.world, collider, 1, 1, 0);
        body.getFixtureList().first().setUserData(this);
    }
}
