package com.mygdx.game.entities.attacks;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
/**
 * Enum representing various spawn points for projectiles relative to their parent entity's position and dimensions.
 */
public enum ProjectileSpawnPoint {

    /**
     * Top-left spawn point for projectiles.
     */
    TOP_LEFT {
        /**
         * Computes the exact spawn point for projectiles at the top-left corner of the parent entity.
         *
         * @param parent The parent entity from which the projectile spawns.
         * @param projectileWidth The width of the projectile.
         * @param projectileHeight The height of the projectile.
         * @return The vector representing the spawn point.
         */
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x - projectileWidth, parentPosition.y + parent.getHeight() - projectileHeight);
        }
    },

    /**
     * Middle-left spawn point for projectiles.
     */
    MIDDLE_LEFT {
        /**
         * Computes the exact spawn point for projectiles at the middle-left of the parent entity.
         *
         * @param parent The parent entity from which the projectile spawns.
         * @param projectileWidth The width of the projectile.
         * @param projectileHeight The height of the projectile.
         * @return The vector representing the spawn point.
         */
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x - projectileWidth, parentPosition.y + parent.getHeight() * 0.5f - projectileHeight * 0.5f);
        }
    },

    /**
     * Bottom-left spawn point for projectiles.
     */
    BOTTOM_LEFT {
        /**
         * Computes the exact spawn point for projectiles at the bottom-left of the parent entity.
         *
         * @param parent The parent entity from which the projectile spawns.
         * @param projectileWidth The width of the projectile.
         * @param projectileHeight The height of the projectile.
         * @return The vector representing the spawn point.
         */
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x - projectileWidth, parentPosition.y + projectileHeight * 0.1f);
        }
    },

    /**
     * Top-right spawn point for projectiles.
     */
    TOP_RIGHT {
        /**
         * Computes the exact spawn point for projectiles at the top-right corner of the parent entity.
         *
         * @param parent The parent entity from which the projectile spawns.
         * @param projectileWidth The width of the projectile.
         * @param projectileHeight The height of the projectile.
         * @return The vector representing the spawn point.
         */
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x + parent.getWidth(), parentPosition.y + parent.getHeight() - projectileHeight);
        }
    },

    /**
     * Middle-right spawn point for projectiles.
     */
    MIDDLE_RIGHT {
        /**
         * Computes the exact spawn point for projectiles at the middle-right of the parent entity.
         *
         * @param parent The parent entity from which the projectile spawns.
         * @param projectileWidth The width of the projectile.
         * @param projectileHeight The height of the projectile.
         * @return The vector representing the spawn point.
         */
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x + parent.getWidth(), parentPosition.y + parent.getHeight() * 0.5f - projectileHeight * 0.5f);
        }
    },

    /**
     * Bottom-right spawn point for projectiles.
     */
    BOTTOM_RIGHT {
        /**
         * Computes the exact spawn point for projectiles at the bottom-right of the parent entity.
         *
         * @param parent The parent entity from which the projectile spawns.
         * @param projectileWidth The width of the projectile.
         * @param projectileHeight The height of the projectile.
         * @return The vector representing the spawn point.
         */
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x + parent.getWidth(), parentPosition.y + projectileHeight * 0.1f);
        }
    };

    /**
     * Abstract method to be implemented by each enum constant to calculate the spawn point of projectiles.
     *
     * @param parent The parent entity from which the projectile spawns.
     * @param projectileWidth The width of the projectile.
     * @param projectileHeight The height of the projectile.
     * @return The vector representing the spawn point.
     */
    public abstract Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight);
}
