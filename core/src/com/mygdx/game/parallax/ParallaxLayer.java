package com.mygdx.game.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

public class ParallaxLayer {
    private static final int SPRITES_COUNT = 3;
    private final Sprite[] sprites = new Sprite[SPRITES_COUNT];
    private final String assetName;
    private final float coefficient;

    private final OrthographicCamera camera;
    private final Vector2 cameraPreviousPos;

    private boolean isFirstRender = true;


    public ParallaxLayer(String assetName, OrthographicCamera camera, float coefficient) {
        this.assetName = assetName;
        this.camera = camera;
        this.cameraPreviousPos = new Vector2(camera.position.x, camera.position.y);
        this.coefficient = coefficient;
    }

    public void render() {
        if (isFirstRender) {
            isFirstRender = false;
            Texture texture = MyGdxGame.getInstance().assetManager.get(assetName);
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = new Sprite(texture);
                sprites[i].setPosition(camera.position.x - camera.viewportWidth / 2 + (camera.viewportWidth / 2 * i),
                        camera.position.y - camera.viewportHeight / 4);
                sprites[i].setSize(camera.viewportWidth / 2, camera.viewportHeight / 2);
            }
        } else {
            for (int i = 0; i < SPRITES_COUNT; i++) {
                sprites[i].setX(sprites[i].getX() - calcXOffset());
                if (sprites[i].getX() < camera.position.x - camera.viewportWidth) {
                    sprites[i].setX(camera.position.x + camera.viewportWidth / 2);
                } else if(sprites[i].getX() > camera.position.x + camera.viewportWidth / 2) {
                    sprites[i].setX(camera.position.x - camera.viewportWidth / 2);
                }
                sprites[i].draw(MyGdxGame.getInstance().batch);
            }
            cameraPreviousPos.x = camera.position.x;
        }
    }

    private float calcXOffset() {
        return coefficient * (camera.position.x - cameraPreviousPos.x);
    }
}
