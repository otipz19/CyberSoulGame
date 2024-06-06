package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.levels.Level;

public abstract class GameObject {
    protected Body body;
    protected Level level;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
