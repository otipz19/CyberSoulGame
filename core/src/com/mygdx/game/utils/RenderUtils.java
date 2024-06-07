package com.mygdx.game.utils;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.IRenderable;

public class RenderUtils {
    public static <T extends IRenderable> void renderEntities(float delta, Array<T> entities) {
        for (T entity : entities) {
            entity.render(delta);
        }
    }
}
