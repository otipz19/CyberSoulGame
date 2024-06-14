package com.mygdx.game.entities.movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A base class that controls the movement behavior of entities with Box2D bodies.
 * Provides methods for movement, impulse application, velocity clearing, and timer updates.
 */
public abstract class MovementController {

    /** The Box2D body associated with the entity. */
    protected Body body;

    /** The maximum horizontal velocity allowed for the entity. */
    public float maxHorizontalVelocity;

    /** The minimum velocity threshold considered not idle. */
    public float minNotIdleVelocity;

    /** The horizontal impulse to apply for movement. */
    public float horizontalImpulse;

    /** Indicates whether the entity is facing right. */
    protected boolean isFacingRight;

    /**
     * Constructs a MovementController instance for a given Box2D Body, initializing movement parameters.
     *
     * @param body The Box2D Body associated with the entity.
     */
    public MovementController(Body body) {
        this.body = body;
        maxHorizontalVelocity = 5f;
        minNotIdleVelocity = maxHorizontalVelocity * 0.6f;
        horizontalImpulse = 0.6f;
    }

    /**
     * Updates the movement controller, typically called once per frame.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    public void update(float deltaTime) {}

    /**
     * Moves the entity left by applying a horizontal impulse, setting facing direction to left.
     */
    public void moveLeft() {
        isFacingRight = false;
        if (body.getLinearVelocity().x >= -maxHorizontalVelocity)
            applyImpulse(-horizontalImpulse, 0);
    }

    /**
     * Moves the entity right by applying a horizontal impulse, setting facing direction to right.
     */
    public void moveRight() {
        isFacingRight = true;
        if (body.getLinearVelocity().x <= maxHorizontalVelocity)
            applyImpulse(horizontalImpulse, 0);
    }

    /**
     * Applies a linear impulse to the body at its center of mass.
     *
     * @param horizontalImpulse The impulse in the horizontal direction.
     * @param verticalImpulse   The impulse in the vertical direction.
     */
    public void applyImpulse(float horizontalImpulse, float verticalImpulse){
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(horizontalImpulse, verticalImpulse, center.x, center.y, true);
    }

    /**
     * Clears the entity's horizontal velocity.
     */
    public void clearVelocityX(){
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(0, oldVelocity.y);
    }

    /**
     * Clears the entity's vertical velocity, ensuring it doesn't go negative.
     */
    public void clearVelocityY(){
        Vector2 oldVelocity = body.getLinearVelocity();
        body.setLinearVelocity(oldVelocity.x, Math.max(0, oldVelocity.y));
    }

    /**
     * Checks if the entity's horizontal velocity is below the threshold for being considered idle.
     *
     * @return true if the entity is effectively idle; false otherwise.
     */
    public boolean isBodyEffectivelyIdle() {
        return Math.abs(body.getLinearVelocity().x) < minNotIdleVelocity;
    }

    /**
     * Checks if the entity is facing to the right.
     *
     * @return true if the entity is facing right; false if facing left.
     */
    public boolean isFacingRight() {
        return isFacingRight;
    }

    /**
     * Sets the facing direction of the entity.
     *
     * @param facingRight true to set the entity facing right; false to set it facing left.
     */
    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }

    /**
     * Updates a timer value with the delta time, ensuring it doesn't go negative.
     *
     * @param value The current timer value.
     * @param delta The time elapsed since the last update.
     * @return The updated timer value.
     */
    protected float updateTimer(float value, float delta) {
        return Math.max(0, value - delta);
    }
}
