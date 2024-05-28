package com.mygdx.game.utils;

import com.badlogic.gdx.math.Rectangle;

public class EnemyData {
    public enum Type {
        //Should be changed, when concrete enemies will be added
        DEFAULT,
    }

    private final Rectangle spawnPoint;
    private final Rectangle travelArea;
    private final Type type;

    public EnemyData(Rectangle spawnPoint, Rectangle travelArea, String type) {
        this.spawnPoint = spawnPoint;
        this.travelArea = travelArea;
        this.type = Type.valueOf(type.toUpperCase());
    }

    public Rectangle getSpawnPoint() {
        return spawnPoint;
    }

    public Rectangle getTravelArea() {
        return travelArea;
    }

    public Type getType() {
        return type;
    }
}
