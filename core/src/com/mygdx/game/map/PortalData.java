package com.mygdx.game.map;

import com.badlogic.gdx.math.Rectangle;

public class PortalData {
    public enum Type {
        FIRST,
        SECOND,
        THIRD
    }

    private static final Type[] TYPES = {Type.FIRST, Type.SECOND, Type.THIRD};

    private final Rectangle bounds;
    private final Type type;

    public PortalData(Rectangle bounds, String type) {
        this.bounds = bounds;
        try{
            int index = Integer.parseInt(type) - 1;
            this.type = TYPES[index];
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            throw new RuntimeException("Invalid portal type!", ex);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Type getType() {
        return type;
    }
}
