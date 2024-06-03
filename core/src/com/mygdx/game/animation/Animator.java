package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Animator {
    public interface State {
    }

    public enum Direction {
        RIGHT,
        LEFT
    }

    private final AnimationsMap animations;

    private Animation<TextureRegion> curAnimation;
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

    protected abstract AnimationsMap createAnimationsMap();

    public boolean isAnimationFinished() {
        if(curAnimation != null) {
            return curAnimation.isAnimationFinished(stateTime);
        }
        return false;
    }

    /**
     * Forces animator to ignore all calls of setState() and setDirection(),
     * until current animation won't be finished.
     * Works only if current animation is in PlayMode.NORMAL.
     */
    public void blockAnimationReset() {
        if (curAnimation.getPlayMode().equals(Animation.PlayMode.NORMAL)) {
            isAnimationResetBlocked = true;
        }
    }

    public void unblockAnimationReset() {
        isAnimationResetBlocked = false;
    }

    public State getState() {
        return curState;
    }

    public void setState(State newState) {
        if (!isAnimationResetBlocked) {
            animationChanged |= newState != curState;
            curState = newState;
            if (!animations.containsKey(curState)) {
                throw new RuntimeException("Animation state " + curState.toString() + " doesn't have registered animation!");
            }
            curAnimation = animations.get(curState);
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
        if (isAnimationResetBlocked && curAnimation.isAnimationFinished(stateTime)) {
            unblockAnimationReset();
        }
        batch.draw(getDirectedSprite(), x, y, width, height);
    }

    private void updateStateTime(float deltaTime) {
        if (animationChanged) {
            stateTime = 0;
        } else {
            stateTime += deltaTime;
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
