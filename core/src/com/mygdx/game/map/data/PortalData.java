package com.mygdx.game.map.data;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

public class PortalData {
    public enum Type {
        FIRST,
        SECOND,
        THIRD,
        LAST
    }

    private final Rectangle bounds;
    private final Type type;
    private final MyGdxGame.Levels destination;
    private final boolean isEnabled;

    public PortalData(Rectangle bounds, String type, String destination, String isEnabled) {
        this.bounds = bounds;
        try {
            this.type = Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid portal type!", ex);
        }
        try {
            this.destination = MyGdxGame.Levels.valueOf(destination.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid portal destination!", ex);
        }
        this.isEnabled = Boolean.parseBoolean(isEnabled);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Type getType() {
        return type;
    }

    public MyGdxGame.Levels getDestination() {
        return destination;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
