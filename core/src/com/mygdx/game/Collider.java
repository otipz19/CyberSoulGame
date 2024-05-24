package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;

public class Collider implements Disposable {
    private float x;
    private float y;
    private Shape shape;

    public Collider(float x, float y, Shape shape) {
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }


    @Override
    public void dispose() {
        shape.dispose();
    }
}
