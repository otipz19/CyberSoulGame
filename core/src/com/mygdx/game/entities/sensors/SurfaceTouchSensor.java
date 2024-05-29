package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ITriggerListener;
import com.mygdx.game.entities.obstacles.Surface;

public class SurfaceTouchSensor extends GameObject implements ITriggerListener {
    private int surfaceTouchesNumber;

    public SurfaceTouchSensor(Entity parent, SensorPosition position){
        this.level = parent.getLevel();
        this.body = parent.getBody();

        Shape colliderShape = position.getColliderShape(parent.getWidth(), parent.getHeight());

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

}
