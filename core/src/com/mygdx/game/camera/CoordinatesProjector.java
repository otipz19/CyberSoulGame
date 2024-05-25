package com.mygdx.game.camera;

import com.badlogic.gdx.math.Vector2;

public class CoordinatesProjector {
    private float unitScale;
    private float screenHeight;

    public CoordinatesProjector(float unitScale, float screenHeight) {
        this.unitScale = unitScale;
        this.screenHeight = screenHeight;
    }

    public float getUnitScale() {
        return unitScale;
    }

    public void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * world -> screen
     */
    public Vector2 project(Vector2 vector2){
        vector2.x /= unitScale;
        vector2.y = screenHeight - vector2.y / unitScale;
        return vector2;
    }

    /**
     * screen -> world
     */
    public Vector2 unproject(Vector2 vector2){
        vector2.x *= unitScale;
        vector2.y = (screenHeight - vector2.y) * unitScale;
        return vector2;
    }
}
