package com.mygdx.game.entities.movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Controls the movement behavior of ground-based enemies within specified range constraints.
 * Extends the MovementController class.
 */
public class GroundEnemyMovementController extends MovementController {

    /** The maximum velocity when patrolling. */
    public float maxPatrolVelocity;

    /** The maximum velocity when attacking. */
    public float maxAttackVelocity;

    /** The minimum x-coordinate limit for movement. */
    public float minX;

    /** The maximum x-coordinate limit for movement. */
    public float maxX;

    /**
     * Constructs a GroundEnemyMovementController instance for a given Body with specified movement boundaries.
     *
     * @param body The Box2D Body associated with the enemy.
     * @param minX The minimum x-coordinate limit within which the enemy can move.
     * @param maxX The maximum x-coordinate limit within which the enemy can move.
     */
    public GroundEnemyMovementController(Body body, float minX, float maxX) {
        super(body);
        this.minX = minX;
        this.maxX = maxX;
        maxPatrolVelocity = 2f;
        maxAttackVelocity = 3f;
        horizontalImpulse = 3f;
        maxHorizontalVelocity = maxPatrolVelocity;
    }

    /**
     * Causes the enemy to patrol within its specified range, changing direction if it reaches a boundary.
     */
    public void patrol() {
        changeToPatrolMode();
        if (isFacingRight()) {
            boolean hasMoved = tryMoveRightInRange();
            if (!hasMoved)
                isFacingRight = false;
        } else {
            boolean hasMoved = tryMoveLeftInRange();
            if (!hasMoved)
                isFacingRight = true;
        }
    }

    /**
     * Attempts to move the enemy right within its allowed range.
     *
     * @return true if the enemy successfully moves right within the range; false otherwise.
     */
    public boolean tryMoveRightInRange() {
        moveRight();
        Vector2 newPosition = body.getPosition();
        if (isGreater(newPosition.x, maxX)) {
            body.setTransform(maxX, newPosition.y, 0);
            return false;
        }
        return true;
    }

    /**
     * Attempts to move the enemy left within its allowed range.
     *
     * @return true if the enemy successfully moves left within the range; false otherwise.
     */
    public boolean tryMoveLeftInRange() {
        moveLeft();
        Vector2 newPosition = body.getPosition();
        if (isLess(newPosition.x, minX)) {
            body.setTransform(minX, newPosition.y, 0);
            return false;
        }
        return true;
    }

    /**
     * Checks if one float value is greater than another with a small epsilon margin.
     *
     * @param first  The first float value to compare.
     * @param second The second float value to compare.
     * @return true if the first value is greater than the second; false otherwise.
     */
    private boolean isGreater(float first, float second) {
        return isLess(second, first);
    }

    /**
     * Checks if one float value is less than another with a small epsilon margin.
     *
     * @param first  The first float value to compare.
     * @param second The second float value to compare.
     * @return true if the first value is less than the second; false otherwise.
     */
    private boolean isLess(float first, float second) {
        final float epsilon = 0.1f;
        return (first - second) <= epsilon;
    }

    /**
     * Attempts to move the enemy towards a specified position.
     *
     * @param position The target position to move towards.
     * @return true if the enemy successfully moves towards the position; false otherwise.
     */
    public boolean tryMoveTo(Vector2 position) {
        float deltaX = position.x - body.getPosition().x;
        if (deltaX > 0)
            return tryMoveRightInRange();
        else
            return tryMoveLeftInRange();
    }

    /**
     * Changes the movement mode of the enemy to patrol mode, adjusting maximum velocity accordingly.
     */
    public void changeToPatrolMode() {
        maxHorizontalVelocity = maxPatrolVelocity;
    }

    /**
     * Changes the movement mode of the enemy to attack mode, adjusting maximum velocity accordingly.
     */
    public void changeToAttackMode() {
        maxHorizontalVelocity = maxAttackVelocity;
    }
}
