package com.mygdx.game.entities.sensors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.utils.DelayedAction;

public class AttackZone extends GameObject implements Disposable {
    private ICollisionListener attackHandler;
    private final FixtureDef fixtureDefinition;
    private Fixture fixture;

    public AttackZone(Entity parent, AttackZonePosition position, float zoneWidth, float zoneHeight) {
        this.level = parent.getLevel();
        this.body = parent.getBody();

        Shape colliderShape = position.getColliderShape(parent.getWidth(), parent.getHeight(), zoneWidth, zoneHeight);

        fixtureDefinition = new FixtureDef();
        fixtureDefinition.shape = colliderShape;
        fixtureDefinition.isSensor = true;
    }

    public ICollisionListener getAttackHandler() {
        return attackHandler;
    }

    public void setAttackHandler(ICollisionListener attackHandler) {
        this.attackHandler = attackHandler;
        if (fixture != null)
            fixture.setUserData(attackHandler);
    }

    public void enable() {
        if (attackHandler == null || fixture != null)
            return;
        fixture = body.createFixture(fixtureDefinition);
        fixture.setUserData(attackHandler);
    }

    public void enable(float enabledTime) {
        enable();
        new DelayedAction(enabledTime, this::disable);
    }

    public void disable() {
        body.destroyFixture(fixture);
        fixture = null;
    }

    @Override
    public void dispose() {
        fixtureDefinition.shape.dispose();
    }
}
