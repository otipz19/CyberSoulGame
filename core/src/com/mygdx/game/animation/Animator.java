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

    private final AnimationsMap animations;

    private Animation<TextureRegion> curAnimation;
    private State curState;
    private Direction curDirection = Direction.RIGHT;
    private boolean animationChanged;
    private float stateTime;

    private final Sprite flippingSprite = new Sprite();

    public Animator(AnimationsMap animations){
        this.animations = animations;
        this.curAnimation = animations.startAnimation;
    }

    public State getState(){
        return curState;
    }

    public void setState(State newState) {
        animationChanged |= newState != curState;
        curState = newState;
        if(!animations.containsKey(curState)){
            throw new RuntimeException("Animation state " + curState.toString() + " doesn't have registered animation!");
        }
        curAnimation = animations.get(curState);
    }

    public Direction getDirection() {
        return curDirection;
    }

    public void setDirection(Direction newDirection) {
        animationChanged |= newDirection != curDirection;
        curDirection = newDirection;
    }

    public void animate(SpriteBatch batch, float x, float y, float width, float height) {
        updateStateTime();
        animationChanged = false;
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
