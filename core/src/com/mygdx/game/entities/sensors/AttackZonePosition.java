package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public enum AttackZonePosition {
    RIGHT_MIDDLE {
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
    RIGHT_TOP {
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
    RIGHT_BOTTOM {
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
    LEFT_MIDDLE {
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
    LEFT_TOP {
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
    LEFT_BOTTOM {
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

    public abstract Shape getColliderShape(float parentWidth, float parentHeight, float zoneWidth, float zoneHeight);
}
