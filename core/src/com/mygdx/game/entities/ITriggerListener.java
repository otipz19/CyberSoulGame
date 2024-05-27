package com.mygdx.game.entities;

public interface ITriggerListener {
    void onTriggerEnter(Entity other);
    void onTriggerExit(Entity other);
}
