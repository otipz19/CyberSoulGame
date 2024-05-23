package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeroAnimator {
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;

    private HeroAnimationState curState = HeroAnimationState.IDLE;
    private HeroAnimationDirection curDirection = HeroAnimationDirection.RIGHT;
    private float stateTime;


    private float idleTime;
    private static final float MAX_IDLE_TIME = 2f;

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
        TextureRegion[][] splitedSheet = TextureRegion
                .split(spritesheet, spritesheet.getWidth() / cols, spritesheet.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[i * cols + j] = splitedSheet[i][j];
            }
        }
        return frames;
    }

    public void setAnimation(HeroAnimationState newState) {
        setAnimation(newState, curDirection);
    }

    public void setAnimation(HeroAnimationState newState, HeroAnimationDirection newDirection) {
        if (newState == HeroAnimationState.IDLE || curState != HeroAnimationState.IDLE) {
            if (idleTime >= MAX_IDLE_TIME) {
                idleTime = 0;
            } else {
                float deltaTime = Gdx.graphics.getDeltaTime();
                idleTime += deltaTime;
                stateTime += deltaTime;
                return;
            }
        }

        if (newState == curState && newDirection == curDirection) {
            stateTime += Gdx.graphics.getDeltaTime();
        } else {
            stateTime = 0;
        }
        curState = newState;
        curDirection = newDirection;
    }

    public void animate(SpriteBatch batch, float x, float y, float width, float height) {
        flippingSprite.setRegion(getFrame());
        flippingSprite.flip(curDirection == HeroAnimationDirection.LEFT, false);
        batch.draw(flippingSprite, x, y, width, height);
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

    public enum HeroAnimationState {
        IDLE,
        RUNNING,
        JUMPING,
    }

    public enum HeroAnimationDirection {
        RIGHT,
        LEFT
    }
}
