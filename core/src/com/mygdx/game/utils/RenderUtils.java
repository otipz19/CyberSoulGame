package com.mygdx.game.utils;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.IRenderable;

/**
 * Provides utility methods for rendering entities that implement the IRenderable interface.
 */
public class RenderUtils {

    /**
     * Renders all entities in the provided array.
     *
     * @param <T>      The type of entities implementing the IRenderable interface.
     * @param delta    The time elapsed since the last frame, in seconds.
     * @param entities The array of entities to render.
     */
    public static <T extends IRenderable> void renderEntities(float delta, Array<T> entities) {
        for (T entity : entities) {
            entity.render(delta);
        }
    }
}
