package com.mygdx.game.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LevelCamera extends OrthographicCamera {
    private final float levelWidth;
    private final float levelHeight;

    public LevelCamera(float levelWidth, float levelHeight){
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    @Override
    public void update(boolean updateFrustum) {
        ensureBounds();
        super.update(updateFrustum);
    }

    private void ensureBounds(){
        zoom = MathUtils.clamp(zoom, getMinZoom(), getMaxZoom());
        float effectiveViewportWidth = viewportWidth * zoom;
        float effectiveViewportHeight = viewportHeight * zoom;
        position.x = MathUtils.clamp(position.x, effectiveViewportWidth / 2f, levelWidth - effectiveViewportWidth / 2f);
        position.y = MathUtils.clamp(position.y, effectiveViewportHeight / 2f, levelHeight - effectiveViewportHeight / 2f);
    }

    public void setPositionSharply(Vector2 newPosition) {
        position.x = newPosition.x;
        position.y = newPosition.y;
    }

    public void setPositionSmoothly(Vector2 newPosition) {
        position.x = Interpolation.circle.apply(position.x, newPosition.x, 0.2f);
        position.y = Interpolation.circle.apply(position.y, newPosition.y, 0.25f);
    }

    public float getMinZoom() {
        if (viewportWidth == 0 || viewportHeight == 0)
            return -Float.MAX_VALUE;
        else
            return Math.min(1/viewportWidth, 1/viewportHeight);
    }

    public float getMaxZoom() {
        if (viewportWidth == 0 || viewportHeight == 0)
            return Float.MAX_VALUE;
        else
            return Math.min(levelWidth/viewportWidth, levelHeight/viewportHeight);
    }

    public void adjustZoomForViewportSize(float width, float height){
        float zoomX = width / levelWidth;
        float zoomY = height / levelHeight;
        zoom = (zoomX+zoomY)/2;
    }
}
