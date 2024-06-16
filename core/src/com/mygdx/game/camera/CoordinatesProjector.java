package com.mygdx.game.camera;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * CoordinatesProjector handles the conversion of coordinates between world and screen space.
 */
public class CoordinatesProjector {
    private float unitScale;
    private float screenHeight;

    /**
     * Constructs a CoordinatesProjector with the given unit scale and screen height.
     *
     * @param unitScale    The scale factor for converting world units to screen units.
     * @param screenHeight The height of the screen in pixels.
     */
    public CoordinatesProjector(float unitScale, float screenHeight) {
        this.unitScale = unitScale;
        this.screenHeight = screenHeight;
    }

    /**
     * Returns the unit scale used for converting world units to screen units.
     *
     * @return The unit scale.
     */
    public float getUnitScale() {
        return unitScale;
    }

    /**
     * Sets the unit scale used for converting world units to screen units.
     *
     * @param unitScale The new unit scale value.
     */
    public void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
    }

    /**
     * Returns the screen height in pixels.
     *
     * @return The screen height.
     */
    public float getScreenHeight() {
        return screenHeight;
    }

    /**
     * Sets the screen height in pixels.
     *
     * @param screenHeight The new screen height value.
     */
    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Projects a world space vector to screen space.
     *
     * @param vector2 The vector in world coordinates to project.
     * @return The projected vector in screen coordinates.
     */
    public Vector2 project(Vector2 vector2) {
        vector2.x /= unitScale;
        vector2.y = screenHeight - vector2.y / unitScale;
        return vector2;
    }

    /**
     * Unprojects a screen space rectangle to world space.
     *
     * @param rectangle The rectangle in screen coordinates to unproject.
     * @return The unprojected rectangle in world coordinates.
     */
    public Rectangle unproject(Rectangle rectangle) {
        Vector2 pos = unproject(rectangle.x, rectangle.y + rectangle.height);
        Vector2 size = toWorldSize(rectangle.width, rectangle.height);
        return new Rectangle(pos.x, pos.y, size.x, size.y);
    }

    /**
     * Unprojects a screen space vector to world space.
     *
     * @param vector2 The vector in screen coordinates to unproject.
     * @return The unprojected vector in world coordinates.
     */
    public Vector2 unproject(Vector2 vector2) {
        vector2.x *= unitScale;
        vector2.y = (screenHeight - vector2.y) * unitScale;
        return vector2;
    }

    /**
     * Unprojects screen space coordinates (x, y) to world space.
     *
     * @param x The x-coordinate in screen coordinates.
     * @param y The y-coordinate in screen coordinates.
     * @return The unprojected vector in world coordinates.
     */
    public Vector2 unproject(float x, float y) {
        return unproject(new Vector2(x, y));
    }

    /**
     * Converts screen size (width, height) to world size.
     *
     * @param width  The width in screen coordinates.
     * @param height The height in screen coordinates.
     * @return The size in world coordinates.
     */
    public Vector2 toWorldSize(float width, float height) {
        return new Vector2(toWorldSize(width), toWorldSize(height));
    }

    /**
     * Converts screen size from a rectangle to world size.
     *
     * @param rectangle The rectangle representing size in screen coordinates.
     * @return The size in world coordinates.
     */
    public Vector2 toWorldSize(Rectangle rectangle) {
        return toWorldSize(rectangle.width, rectangle.height);
    }

    /**
     * Converts a screen value to world size using the unit scale.
     *
     * @param screenValue The value in screen coordinates.
     * @return The value in world coordinates.
     */
    public float toWorldSize(float screenValue) {
        return screenValue * unitScale;
    }
}
