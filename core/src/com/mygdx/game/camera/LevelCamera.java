package com.mygdx.game.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * LevelCamera extends OrthographicCamera to manage camera operations within a game level.
 */
public class LevelCamera extends OrthographicCamera {
    private final float levelWidth;
    private final float levelHeight;

    /**
     * Constructs a LevelCamera with the specified level width and height.
     *
     * @param levelWidth  The width of the level.
     * @param levelHeight The height of the level.
     */
    public LevelCamera(float levelWidth, float levelHeight){
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    /**
     * Updates the camera's position and ensures it stays within bounds based on the level dimensions.
     *
     * @param updateFrustum Whether to update the frustum planes of the camera.
     */
    @Override
    public void update(boolean updateFrustum) {
        ensureBounds();
        super.update(updateFrustum);
    }

    /**
     * Ensures the camera zoom and position are within acceptable bounds based on the level dimensions.
     */
    private void ensureBounds(){
        zoom = MathUtils.clamp(zoom, getMinZoom(), getMaxZoom());
        float effectiveViewportWidth = viewportWidth * zoom;
        float effectiveViewportHeight = viewportHeight * zoom;
        position.x = MathUtils.clamp(position.x, effectiveViewportWidth / 2f, levelWidth - effectiveViewportWidth / 2f);
        position.y = MathUtils.clamp(position.y, effectiveViewportHeight / 2f, levelHeight - effectiveViewportHeight / 2f);
    }

    /**
     * Sets the camera position abruptly to the specified coordinates.
     *
     * @param newPosition The new position for the camera.
     */
    public void setPositionSharply(Vector2 newPosition) {
        position.x = newPosition.x;
        position.y = newPosition.y;
    }

    /**
     * Sets the camera position smoothly using interpolation to the specified coordinates.
     *
     * @param newPosition The new position for the camera.
     */
    public void setPositionSmoothly(Vector2 newPosition) {
        position.x = Interpolation.circle.apply(position.x, newPosition.x, 0.2f);
        position.y = Interpolation.circle.apply(position.y, newPosition.y, 0.25f);
    }

    /**
     * Calculates the minimum zoom level based on the current viewport size.
     *
     * @return The minimum zoom level.
     */
    public float getMinZoom() {
        if (viewportWidth == 0 || viewportHeight == 0)
            return -Float.MAX_VALUE;
        else
            return Math.min(1/viewportWidth, 1/viewportHeight);
    }

    /**
     * Calculates the maximum zoom level based on the current viewport size and level dimensions.
     *
     * @return The maximum zoom level.
     */
    public float getMaxZoom() {
        if (viewportWidth == 0 || viewportHeight == 0)
            return Float.MAX_VALUE;
        else
            return Math.min(levelWidth/viewportWidth, levelHeight/viewportHeight);
    }

    /**
     * Adjusts the zoom level of the camera to fit the specified viewport size.
     *
     * @param width  The width of the viewport.
     * @param height The height of the viewport.
     */
    public void adjustZoomForViewportSize(float width, float height){
        float zoomX = viewportWidth != 0 ? width / viewportWidth : 1;
        float zoomY = viewportHeight != 0 ? height / viewportHeight : 1;
        zoom = (zoomX+zoomY)/2;
    }
}
