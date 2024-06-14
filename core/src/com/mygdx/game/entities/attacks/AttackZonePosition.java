package com.mygdx.game.entities.attacks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
/**
 * AttackZonePosition is an enumeration representing different positions
 * for defining collision shapes relative to a parent entity's dimensions.
 */
public enum AttackZonePosition {

    /**
     * Defines a collision shape positioned at the middle right of the parent entity.
     */
    RIGHT_MIDDLE {
        /**
         * Generates a polygon shape for collision detection at the middle right position.
         *
         * @param parentWidth  The width of the parent entity.
         * @param parentHeight The height of the parent entity.
         * @param zoneWidth    The width of the collision zone.
         * @param zoneHeight   The height of the collision zone.
         * @return A PolygonShape instance representing the collision shape.
         */
        @Override
        public Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight) {
            PolygonShape shape = new PolygonShape();
            float hw = zoneWidth * 0.5f;
            float hh = zoneHeight * 0.5f;
            float x = parentWidth + hw;
            float y = parentHeight * 0.5f;
            shape.setAsBox(hw, hh, new Vector2(x, y), 0);
            return shape;
        }
    },

    /**
     * Defines a collision shape positioned at the top right of the parent entity.
     */
    RIGHT_TOP {
        /**
         * Generates a polygon shape for collision detection at the top right position.
         *
         * @param parentWidth  The width of the parent entity.
         * @param parentHeight The height of the parent entity.
         * @param zoneWidth    The width of the collision zone.
         * @param zoneHeight   The height of the collision zone.
         * @return A PolygonShape instance representing the collision shape.
         */
        @Override
        public Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight) {
            PolygonShape shape = new PolygonShape();
            float hw = zoneWidth * 0.5f;
            float hh = zoneHeight * 0.5f;
            float x = parentWidth + hw;
            float y = parentHeight - hh;
            shape.setAsBox(hw, hh, new Vector2(x, y), 0);
            return shape;
        }
    },

    /**
     * Defines a collision shape positioned at the bottom right of the parent entity.
     */
    RIGHT_BOTTOM {
        /**
         * Generates a polygon shape for collision detection at the bottom right position.
         *
         * @param parentWidth  The width of the parent entity.
         * @param parentHeight The height of the parent entity.
         * @param zoneWidth    The width of the collision zone.
         * @param zoneHeight   The height of the collision zone.
         * @return A PolygonShape instance representing the collision shape.
         */
        @Override
        public Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight) {
            PolygonShape shape = new PolygonShape();
            float hw = zoneWidth * 0.5f;
            float hh = zoneHeight * 0.5f;
            float x = parentWidth + hw;
            float y = hh;
            shape.setAsBox(hw, hh, new Vector2(x, y), 0);
            return shape;
        }
    },

    /**
     * Defines a collision shape positioned at the middle left of the parent entity.
     */
    LEFT_MIDDLE {
        /**
         * Generates a polygon shape for collision detection at the middle left position.
         *
         * @param parentWidth  The width of the parent entity.
         * @param parentHeight The height of the parent entity.
         * @param zoneWidth    The width of the collision zone.
         * @param zoneHeight   The height of the collision zone.
         * @return A PolygonShape instance representing the collision shape.
         */
        @Override
        public Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight) {
            PolygonShape shape = new PolygonShape();
            float hw = zoneWidth * 0.5f;
            float hh = zoneHeight * 0.5f;
            float x = -hw;
            float y = parentHeight * 0.5f;
            shape.setAsBox(hw, hh, new Vector2(x, y), 0);
            return shape;
        }
    },

    /**
     * Defines a collision shape positioned at the top left of the parent entity.
     */
    LEFT_TOP {
        /**
         * Generates a polygon shape for collision detection at the top left position.
         *
         * @param parentWidth  The width of the parent entity.
         * @param parentHeight The height of the parent entity.
         * @param zoneWidth    The width of the collision zone.
         * @param zoneHeight   The height of the collision zone.
         * @return A PolygonShape instance representing the collision shape.
         */
        @Override
        public Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight) {
            PolygonShape shape = new PolygonShape();
            float hw = zoneWidth * 0.5f;
            float hh = zoneHeight * 0.5f;
            float x = -hw;
            float y = parentHeight - hh;
            shape.setAsBox(hw, hh, new Vector2(x, y), 0);
            return shape;
        }
    },

    /**
     * Defines a collision shape positioned at the bottom left of the parent entity.
     */
    LEFT_BOTTOM {
        /**
         * Generates a polygon shape for collision detection at the bottom left position.
         *
         * @param parentWidth  The width of the parent entity.
         * @param parentHeight The height of the parent entity.
         * @param zoneWidth    The width of the collision zone.
         * @param zoneHeight   The height of the collision zone.
         * @return A PolygonShape instance representing the collision shape.
         */
        @Override
        public Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight) {
            PolygonShape shape = new PolygonShape();
            float hw = zoneWidth * 0.5f;
            float hh = zoneHeight * 0.5f;
            float x = -hw;
            float y = hh;
            shape.setAsBox(hw, hh, new Vector2(x, y), 0);
            return shape;
        }
    };

    /**
     * Abstract method to be implemented by each enum constant to provide
     * a specific polygon shape for collision detection.
     *
     * @param parentWidth  The width of the parent entity.
     * @param parentHeight The height of the parent entity.
     * @param zoneWidth    The width of the collision zone.
     * @param zoneHeight   The height of the collision zone.
     * @return A Shape instance representing the collision shape.
     */
    public abstract Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight);
}
