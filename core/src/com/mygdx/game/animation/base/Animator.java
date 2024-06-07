package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.animation.concrete.heroes.HeroAnimator;

public abstract class Animator {
    public interface State {
    }

    public enum Direction {
        RIGHT,
        LEFT
    }

    private final AnimationsMap animations;

    private MyAnimation curAnimation;
    private State curState;
    private Direction curDirection = Direction.RIGHT;
    private boolean animationChanged;
    private float stateTime;

    private final Sprite flippingSprite = new Sprite();

    private boolean isAnimationResetBlocked;

    public Animator() {
        this.animations = createAnimationsMap();
        this.curAnimation = animations.startAnimation;
    }
    public Animator(AnimationsMap animations) {
        this.animations = animations;
        this.curAnimation = animations.startAnimation;
    }

    protected abstract AnimationsMap createAnimationsMap();
    public boolean isAnimationFinished() {
        return curAnimation != null && curAnimation.isAnimationFinished(stateTime);
    }

    public float getCurrentAnimationDuration() {
        return curAnimation.getAnimationDuration();
    }

    public State getState() {
        return curState;
    }

    public void setState(State newState) {
        if (!animations.containsKey(newState)) {
            throw new RuntimeException("Animation state " + newState.toString() + " doesn't have registered animation!");
        }

        if (!isAnimationResetBlocked || animations.get(newState).getPriority() > curAnimation.getPriority()) {
            animationChanged |= newState != curState;
            curState = newState;
            curAnimation = animations.get(curState);
            isAnimationResetBlocked = curAnimation.isBlocked();
        }
    }

    public Direction getDirection() {
        return curDirection;
    }

    public void setDirection(Direction newDirection) {
        if (!isAnimationResetBlocked) {
            animationChanged |= newDirection != curDirection;
            curDirection = newDirection;
        }
    }

    public void animate(SpriteBatch batch, float x, float y, float width, float height, float deltaTime) {
        updateStateTime(deltaTime);
        animationChanged = false;
        handleBlockedAnimation();
        batch.draw(getDirectedSprite(), x, y, width, height);
    }


    private void updateStateTime(float deltaTime) {
        if (animationChanged) {
            stateTime = 0;
        } else {
            stateTime += deltaTime;
        }
    }

    private void handleBlockedAnimation() {
        if (isAnimationResetBlocked && curAnimation.isAnimationFinished(stateTime)) {
            isAnimationResetBlocked = false;
            if(curAnimation.getFallbackState() != null) {
                setState(curAnimation.getFallbackState());
            }
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
