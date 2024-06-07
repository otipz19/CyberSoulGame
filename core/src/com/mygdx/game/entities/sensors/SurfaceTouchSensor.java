package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.Surface;

public class SurfaceTouchSensor extends Sensor {
    private int surfaceTouchesNumber;

    public SurfaceTouchSensor(Entity parent, SensorPosition position){
        super(parent);
        Shape colliderShape = position.getColliderShape(parent.getWidth(), parent.getHeight());
        createFixture(colliderShape);
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
