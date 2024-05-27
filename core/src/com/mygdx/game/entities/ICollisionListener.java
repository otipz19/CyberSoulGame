package com.mygdx.game.entities;

public interface ICollisionListener {
    void onCollisionEnter(Entity other);
    void onCollisionExit(Entity other);
}
