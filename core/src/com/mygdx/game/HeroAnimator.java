package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeroAnimator {
    public enum State {
        IDLE,
        RUNNING,
        JUMPING,
    }

    public enum Direction {
        RIGHT,
        LEFT
    }

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;

    private State curState = State.IDLE;
    private Direction curDirection = Direction.RIGHT;
    private float stateTime;
    private boolean animationChanged;

    private float idleTime;
    private static final float MAX_IDLE_TIME = 0.2f;

    private final Sprite flippingSprite = new Sprite();

    public HeroAnimator() {
        createIdleAnimation();
        createRunAnimation();
        createJumpAnimation();
    }

    private void createIdleAnimation() {
        idleAnimation = createAnimation("biker-idle.png", 1, 4, 1 / 10f);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void createRunAnimation() {
        runAnimation = createAnimation("biker-run.png", 1, 6, 1 / 10f);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void createJumpAnimation() {
        jumpAnimation = createAnimation("biker-jump.png", 1, 4, 1 / 10f);
    }

    private Animation<TextureRegion> createAnimation(String assetName, int rows, int cols, float frameDuration) {
        Texture spritesheet = MyGdxGame.getInstance().assetManager.get(assetName);
        TextureRegion[] frames = splitSheetIntoFrames(spritesheet, rows, cols);
        Animation<TextureRegion> animation = new Animation<>(frameDuration, frames);
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

    public void setState(State newState) {
        if (newState == State.IDLE && curState != State.IDLE && idleTime < MAX_IDLE_TIME) {
            idleTime += Gdx.graphics.getDeltaTime();
        } else {
            idleTime = 0;
            animationChanged = newState != curState;
            curState = newState;
        }
    }

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

    private Sprite getDirectedSprite(){
        flippingSprite.setRegion(getFrame());
        flippingSprite.flip(curDirection == Direction.LEFT, false);
        return flippingSprite;
    }

    private TextureRegion getFrame() {
        Animation<TextureRegion> animation = selectAnimation();
        return animation.getKeyFrame(stateTime, false);
    }

    private Animation<TextureRegion> selectAnimation() {
        switch (curState) {
            case RUNNING:
                return runAnimation;
            case JUMPING:
                return jumpAnimation;
            default:
                return idleAnimation;
        }
    }
}
