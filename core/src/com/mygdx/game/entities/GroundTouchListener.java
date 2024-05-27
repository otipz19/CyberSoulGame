package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class GroundTouchListener extends GameObject implements ITriggerListener{
    private int groundTouchesNumber;

    public GroundTouchListener(Entity parent){
        this.level = parent.level;
        this.body = parent.body;

        PolygonShape colliderShape = new PolygonShape();
        colliderShape.setAsBox(parent.width*0.49f, parent.height*0.05f, new Vector2(parent.width*0.5f, 0), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = colliderShape;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        colliderShape.dispose();
    }

    public boolean isOnGround() {
        return groundTouchesNumber > 0;
    }

    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof Ground)
            groundTouchesNumber++;
    }

    @Override
    public void onTriggerExit(GameObject other) {
        if (other instanceof Ground)
            groundTouchesNumber--;
    }
}
