package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Animator {
    public interface State { }

    public enum Direction {
        RIGHT,
        LEFT
    }

    protected final AnimationsMap animations;

    protected Animation<TextureRegion> curAnimation;
    protected State curState;
    protected Direction curDirection = Direction.RIGHT;
    private boolean animationChanged;
    private float stateTime;

    private final Sprite flippingSprite = new Sprite();

    public Animator(AnimationsMap animations){
        this.animations = animations;
        this.curAnimation = animations.startAnimation;
    }

    public void setState(State newState) {
        animationChanged = newState != curState;
        curState = newState;
        curAnimation = animations.get(curState);
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

    private Sprite getDirectedSprite() {
        flippingSprite.setRegion(getFrame());
        flippingSprite.flip(curDirection == HeroAnimator.Direction.LEFT, false);
        return flippingSprite;
    }

    private TextureRegion getFrame() {
        return curAnimation.getKeyFrame(stateTime, false);
    }
}