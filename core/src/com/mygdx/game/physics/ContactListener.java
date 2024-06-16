package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.ITriggerListener;

/**
 * Implements Box2D's ContactListener interface to handle collision and trigger events between entities.
 */
public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    /**
     * Called when two fixtures start to overlap.
     *
     * @param contact The contact information.
     */
    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getUserData();
        Object b = contact.getFixtureB().getUserData();
        if (a instanceof ITriggerListener aListener && b instanceof GameObject bObject)
            aListener.onTriggerEnter(bObject);
        if (a instanceof ICollisionListener aListener && b instanceof Entity bEntity)
            aListener.onCollisionEnter(bEntity);
        if (b instanceof ITriggerListener bListener && a instanceof GameObject aObject)
            bListener.onTriggerEnter(aObject);
        if (b instanceof ICollisionListener bListener && a instanceof Entity aEntity)
            bListener.onCollisionEnter(aEntity);
    }

    /**
     * Called when two fixtures stop overlapping.
     *
     * @param contact The contact information.
     */
    @Override
    public void endContact(Contact contact) {
        Object a = contact.getFixtureA().getUserData();
        Object b = contact.getFixtureB().getUserData();
        if (a instanceof ITriggerListener aListener && b instanceof GameObject bObject)
            aListener.onTriggerExit(bObject);
        if (a instanceof ICollisionListener aListener && b instanceof Entity bEntity)
            aListener.onCollisionExit(bEntity);
        if (b instanceof ITriggerListener bListener && a instanceof GameObject aObject)
            bListener.onTriggerExit(aObject);
        if (b instanceof ICollisionListener bListener && a instanceof Entity aEntity)
            bListener.onCollisionExit(aEntity);
    }

    /**
     * This is called before a contact is resolved. It allows you to disable the contact based on the current configuration.
     *
     * @param contact  The contact information.
     * @param manifold The manifold detailing the contact points and normal information.
     */
    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        // Not implemented in this listener.
    }

    /**
     * This is called after a contact is solved, where impulse is resolved.
     *
     * @param contact        The contact information.
     * @param contactImpulse The impulse caused by the contact.
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }
}
