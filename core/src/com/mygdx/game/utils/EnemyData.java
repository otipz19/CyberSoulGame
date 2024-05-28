package com.mygdx.game.utils;

import com.badlogic.gdx.math.Rectangle;

public class EnemyData {
    public enum Type {
        //Should be changed, when concrete enemies will be added
        DEFAULT,
    }

    private Rectangle travelArea;
    private Rectangle spawnPoint;
    private Type type;

    public EnemyData(Rectangle travelArea, Rectangle spawnPoint, String type) {

    }
}
