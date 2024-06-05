package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

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

    public AnimationBuilder(String assetName){
        this.assetName = assetName;
    }

    public AnimationBuilder(Texture spriteSheet){
        this.spriteSheet = spriteSheet;
    }

    public AnimationBuilder frameWidth(int frameWidth){
        this.frameWidth = frameWidth;
        return this;
    }

    public AnimationBuilder frameHeight(int frameHeight){
        this.frameHeight = frameHeight;
        return this;
    }

    public AnimationBuilder rows(int rows){
        this.rows = rows;
        return this;
    }

    public AnimationBuilder cols(int cols){
        this.cols = cols;
        return this;
    }

    public AnimationBuilder frameDuration(float frameDuration){
        this.frameDuration = frameDuration;
        return this;
    }

    public AnimationBuilder playMode(Animation.PlayMode playMode){
        this.playMode = playMode;
        return this;
    }

    public AnimationBuilder blocked(Animator.State fallbackState){
        this.fallbackState = fallbackState;
        return blocked();
    }

    public AnimationBuilder blocked() {
        this.isBlocked = true;
        return this;
    }

    /**
     * priority = 1000
     */
    public AnimationBuilder veryHighPriority() {
        return priority(1000);
    }

    /**
     * priority = 100
     */
    public AnimationBuilder highPriority() {
        return priority(100);
    }

    /**
     * priority = 0
     */
    public AnimationBuilder normalPriority() {
        return priority(0);
    }

    /**
     * priority = -100
     */
    public AnimationBuilder lowPriority() {
        return priority(1000);
    }

    /**
     * priority = -1000
     */
    public AnimationBuilder veryLowPriority() {
        return priority(1000);
    }

    public AnimationBuilder priority(int value) {
        this.priority = value;
        return this;
    }

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
