package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

@FunctionalInterface
public interface UnprojectionDelegate {
    Vector3 unproject(Vector3 coordinates);
}
