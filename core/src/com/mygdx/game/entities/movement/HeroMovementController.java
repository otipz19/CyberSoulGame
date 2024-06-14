package com.mygdx.game.entities.movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Controls the movement behavior of a hero character, including jumping, dashing, falling acceleration,
 * and velocity clamping near walls.
 * Extends the MovementController class.
 */
public class HeroMovementController extends MovementController {

    /** The maximum vertical velocity for the hero. */
    public float maxVerticalVelocity;

    /** The impulse applied when accelerating the hero's fall. */
    public float fallImpulse;

    /** The impulse applied when dashing. */
    public float dashImpulse;

    /** The delay between consecutive dashes. */
    public float dashDelay;

    /** Timer for tracking the dash cooldown. */
    protected float dashTimer;

    /** The delay between consecutive jumps. */
    public float jumpDelay;

    /** The impulse applied when jumping. */
    public float jumpImpulse;

    /** Timer for tracking the jump cooldown. */
    protected float jumpTimer;

    /**
     * Constructs a HeroMovementController instance for a given Body, initializing movement parameters.
     *
     * @param body The Box2D Body associated with the hero.
     */
    public HeroMovementController(Body body) {
        super(body);
        maxVerticalVelocity = maxHorizontalVelocity;
        fallImpulse = 0.6f;
        dashImpulse = 4f;
        dashDelay = 2f;
        jumpDelay = 0.5f;
        jumpImpulse = 7f;
    }

    /**
     * Updates the timers for jump and dash cooldowns based on the elapsed time.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        jumpTimer = updateTimer(jumpTimer, deltaTime);
        dashTimer = updateTimer(dashTimer, deltaTime);
    }

    /**
     * Accelerates the hero's fall by applying a downward impulse if the current velocity is within limits.
     */
    public void accelerateFall() {
        if (body.getLinearVelocity().y >= -maxVerticalVelocity)
            applyImpulse(0, -fallImpulse);
    }

    /**
     * Attempts to make the hero jump if the jump timer allows, resetting vertical velocity and applying an upward impulse.
     *
     * @return true if the jump action was successfully initiated; false otherwise.
     */
    public boolean tryJump() {
        if (jumpTimer != 0)
            return false;

        clearVelocityY();
        applyImpulse(0, jumpImpulse);
        jumpTimer = jumpDelay;
        return true;
    }

    /**
     * Attempts to make the hero dash if the dash timer allows, adjusting horizontal velocity and applying an impulse in the dash direction.
     *
     * @return true if the dash action was successfully initiated; false otherwise.
     */
    public boolean tryDash() {
        if (dashTimer != 0)
            return false;

        if (isFacingRight) {
            body.setLinearVelocity(maxHorizontalVelocity, body.getLinearVelocity().y);
            applyImpulse(dashImpulse, 0);
        } else {
            body.setLinearVelocity(-maxHorizontalVelocity, body.getLinearVelocity().y);
            applyImpulse(-dashImpulse, 0);
        }
        dashTimer = dashDelay;
        return true;
    }

    /**
     * Adjusts the hero's horizontal velocity near walls based on their proximity.
     *
     * @param isNearLeftWall  Indicates if the hero is near a left wall.
     * @param isNearRightWall Indicates if the hero is near a right wall.
     */
    public void clampVelocityNearWalls(boolean isNearLeftWall, boolean isNearRightWall) {
        Vector2 velocity = body.getLinearVelocity();
        if (isNearLeftWall)
            velocity.x = Math.max(velocity.x, 0);
        if (isNearRightWall)
            velocity.x = Math.min(velocity.x, 0);
        body.setLinearVelocity(velocity);
    }
}
