package com.mygdx.game.entities;
/**
 * ITriggerListener is an interface implemented by classes that need to handle
 * trigger events in the game.
 */
public interface ITriggerListener {
    void onTriggerEnter(GameObject other);
    void onTriggerExit(GameObject other);
}
