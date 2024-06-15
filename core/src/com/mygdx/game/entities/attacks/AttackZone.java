package com.mygdx.game.entities.attacks;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.utils.DelayedAction;
/**
 * AttackZone represents a sensor zone attached to an entity's body in the game world,
 * used for detecting collisions during attacks.
 * It extends GameObject and implements the Disposable interface for cleanup purposes.
 */
public class AttackZone extends GameObject implements Disposable {
    private ICollisionListener attackHandler;
    private final FixtureDef fixtureDefinition;
    private Fixture fixture;

    /**
     * Constructs an AttackZone instance for the specified parent entity and position.
     *
     * @param parent    The parent entity to which the attack zone is attached.
     * @param position  The position of the attack zone relative to the parent entity.
     * @param zoneWidth The width of the attack zone in game units.
     * @param zoneHeight The height of the attack zone in game units.
     */
    public AttackZone(Entity parent, AttackZonePosition position, float zoneWidth, float zoneHeight) {
        this.level = parent.getLevel();
        this.body = parent.getBody();

        Shape colliderShape = position.getColliderShape(parent.getWidth(), parent.getHeight(), zoneWidth, zoneHeight);

        fixtureDefinition = new FixtureDef();
        fixtureDefinition.shape = colliderShape;
        fixtureDefinition.isSensor = true;
    }

    /**
     * Retrieves the attack handler responsible for processing collision events.
     *
     * @return The current attack handler assigned to this attack zone.
     */
    public ICollisionListener getAttackHandler() {
        return attackHandler;
    }

    /**
     * Sets the attack handler responsible for processing collision events.
     *
     * @param attackHandler The attack handler to set for this attack zone.
     */
    public void setAttackHandler(ICollisionListener attackHandler) {
        this.attackHandler = attackHandler;
        if (fixture != null)
            fixture.setUserData(attackHandler);
    }

    /**
     * Enables the attack zone by attaching its fixture to the parent entity's body.
     * If the attack handler is not set or if the fixture is already enabled, does nothing.
     */
    public void enable() {
        if (attackHandler == null || fixture != null)
            return;
        fixture = body.createFixture(fixtureDefinition);
        fixture.setUserData(attackHandler);
    }

    /**
     * Enables the attack zone for a specified duration and then disables it.
     *
     * @param enabledTime The duration in seconds for which the attack zone remains enabled.
     */
    public void enable(float enabledTime) {
        enable();
        level.addDelayedAction(enabledTime, this::disable);
    }

    /**
     * Disables the attack zone by removing its fixture from the parent entity's body.
     * If the fixture is already disabled or does not exist, does nothing.
     */
    public void disable() {
        if (!body.getFixtureList().isEmpty() && fixture != null)
            body.destroyFixture(fixture);
        fixture = null;
    }

    /**
     * Cleans up resources associated with the attack zone, disposing its collider shape.
     */
    @Override
    public void dispose() {
        fixtureDefinition.shape.dispose();
    }
}
