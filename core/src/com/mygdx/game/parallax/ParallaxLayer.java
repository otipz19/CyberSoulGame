package com.mygdx.game.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

public class ParallaxLayer {
    private static final int SPRITES_COUNT = 9;

    private final Sprite[] sprites = new Sprite[SPRITES_COUNT];
    private final String assetName;
    private final float horizontalParallaxCoefficient;
    private final float verticalParallaxCoefficient;
    private final float levelHeight;

    private final OrthographicCamera camera;
    private final Vector2 cameraPreviousPos;

    private boolean isFirstRender = true;

    public ParallaxLayer(String assetName,
                         OrthographicCamera camera,
                         float horizontalParallaxCoefficient,
                         float verticalParallaxCoefficient,
                         float levelHeight) {
        this.assetName = assetName;
        this.camera = camera;
        this.cameraPreviousPos = new Vector2(camera.position.x, camera.position.y);
        this.horizontalParallaxCoefficient = horizontalParallaxCoefficient;
        this.verticalParallaxCoefficient = verticalParallaxCoefficient;
        this.levelHeight = levelHeight;
    }

    public void render() {
        // Sprites creation is performed during rendering of first frame
        // due to unknown value of viewport's size at the time of constructor call
        if (isFirstRender) {
            isFirstRender = false;
            createSprites();
        } else {
            updateSprites();
        }
    }

    private void createSprites() {
        Texture texture = MyGdxGame.getInstance().assetManager.get(assetName);
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(texture);
            initialTransformSprite(sprites[i], i - SPRITES_COUNT / 2);
        }
    }

    private void initialTransformSprite(Sprite sprite, int index) {
        // getScreenWidth() * 0.05 is added to the width to make sprites slightly overlap,
        // so the gap between them, that sometimes appear after "from edge - to edge" permutation, won't be visible,
        // but that creates background blinking effect at the edges of the screen
        sprite.setSize(getScreenWidth() + getScreenWidth() * 0.05f, getScreenHeight());
        sprite.setX(getMiddleScreenX() + (index * getScreenWidth()));
        sprite.setY(getMiddleScreenY());
    }

    private float getScreenWidth() {
        return camera.viewportWidth / 2;
    }

    private float getScreenHeight() {
        return camera.viewportHeight / 2;
    }

    private float getMiddleScreenX() {
        return camera.position.x - getScreenWidth() / 2;
    }

    private void updateSprites() {
        for (int i = 0; i < SPRITES_COUNT; i++) {
            updateSprite(sprites[i]);
        }
        cameraPreviousPos.x = camera.position.x;
    }

    private void updateSprite(Sprite sprite) {
        sprite.setX(sprite.getX() - calcXOffset());
        if (isSpriteBehindTheLeftEdge(sprite)) {
            sprite.setX(getBehindTheRightEdgeX());
        } else if (isSpriteBehindTheRightEdge(sprite)) {
            sprite.setX(getBehindTheLeftEdgeX());
        }
        sprite.setY(getMiddleScreenY() + calcYOffset());
        sprite.draw(MyGdxGame.getInstance().batch);
    }

    private float calcXOffset() {
        return horizontalParallaxCoefficient * (camera.position.x - cameraPreviousPos.x);
    }

    private float calcYOffset() {
        return Interpolation.linear.apply(-(levelHeight * verticalParallaxCoefficient),
                0,
                (levelHeight - camera.position.y) / levelHeight);
    }

    private boolean isSpriteBehindTheLeftEdge(Sprite sprite) {
        return sprite.getX() < getBehindTheLeftEdgeX();
    }

    private boolean isSpriteBehindTheRightEdge(Sprite sprite) {
        return sprite.getX() > getBehindTheRightEdgeX();
    }

    private float getBehindTheLeftEdgeX() {
        return getMiddleScreenX() - (SPRITES_COUNT / 2 * getScreenWidth());
    }

    private float getBehindTheRightEdgeX() {
        return getMiddleScreenX() + (SPRITES_COUNT / 2 * getScreenWidth());
    }

    private float getMiddleScreenY() {
        return camera.position.y - camera.viewportHeight / 4;
    }
}
