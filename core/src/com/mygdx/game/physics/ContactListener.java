package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.ITriggerListener;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();
        if (a instanceof Entity aEntity && b instanceof Entity bEntity){
            if (aEntity instanceof ICollisionListener aListener)
                aListener.onCollisionEnter(bEntity);
            if (aEntity instanceof ITriggerListener aListener)
                aListener.onTriggerEnter(bEntity);
            if (bEntity instanceof ICollisionListener bListener)
                bListener.onCollisionEnter(aEntity);
            if (bEntity instanceof ITriggerListener bListener)
                bListener.onTriggerEnter(aEntity);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();
        if (a instanceof Entity aEntity && b instanceof Entity bEntity){
            if (a instanceof ICollisionListener aListener)
                aListener.onCollisionExit(bEntity);
            if (a instanceof ITriggerListener aListener)
                aListener.onTriggerExit(bEntity);
            if (b instanceof ICollisionListener bListener)
                bListener.onCollisionExit(aEntity);
            if (b instanceof ITriggerListener bListener)
                bListener.onTriggerExit(aEntity);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
