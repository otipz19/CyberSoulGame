package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class AnimationBuilder {
    private static final int DEFAULT_SPRITE_WIDTH = 48;
    private static final int DEFAULT_SPRITE_HEIGHT = 48;
    private static final float DEFAULT_FRAME_DURATION = 1 / 10f;

    private String assetName;
    private Texture spriteSheet;
    private Integer rows;
    private Integer cols;
    private float frameDuration = DEFAULT_FRAME_DURATION;
    private Animation.PlayMode playMode = Animation.PlayMode.NORMAL;

    public AnimationBuilder(String assetName){
        this.assetName = assetName;
    }

    public AnimationBuilder(Texture spriteSheet){
        this.spriteSheet = spriteSheet;
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

    public Animation<TextureRegion> build(){
        if(spriteSheet == null){
            spriteSheet = MyGdxGame.getInstance().assetManager.get(assetName);
        }
        if(rows == null){
            rows = spriteSheet.getHeight() / DEFAULT_SPRITE_HEIGHT;
        }
        if(cols == null){
            cols = spriteSheet.getWidth() / DEFAULT_SPRITE_WIDTH;
        }
        return createAnimation(spriteSheet, rows, cols, frameDuration, playMode);
    }

    protected Animation<TextureRegion> createAnimation(Texture spriteSheet,
                                                       int rows,
                                                       int cols,
                                                       float frameDuration,
                                                       Animation.PlayMode playMode) {
        TextureRegion[] frames = splitSheetIntoFrames(spriteSheet, rows, cols);
        Animation<TextureRegion> animation = new Animation<>(frameDuration, frames);
        animation.setPlayMode(playMode);
        return animation;
    }

    protected TextureRegion[] splitSheetIntoFrames(Texture spritesheet, int rows, int cols) {
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