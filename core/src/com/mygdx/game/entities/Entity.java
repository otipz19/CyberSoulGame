package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.base.Animator;

public abstract class Entity extends GameObject implements IRenderable {
    protected Animator animator;
    protected float width;
    protected float height;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    public void setPosition(float x, float y){
        body.getPosition().set(x, y);
    }

    public void setX(float x){
        Vector2 position = getPosition();
        position.x = x;
        body.setTransform(position, body.getAngle());
    }

    public void setY(float y){
        Vector2 position = getPosition();
        position.y = y;
        body.setTransform(position, body.getAngle());
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public void animate(float deltaTime) {
        animator.animate(MyGdxGame.getInstance().batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);
    }
}
