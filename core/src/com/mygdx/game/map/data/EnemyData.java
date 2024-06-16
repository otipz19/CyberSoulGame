package com.mygdx.game.map.data;

import com.badlogic.gdx.math.Rectangle;
/**
 * Represents data about an enemy in the game, including its spawn point, travel area, and type.
 */
public class EnemyData {
    public enum Type {
        MONSTER,
        CAR,
        BAT,
        BOSS
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
