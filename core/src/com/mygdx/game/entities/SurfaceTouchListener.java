package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class SurfaceTouchListener extends GameObject implements ITriggerListener{
    private int surfaceTouchesNumber;

    public SurfaceTouchListener(Entity parent, ListenerPosition position){
        this.level = parent.level;
        this.body = parent.body;

        Shape colliderShape = position.getColliderShape(parent.width, parent.height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = colliderShape;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        colliderShape.dispose();
    }

    public boolean isOnSurface() {
        return surfaceTouchesNumber > 0;
    }

    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof Surface)
            surfaceTouchesNumber++;
    }

    @Override
    public void onTriggerExit(GameObject other) {
        if (other instanceof Surface)
            surfaceTouchesNumber--;
    }

    public enum ListenerPosition {
        BOTTOM(0.49f, 0.05f, 0.5f, 0f),
        TOP(0.49f, 0.05f, 0.5f, 1f),
        LEFT(0.05f, 0.49f, 0f, 0.5f),
        RIGHT(0.05f, 0.49f, 1f, 0.5f),
        SIDES(0.51f, 0.49f, 0.5f, 0.5f),
        TOP_AND_BOTTOM(0.49f, 0.51f, 0.5f, 0.5f);

        private final float widthMultiplier;
        private final float heightMultiplier;
        private final float deltaXCoefficient;
        private final float deltaYCoefficient;

        ListenerPosition(float widthMultiplier, float heightMultiplier, float deltaXCoefficient, float deltaYCoefficient) {
            this.widthMultiplier = widthMultiplier;
            this.heightMultiplier = heightMultiplier;
            this.deltaXCoefficient = deltaXCoefficient;
            this.deltaYCoefficient = deltaYCoefficient;
        }

        public Shape getColliderShape(float parentWidth, float parentHeight) {
            PolygonShape shape = new PolygonShape();
            float hw = parentWidth * widthMultiplier;
            float hh = parentHeight * heightMultiplier;
            float x = parentWidth * deltaXCoefficient;
            float y = parentHeight * deltaYCoefficient;
            shape.setAsBox(hw, hh, new Vector2(x,y), 0);
            return shape;
        }
    }
}
