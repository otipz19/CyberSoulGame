package com.mygdx.game.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ParallaxBackground {
    private static final int LAYERS_COUNT = 5;
    private final ParallaxLayer[] layers = new ParallaxLayer[LAYERS_COUNT];

    public ParallaxBackground(OrthographicCamera camera, String[] layersAssetNames) {
        if(layersAssetNames.length != LAYERS_COUNT) {
            throw new RuntimeException(LAYERS_COUNT + " asset names for layers must be provided!");
        }
        for (int i = 0; i < LAYERS_COUNT; i++) {
            layers[i] = new ParallaxLayer(layersAssetNames[i], camera, 0.5f / (i + 1));
        }
    }

    public void render() {
        for (int i = 0; i < LAYERS_COUNT; i++) {
            layers[i].render();
        }
    }
}
