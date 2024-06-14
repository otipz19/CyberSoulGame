package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
/**
 * SensorPosition defines various positions and shapes for sensor colliders
 * relative to the dimensions of a parent entity.
 */
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
    /**
     * Constructs a SensorPosition with specified multipliers and coefficients.
     *
     * @param widthMultiplier Multiplier for the width of the sensor.
     * @param heightMultiplier Multiplier for the height of the sensor.
     * @param deltaXCoefficient Coefficient for the x-axis position of the sensor.
     * @param deltaYCoefficient Coefficient for the y-axis position of the sensor.
     */
    SensorPosition(float widthMultiplier, float heightMultiplier, float deltaXCoefficient, float deltaYCoefficient) {
        this.widthMultiplier = widthMultiplier;
        this.heightMultiplier = heightMultiplier;
        this.deltaXCoefficient = deltaXCoefficient;
        this.deltaYCoefficient = deltaYCoefficient;
    }
    /**
     * Returns a PolygonShape representing the collider shape for the sensor
     * relative to the parent entity's dimensions.
     *
     * @param parentWidth The width of the parent entity.
     * @param parentHeight The height of the parent entity.
     * @return The PolygonShape representing the collider shape of the sensor.
     */
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
