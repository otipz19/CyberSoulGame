package com.mygdx.game.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.AssetsNames;

public class ParallaxBackground {
    private static final int LAYERS_COUNT = 5;
    private final ParallaxLayer[] layers = new ParallaxLayer[LAYERS_COUNT];

    public ParallaxBackground(OrthographicCamera camera) {
        String[] assetsNames = new String[]{
                AssetsNames.GREENZONE_PARALLAX_1,
                AssetsNames.GREENZONE_PARALLAX_2,
                AssetsNames.GREENZONE_PARALLAX_3,
                AssetsNames.GREENZONE_PARALLAX_4,
                AssetsNames.GREENZONE_PARALLAX_5
        };
        for (int i = 0; i < LAYERS_COUNT; i++) {
            layers[i] = new ParallaxLayer(assetsNames[i], camera, 1f / (i + 1));
        }
    }

    public void render() {
        for (int i = 0; i < LAYERS_COUNT; i++) {
            layers[i].render();
        }
    }
}
