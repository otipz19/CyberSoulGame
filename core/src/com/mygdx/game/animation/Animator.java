package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public abstract class Animator {
    public enum Direction {
        RIGHT,
        LEFT
    }

    private static final int DEFAULT_SPRITE_WIDTH = 48;
    private static final int DEFAULT_SPRITE_HEIGHT = 48;
    private static final float DEFAULT_FRAME_DURATION = 1 / 10f;

    private Direction curDirection = Direction.RIGHT;
    protected boolean animationChanged;
    private float stateTime;

    private final Sprite flippingSprite = new Sprite();

    public void setDirection(Direction newDirection) {
        animationChanged = newDirection != curDirection;
        curDirection = newDirection;
    }

    public void animate(SpriteBatch batch, float x, float y, float width, float height) {
        updateStateTime();
        batch.draw(getDirectedSprite(), x, y, width, height);
    }

    private void updateStateTime() {
        if (animationChanged) {
            stateTime = 0;
        } else {
            stateTime += Gdx.graphics.getDeltaTime();
        }
    }

    private Sprite getDirectedSprite() {
        flippingSprite.setRegion(getFrame());
        flippingSprite.flip(curDirection == HeroAnimator.Direction.LEFT, false);
        return flippingSprite;
    }

    private TextureRegion getFrame() {
        Animation<TextureRegion> animation = selectAnimation();
        return animation.getKeyFrame(stateTime, false);
    }

    protected Animation<TextureRegion> createAnimation(String assetName, Animation.PlayMode playMode) {
        return createAnimation(assetName, DEFAULT_FRAME_DURATION, playMode);
    }

    protected Animation<TextureRegion> createAnimation(String assetName, float frameDuration, Animation.PlayMode playMode) {
        Texture spritesheet = MyGdxGame.getInstance().assetManager.get(assetName);
        int rows = spritesheet.getHeight() / DEFAULT_SPRITE_HEIGHT;
        int cols = spritesheet.getWidth() / DEFAULT_SPRITE_WIDTH;
        Animation<TextureRegion> animation = createAnimation(spritesheet, rows, cols, frameDuration);
        animation.setPlayMode(playMode);
        return animation;
    }

    protected Animation<TextureRegion> createAnimation(String assetName, int rows, int cols, float frameDuration) {
        Texture spritesheet = MyGdxGame.getInstance().assetManager.get(assetName);
        return createAnimation(spritesheet, rows, cols, frameDuration);
    }

    protected Animation<TextureRegion> createAnimation(Texture spritesheet, int rows, int cols, float frameDuration) {
        TextureRegion[] frames = splitSheetIntoFrames(spritesheet, rows, cols);
        Animation<TextureRegion> animation = new Animation<>(frameDuration, frames);
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

    protected abstract Animation<TextureRegion> selectAnimation();
}
