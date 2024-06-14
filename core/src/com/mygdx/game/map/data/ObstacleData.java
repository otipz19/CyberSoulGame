package com.mygdx.game.map.data;

import com.badlogic.gdx.math.Rectangle;
/**
 * Represents data about an obstacle in the game map, including its bounds and type.
 */
public class ObstacleData {
    public enum Type {
        ENTRY,
        HAMMER
    }

    private final Rectangle bounds;
    private final Type type;

    public ObstacleData(Rectangle bounds, String type) {
        this.bounds = bounds;
        this.type = Type.valueOf(type.toUpperCase());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Type getType() {
        return type;
    }
}
