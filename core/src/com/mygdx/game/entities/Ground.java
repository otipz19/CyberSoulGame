package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.Collider;

public class Ground extends GameObject {
    public Ground(Level level, Collider collider) {
        this.level = level;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(collider.getX(), collider.getY());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = collider.getShape();
        fixtureDef.friction = 1;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0;

        body = level.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);

        fixture.setUserData(this);
    }
}
