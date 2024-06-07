package com.mygdx.game.map.data;

import com.badlogic.gdx.math.Rectangle;

public class EnemyData {
    public enum Type {
        DEFAULT,
        MONSTER,
        CAR,
        BAT,
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
