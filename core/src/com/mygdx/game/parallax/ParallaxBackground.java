package com.mygdx.game.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Represents a parallax background composed of multiple layers.
 * Each layer scrolls horizontally and vertically at different rates,
 * creating a parallax effect based on the camera movement.
 */
public class ParallaxBackground {

    private final ParallaxLayer[] layers;

    /**
     * Constructs a ParallaxBackground with multiple layers.
     *
     * @param camera           The OrthographicCamera used for rendering.
     * @param layersAssetNames An array of asset names for each parallax layer.
     * @param levelHeight      The height of the level (used for vertical parallax calculation).
     */
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

    /**
     * Renders the parallax background by rendering each layer.
     */
    public void render() {
        for (ParallaxLayer layer : layers) {
            layer.render();
        }
    }
}
