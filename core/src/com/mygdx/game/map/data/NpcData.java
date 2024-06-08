package com.mygdx.game.map.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class NpcData {
    public enum Type {
        MONK,
    }

    private final Type type;
    private final Rectangle bounds;

    public NpcData(Rectangle bounds, String type) {
        this.bounds = bounds;
        try {
            this.type = Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Unsupported NPC type! " + type);
        }
    }

    public Type getType() {
        return type;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
