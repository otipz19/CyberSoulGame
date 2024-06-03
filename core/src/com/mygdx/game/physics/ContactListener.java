package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.ITriggerListener;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
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

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
