package com.mygdx.game.map.data;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
/**
 * Represents data about a player spawn point in the game map, including its bounds and originating level.
 */
public class PlayerSpawnData {
    private Rectangle bounds;
    private MyGdxGame.Levels fromLevel;

    public PlayerSpawnData(Rectangle bounds, String fromLevel) {
        this.bounds = bounds;
        try{
            this.fromLevel = MyGdxGame.Levels.valueOf(fromLevel.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid level type!", ex);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public MyGdxGame.Levels getFromLevel() {
        return fromLevel;
    }
}
