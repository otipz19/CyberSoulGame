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
        if (a instanceof GameObject aObject && b instanceof GameObject bObject){
            if (aObject instanceof ICollisionListener aListener)
                aListener.onCollisionEnter(bObject);
            if (aObject instanceof ITriggerListener aListener)
                aListener.onTriggerEnter(bObject);
            if (bObject instanceof ICollisionListener bListener)
                bListener.onCollisionEnter(aObject);
            if (bObject instanceof ITriggerListener bListener)
                bListener.onTriggerEnter(aObject);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object a = contact.getFixtureA().getUserData();
        Object b = contact.getFixtureB().getUserData();
        if (a instanceof GameObject aObject && b instanceof GameObject bObject){
            if (a instanceof ICollisionListener aListener)
                aListener.onCollisionExit(bObject);
            if (a instanceof ITriggerListener aListener)
                aListener.onTriggerExit(bObject);
            if (b instanceof ICollisionListener bListener)
                bListener.onCollisionExit(aObject);
            if (b instanceof ITriggerListener bListener)
                bListener.onTriggerExit(aObject);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
