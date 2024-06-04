package com.mygdx.game.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ParallaxBackground {
    private final ParallaxLayer[] layers;

    public ParallaxBackground(OrthographicCamera camera, String[] layersAssetNames, float levelHeight) {
        layers = new ParallaxLayer[layersAssetNames.length];
        for (int i = 0; i < layers.length; i++) {
            float horizontalParallaxCoefficient = 0.5f / (i + 1);
            float verticalParallaxCoefficient = i == 0 ? 0 : 0.07f / i;
            layers[i] = new ParallaxLayer(layersAssetNames[i],
                    camera,
                    horizontalParallaxCoefficient,
                    verticalParallaxCoefficient,
                    levelHeight);
        }
    }

    public void render() {
        for (ParallaxLayer layer : layers) {
            layer.render();
        }
    }
}
