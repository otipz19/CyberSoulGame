package com.mygdx.game.entities;

public interface ITriggerListener {
    void onTriggerEnter(GameObject other);
    void onTriggerExit(GameObject other);
}
