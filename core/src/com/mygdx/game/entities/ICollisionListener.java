package com.mygdx.game.entities;

public interface ICollisionListener {
    void onCollisionEnter(GameObject other);
    void onCollisionExit(GameObject other);
}
