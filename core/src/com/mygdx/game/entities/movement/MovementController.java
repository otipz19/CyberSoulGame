package com.mygdx.game.entities.movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class MovementController {
    protected Body body;
    public float maxHorizontalVelocity;
    public float minNotIdleVelocity;
    public float horizontalImpulse;
    protected boolean isFacingRight;

    public MovementController(Body body) {
        this.body = body;
        maxHorizontalVelocity = 5f;
        minNotIdleVelocity = maxHorizontalVelocity * 0.6f;
        horizontalImpulse = 0.6f;
    }

    public void update(float deltaTime) {}

    public void moveLeft() {
        isFacingRight = false;
        if (body.getLinearVelocity().x >= -maxHorizontalVelocity)
            applyImpulse(-horizontalImpulse, 0);
    }

    public void moveRight() {
        isFacingRight = true;
        if (body.getLinearVelocity().x <= maxHorizontalVelocity)
            applyImpulse(horizontalImpulse, 0);
    }

    public void applyImpulse(float horizontalImpulse, float verticalImpulse){
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(horizontalImpulse, verticalImpulse, center.x, center.y, true);
    }

    public void clearVelocityX(){
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(0, oldVelocity.y);
    }

    public void clearVelocityY(){
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(oldVelocity.x, Math.max(0, oldVelocity.y));
    }

    public boolean isBodyEffectivelyIdle() {
        return Math.abs(body.getLinearVelocity().x) < minNotIdleVelocity;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    protected float updateTimer(float value, float delta) {
        return Math.max(0, value - delta);
    }
}
