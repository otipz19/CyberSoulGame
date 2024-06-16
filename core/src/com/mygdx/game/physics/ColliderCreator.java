package com.mygdx.game.physics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.camera.CoordinatesProjector;
/**
 * Utility class for creating Box2D colliders from various shapes and coordinates.
 */
public class ColliderCreator {
    /**
     *  It is expected that coordinates of rectangle are x and y of bottom-left corner in world space
     */
    public static Collider create(Rectangle rectangle) {
        return create(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     *  It is expected that x and y are coordinates of bottom-left corner of rectangle in world space
     */
    public static Collider create(float x, float y, float width, float height) {
        float[] vertices =  {0, 0, width, 0, width, height, 0, height };

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        return new Collider(x, y, shape);
    }

    /**
     *  It is expected that coordinates of rectangle are x and y of top-left corner in screen space
     */
    public static Collider create(Rectangle rectangle, CoordinatesProjector projector) {
        float x0 = rectangle.x;
        float x1 = rectangle.x + rectangle.width;
        float y0 = rectangle.y + rectangle.height;
        float y2 = rectangle.y;

        Vector2 unprojectionVector = new Vector2();

        unprojectionVector.set(x0, y0);
        projector.unproject(unprojectionVector);
        float worldX = unprojectionVector.x;
        float worldY = unprojectionVector.y;

        unprojectionVector.set(x1, y0);
        projector.unproject(unprojectionVector);
        float worldWidth = unprojectionVector.x-worldX;

        unprojectionVector.set(x0, y2);
        projector.unproject(unprojectionVector);
        float worldHeight = unprojectionVector.y-worldY;

        float[] vertices =  {0, 0, worldWidth, 0, worldWidth, worldHeight, 0, worldHeight };

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        return new Collider(worldX, worldY, shape);
    }

    /**
     *  It is expected that coordinates of polygon are in world space
     */
    public static Collider create(Polygon polygon) {
        return create(polygon.getTransformedVertices());
    }

    /**
     *  It is expected that coordinates of polygon are in screen space
     */
    public static Collider create(Polygon polygon, CoordinatesProjector projector) {
        Vector2 unprojectionVector = new Vector2();
        float[] vertices = polygon.getTransformedVertices();
        for (int i = 0; i < vertices.length; i+=2){
            unprojectionVector.x = vertices[i];
            unprojectionVector.y = vertices[i+1];
            projector.unproject(unprojectionVector);
            vertices[i] = unprojectionVector.x;
            vertices[i+1] = unprojectionVector.y;
        }

        return create(vertices);
    }

    private static Collider create(float[] worldVertices){
        int minVerticeIndex = 0;
        for (int i = 2; i < worldVertices.length; i+=2){
            if (worldVertices[i] < worldVertices[minVerticeIndex])
                minVerticeIndex = i;
            else if (worldVertices[i] == worldVertices[minVerticeIndex] && worldVertices[i+1] < worldVertices[minVerticeIndex+1])
                minVerticeIndex = i;
        }

        float x = worldVertices[minVerticeIndex];
        float y = worldVertices[minVerticeIndex+1];
        float[] localVertices = new float[worldVertices.length];
        for (int i = 0; i < worldVertices.length; i+= 2){
            localVertices[i] = worldVertices[(minVerticeIndex+i) % worldVertices.length] - x;
            localVertices[i+1] = worldVertices[(minVerticeIndex+i+1) % worldVertices.length] - y;
        }

        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(localVertices);

        return new Collider(x, y, chainShape);
    }
}
