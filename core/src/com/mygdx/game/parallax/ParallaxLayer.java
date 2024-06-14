package com.mygdx.game.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

/**
 * Represents a single layer in a parallax background.
 * This layer scrolls horizontally and vertically based on the movement of an OrthographicCamera.
 */
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

    /**
     * Constructs a ParallaxLayer with the given parameters.
     *
     * @param assetName                    The asset name for the layer's texture.
     * @param camera                       The OrthographicCamera used for rendering.
     * @param horizontalParallaxCoefficient The coefficient controlling the horizontal scroll speed relative to camera movement.
     * @param verticalParallaxCoefficient   The coefficient controlling the vertical scroll speed relative to camera movement.
     * @param levelHeight                  The height of the level, used for vertical parallax effect calculation.
     */
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

    /**
     * Renders the parallax layer by updating and drawing its sprites.
     * This method creates sprites during the first render to accommodate the viewport size.
     */
    public void render() {
        if (isFirstRender) {
            isFirstRender = false;
            createSprites();
        } else {
            updateSprites();
        }
    }

    /**
     * Creates sprites for the layer using the specified texture and initializes their positions.
     */
    private void createSprites() {
        Texture texture = MyGdxGame.getInstance().assetManager.get(assetName);
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(texture);
            initialTransformSprite(sprites[i], i - SPRITES_COUNT / 2);
        }
    }

    /**
     * Initializes the position and size of a sprite based on its index and the screen dimensions.
     *
     * @param sprite The sprite to initialize.
     * @param index  The index of the sprite in the array.
     */
    private void initialTransformSprite(Sprite sprite, int index) {
        sprite.setSize(getScreenWidth() + getScreenWidth() * 0.05f, getScreenHeight());
        sprite.setX(getMiddleScreenX() + (index * getScreenWidth()));
        sprite.setY(getMiddleScreenY());
    }

    /**
     * Updates the positions of all sprites based on the camera movement.
     */
    private void updateSprites() {
        for (int i = 0; i < SPRITES_COUNT; i++) {
            updateSprite(sprites[i]);
        }
        cameraPreviousPos.x = camera.position.x;
    }

    /**
     * Updates the position of a sprite based on the camera movement and redraws it.
     *
     * @param sprite The sprite to update and render.
     */
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

    /**
     * Calculates the horizontal offset based on the camera's movement since the last frame.
     *
     * @return The horizontal offset.
     */
    private float calcXOffset() {
        return horizontalParallaxCoefficient * (camera.position.x - cameraPreviousPos.x);
    }

    /**
     * Calculates the vertical offset based on the camera's vertical position relative to the level height.
     *
     * @return The vertical offset.
     */
    private float calcYOffset() {
        return Interpolation.linear.apply(-(levelHeight * verticalParallaxCoefficient),
                0,
                (levelHeight - camera.position.y) / levelHeight);
    }

    /**
     * Checks if the sprite is positioned behind the left edge of the screen.
     *
     * @param sprite The sprite to check.
     * @return True if the sprite is behind the left edge, false otherwise.
     */
    private boolean isSpriteBehindTheLeftEdge(Sprite sprite) {
        return sprite.getX() < getBehindTheLeftEdgeX();
    }

    /**
     * Checks if the sprite is positioned behind the right edge of the screen.
     *
     * @param sprite The sprite to check.
     * @return True if the sprite is behind the right edge, false otherwise.
     */
    private boolean isSpriteBehindTheRightEdge(Sprite sprite) {
        return sprite.getX() > getBehindTheRightEdgeX();
    }

    /**
     * Calculates the x-coordinate behind the left edge of the screen for sprite wrapping.
     *
     * @return The x-coordinate behind the left edge.
     */
    private float getBehindTheLeftEdgeX() {
        return getMiddleScreenX() - (SPRITES_COUNT / 2 * getScreenWidth());
    }

    /**
     * Calculates the x-coordinate behind the right edge of the screen for sprite wrapping.
     *
     * @return The x-coordinate behind the right edge.
     */
    private float getBehindTheRightEdgeX() {
        return getMiddleScreenX() + (SPRITES_COUNT / 2 * getScreenWidth());
    }

    /**
     * Calculates the y-coordinate at the middle of the screen adjusted for the parallax effect.
     *
     * @return The y-coordinate at the middle of the screen.
     */
    private float getMiddleScreenY() {
        return camera.position.y - camera.viewportHeight / 4;
    }

    /**
     * Calculates the x-coordinate at the middle of the screen adjusted for the parallax effect.
     *
     * @return The x-coordinate at the middle of the screen.
     */
    private float getMiddleScreenX() {
        return camera.position.x - getScreenWidth() / 2;
    }

    /**
     * Calculates the screen width based on the camera's viewport width.
     *
     * @return The screen width.
     */
    private float getScreenWidth() {
        return camera.viewportWidth / 2;
    }

    /**
     * Calculates the screen height based on the camera's viewport height.
     *
     * @return The screen height.
     */
    private float getScreenHeight() {
        return camera.viewportHeight / 2;
    }
}
