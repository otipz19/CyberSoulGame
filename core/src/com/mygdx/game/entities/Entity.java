package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.animation.Animator;

public abstract class Entity {
    protected Animator animator;
    protected Body body;
    protected float width;
    protected float height;

    public abstract void render();

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

    private Vector2 getPosition(){
        return body.getPosition();
    }

    private void setX(float x){
        Vector2 position = getPosition();
        position.x = x;
        body.setTransform(position, body.getAngle());
    }

    private void setY(float y){
        Vector2 position = getPosition();
        position.y = y;
        body.setTransform(position, body.getAngle());
    }
}
