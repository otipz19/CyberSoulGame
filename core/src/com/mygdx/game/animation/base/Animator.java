package com.mygdx.game.animation.base;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.animation.concrete.heroes.HeroAnimator;

/**
 * Abstract class for handling animations in the game.
 * Provides methods to manage animation states, directions, and rendering.
 */
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

    /**
     * Default constructor. Initializes animations using the abstract method createAnimationsMap().
     */
    public Animator() {
        this.animations = createAnimationsMap();
        this.curAnimation = animations.startAnimation;
    }

    /**
     * Constructor that initializes the Animator with a given AnimationsMap.
     *
     * @param animations the AnimationsMap to use
     */
    public Animator(AnimationsMap animations) {
        this.animations = animations;
        this.curAnimation = animations.startAnimation;
    }

    /**
     * Abstract method to be implemented by subclasses to create the AnimationsMap.
     *
     * @return the created AnimationsMap
     */
    protected abstract AnimationsMap createAnimationsMap();

    /**
     * Checks if the current animation is finished.
     *
     * @return true if the current animation is finished, false otherwise
     */
    public boolean isAnimationFinished() {
        return curAnimation != null && curAnimation.isAnimationFinished(stateTime);
    }

    /**
     * Gets the duration of the current animation.
     *
     * @return the duration of the current animation
     */
    public float getCurrentAnimationDuration() {
        return curAnimation.getAnimationDuration();
    }

    /**
     * Gets the current state of the animator.
     *
     * @return the current state
     */
    public State getState() {
        return curState;
    }

    /**
     * Sets a new state for the animator. If the new state does not have a registered animation, an exception is thrown.
     *
     * @param newState the new state to set
     * @throws RuntimeException if the new state does not have a registered animation
     */
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

    /**
     * Gets the current direction of the animator.
     *
     * @return the current direction
     */
    public Direction getDirection() {
        return curDirection;
    }

    /**
     * Sets a new direction for the animator.
     *
     * @param newDirection the new direction to set
     */
    public void setDirection(Direction newDirection) {
        if (!isAnimationResetBlocked) {
            animationChanged |= newDirection != curDirection;
            curDirection = newDirection;
        }
    }

    /**
     * Renders the animation at the specified position and size.
     *
     * @param batch the SpriteBatch used for drawing
     * @param x the x-coordinate for rendering
     * @param y the y-coordinate for rendering
     * @param width the width of the rendering area
     * @param height the height of the rendering area
     * @param deltaTime the time passed since the last frame
     */
    public void animate(SpriteBatch batch, float x, float y, float width, float height, float deltaTime) {
        updateStateTime(deltaTime);
        animationChanged = false;
        handleBlockedAnimation();
        batch.draw(getDirectedSprite(), x, y, width, height);
    }

    /**
     * Updates the state time based on the delta time.
     *
     * @param deltaTime the time passed since the last frame
     */
    private void updateStateTime(float deltaTime) {
        if (animationChanged) {
            stateTime = 0;
        } else {
            stateTime += deltaTime;
        }
    }

    /**
     * Handles animations that are blocked, resetting to fallback state if necessary.
     */
    private void handleBlockedAnimation() {
        if (isAnimationResetBlocked && curAnimation.isAnimationFinished(stateTime)) {
            isAnimationResetBlocked = false;
            if (curAnimation.getFallbackState() != null) {
                setState(curAnimation.getFallbackState());
            }
        }
    }

    /**
     * Gets the sprite for the current frame, adjusted for the current direction.
     *
     * @return the sprite for the current frame
     */
    private Sprite getDirectedSprite() {
        flippingSprite.setRegion(getFrame());
        flippingSprite.flip(curDirection == HeroAnimator.Direction.LEFT, false);
        return flippingSprite;
    }

    /**
     * Gets the texture region for the current frame.
     *
     * @return the texture region for the current frame
     */
    private TextureRegion getFrame() {
        return curAnimation.getKeyFrame(stateTime, false);
    }
}
