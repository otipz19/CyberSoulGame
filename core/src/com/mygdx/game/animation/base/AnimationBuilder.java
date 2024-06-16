package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

/**
 * Builder class for creating animations with various customization options.
 */
public class AnimationBuilder {
    private static final int DEFAULT_FRAME_WIDTH = 48;
    private static final int DEFAULT_FRAME_HEIGHT = 48;
    private static final float DEFAULT_FRAME_DURATION = 1 / 10f;

    private String assetName;
    private Texture spriteSheet;
    private Integer rows;
    private Integer cols;
    private int frameWidth = DEFAULT_FRAME_WIDTH;
    private int frameHeight = DEFAULT_FRAME_HEIGHT;
    private float frameDuration = DEFAULT_FRAME_DURATION;
    private Animation.PlayMode playMode = Animation.PlayMode.NORMAL;

    private boolean isBlocked;
    private Animator.State fallbackState;
    private int priority;

    /**
     * Constructor for creating an AnimationBuilder with an asset name.
     *
     * @param assetName the name of the asset
     */
    public AnimationBuilder(String assetName){
        this.assetName = assetName;
    }

    /**
     * Constructor for creating an AnimationBuilder with a sprite sheet.
     *
     * @param spriteSheet the texture representing the sprite sheet
     */
    public AnimationBuilder(Texture spriteSheet){
        this.spriteSheet = spriteSheet;
    }

    /**
     * Sets the frame width.
     *
     * @param frameWidth the width of each frame
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder frameWidth(int frameWidth){
        this.frameWidth = frameWidth;
        return this;
    }

    /**
     * Sets the frame height.
     *
     * @param frameHeight the height of each frame
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder frameHeight(int frameHeight){
        this.frameHeight = frameHeight;
        return this;
    }

    /**
     * Sets the number of rows in the sprite sheet.
     *
     * @param rows the number of rows
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder rows(int rows){
        this.rows = rows;
        return this;
    }

    /**
     * Sets the number of columns in the sprite sheet.
     *
     * @param cols the number of columns
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder cols(int cols){
        this.cols = cols;
        return this;
    }

    /**
     * Sets the frame duration.
     *
     * @param frameDuration the duration of each frame
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder frameDuration(float frameDuration){
        this.frameDuration = frameDuration;
        return this;
    }

    /**
     * Sets the play mode for the animation.
     *
     * @param playMode the play mode
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder playMode(Animation.PlayMode playMode){
        this.playMode = playMode;
        return this;
    }

    /**
     * Sets the fallback state and marks the animation as blocked.
     *
     * @param fallbackState the fallback state
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder blocked(Animator.State fallbackState){
        this.fallbackState = fallbackState;
        return blocked();
    }

    /**
     * Marks the animation as blocked.
     *
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder blocked() {
        this.isBlocked = true;
        return this;
    }

    /**
     * Sets the priority to very high (1000).
     *
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder veryHighPriority() {
        return priority(1000);
    }

    /**
     * Sets the priority to high (100).
     *
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder highPriority() {
        return priority(100);
    }

    /**
     * Sets the priority to normal (0).
     *
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder normalPriority() {
        return priority(0);
    }

    /**
     * Sets the priority to low (-100).
     *
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder lowPriority() {
        return priority(-100);
    }

    /**
     * Sets the priority to very low (-1000).
     *
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder veryLowPriority() {
        return priority(-1000);
    }

    /**
     * Sets the priority to a specific value.
     *
     * @param value the priority value
     * @return the current AnimationBuilder instance
     */
    public AnimationBuilder priority(int value) {
        this.priority = value;
        return this;
    }

    /**
     * Builds and returns the MyAnimation instance based on the provided parameters.
     *
     * @return the created MyAnimation instance
     */
    public MyAnimation build(){
        if(spriteSheet == null){
            spriteSheet = MyGdxGame.getInstance().assetManager.get(assetName);
        }
        if(rows == null){
            rows = spriteSheet.getHeight() / frameWidth;
        }
        if(cols == null){
            cols = spriteSheet.getWidth() / frameHeight;
        }
        return createAnimation(spriteSheet, rows, cols, frameDuration, playMode);
    }
    /**
     * Creates an animation from the sprite sheet using the specified parameters.
     *
     * @param spriteSheet the texture representing the sprite sheet
     * @param rows the number of rows in the sprite sheet
     * @param cols the number of columns in the sprite sheet
     * @param frameDuration the duration of each frame
     * @param playMode the play mode for the animation
     * @return the created MyAnimation instance
     */
    private MyAnimation createAnimation(Texture spriteSheet,
                                        int rows,
                                        int cols,
                                        float frameDuration,
                                        Animation.PlayMode playMode) {
        TextureRegion[] frames = splitSheetIntoFrames(spriteSheet, rows, cols);
        var animation = new MyAnimation(frameDuration, priority, isBlocked, fallbackState, frames);
        animation.setPlayMode(playMode);
        return animation;
    }
    /**
     * Splits the sprite sheet into individual frames.
     *
     * @param spritesheet the texture representing the sprite sheet
     * @param rows the number of rows in the sprite sheet
     * @param cols the number of columns in the sprite sheet
     * @return an array of TextureRegions representing the frames
     */
    private TextureRegion[] splitSheetIntoFrames(Texture spritesheet, int rows, int cols) {
        TextureRegion[][] splittedSheet = TextureRegion
                .split(spritesheet, spritesheet.getWidth() / cols, spritesheet.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[i * cols + j] = splittedSheet[i][j];
            }
        }
        return frames;
    }
}
