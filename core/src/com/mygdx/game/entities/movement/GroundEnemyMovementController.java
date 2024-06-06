package com.mygdx.game.entities.movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class GroundEnemyMovementController extends MovementController{
    public float maxAttackVelocity;
    public float maxPatrolVelocity;
    public float minX;
    public float maxX;

    public GroundEnemyMovementController(Body body, float minX, float maxX){
        super(body);
        this.minX = minX;
        this.maxX = maxX;
        maxPatrolVelocity = 2f;
        maxAttackVelocity = 3f;
        horizontalImpulse = 3f;
        maxHorizontalVelocity = maxPatrolVelocity;
    }

    public void patrol() {
        changeToPatrolMode();
        if (isFacingRight()) {
            boolean hasMoved = tryMoveRightInRange();
            if (!hasMoved)
                isFacingRight = false;
        }
        else {
            boolean hasMoved = tryMoveLeftInRange();
            if (!hasMoved)
                isFacingRight = true;
        }
    }

    public boolean tryMoveRightInRange() {
        moveRight();
        Vector2 newPosition = body.getPosition();
        if (newPosition.x > maxX) {
            body.setTransform(maxX, newPosition.y, 0);
            return false;
        }
        return true;
    }

    public boolean tryMoveLeftInRange() {
        moveLeft();
        Vector2 newPosition = body.getPosition();
        if (newPosition.x < minX) {
            body.setTransform(minX, newPosition.y, 0);
            return false;
        }
        return true;
    }

    public boolean tryMoveTo(Vector2 position) {
        float deltaX = position.x - body.getPosition().x;
        if (deltaX > 0)
            return tryMoveRightInRange();
        else
            return tryMoveLeftInRange();
    }

    public void changeToPatrolMode() {
        maxHorizontalVelocity = maxPatrolVelocity;
    }

    public void changeToAttackMode() {
        maxHorizontalVelocity = maxAttackVelocity;
    }
}
