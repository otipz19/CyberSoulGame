package com.mygdx.game.entities.attacks;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;

public enum ProjectileSpawnPoint {
    TOP_LEFT {
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x - projectileWidth, parentPosition.y + parent.getHeight() - projectileHeight);
        }
    },
    MIDDLE_LEFT {
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x - projectileWidth, parentPosition.y + parent.getHeight() * 0.5f - projectileHeight * 0.5f);
        }
    },
    BOTTOM_LEFT {
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x - projectileWidth, parentPosition.y);
        }
    },
    TOP_RIGHT {
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x + parent.getWidth(), parentPosition.y + parent.getHeight() - projectileHeight);
        }
    },
    MIDDLE_RIGHT {
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x + parent.getWidth(), parentPosition.y +  + parent.getHeight() * 0.5f - projectileHeight * 0.5f);
        }
    },
    BOTTOM_RIGHT {
        public Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight) {
            Vector2 parentPosition = parent.getPosition();
            return new Vector2(parentPosition.x + parent.getWidth(), parentPosition.y);
        }
    };

    public abstract Vector2 getPoint(Entity parent, float projectileWidth, float projectileHeight);
}
