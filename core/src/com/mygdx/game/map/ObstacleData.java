package com.mygdx.game.utils;

import com.badlogic.gdx.math.Rectangle;

public class ObstacleData {
    public enum Type {
        ENTRY,
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