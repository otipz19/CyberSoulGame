package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public enum SensorPosition {
    BOTTOM(0.49f, 0.05f, 0.5f, 0f),
    TOP(0.49f, 0.05f, 0.5f, 1f),
    LEFT(0.05f, 0.49f, 0f, 0.5f),
    RIGHT(0.05f, 0.49f, 1f, 0.5f),
    SIDES(0.51f, 0.49f, 0.5f, 0.5f),
    TOP_AND_BOTTOM(0.49f, 0.51f, 0.5f, 0.5f),
    INSIDE(0.49f, 0.49f, 0.5f, 0.5f),
    SLIM_INSIDE(0.45f, 0.45f, 0.5f, 0.5f),
    OUTSIDE(0.51f, 0.51f, 0.5f, 0.5f),
    THICK_OUTSIDE(0.55f, 0.55f, 0.5f, 0.5f);

    private final float widthMultiplier;
    private final float heightMultiplier;
    private final float deltaXCoefficient;
    private final float deltaYCoefficient;

    SensorPosition(float widthMultiplier, float heightMultiplier, float deltaXCoefficient, float deltaYCoefficient) {
        this.widthMultiplier = widthMultiplier;
        this.heightMultiplier = heightMultiplier;
        this.deltaXCoefficient = deltaXCoefficient;
        this.deltaYCoefficient = deltaYCoefficient;
    }

    public Shape getColliderShape(float parentWidth, float parentHeight) {
        PolygonShape shape = new PolygonShape();
        float hw = parentWidth * widthMultiplier;
        float hh = parentHeight * heightMultiplier;
        float x = parentWidth * deltaXCoefficient;
        float y = parentHeight * deltaYCoefficient;
        shape.setAsBox(hw, hh, new Vector2(x, y), 0);
        return shape;
    }
}
