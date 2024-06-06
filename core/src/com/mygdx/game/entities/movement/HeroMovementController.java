package com.mygdx.game.entities.movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class HeroMovementController extends MovementController {
    public float maxVerticalVelocity;
    public float fallImpulse;
    public float dashImpulse;
    public float dashDelay;
    protected float dashTimer;
    public float jumpDelay;
    public float jumpImpulse;
    protected float jumpTimer;

    public HeroMovementController(Body body) {
        super(body);
        maxVerticalVelocity = maxHorizontalVelocity;
        fallImpulse = 0.6f;
        dashImpulse = 4f;
        dashDelay = 2f;
        jumpDelay = 0.5f;
        jumpImpulse = 7f;
    }

    @Override
    public void update(float deltaTime) {
        jumpTimer = updateTimer(jumpTimer, deltaTime);
        dashTimer = updateTimer(dashTimer, deltaTime);
    }

    public void accelerateFall() {
        if (body.getLinearVelocity().y >= -maxVerticalVelocity)
            applyImpulse(0, -fallImpulse);
    }

    public boolean tryJump() {
        if (jumpTimer != 0)
            return false;

        clearVelocityY();
        applyImpulse(0, jumpImpulse);
        jumpTimer = jumpDelay;
        return true;
    }

    public boolean tryDash() {
        if (dashTimer != 0)
            return false;

        if (isFacingRight) {
            body.setLinearVelocity(maxHorizontalVelocity, body.getLinearVelocity().y);
            applyImpulse(dashImpulse, 0);
        }
        else {
            body.setLinearVelocity(-maxHorizontalVelocity, body.getLinearVelocity().y);
            applyImpulse(-dashImpulse, 0);
        }
        dashTimer = dashDelay;
        return true;
    }

    public void clampVelocityNearWalls(boolean isNearLeftWall, boolean isNearRightWall) {
        Vector2 velocity = body.getLinearVelocity();
        if (isNearLeftWall)
            velocity.x = Math.max(velocity.x, 0);
        if (isNearRightWall)
            velocity.x = Math.min(velocity.x, 0);
        body.setLinearVelocity(velocity);
    }
}
