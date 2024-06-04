package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utils.DelayedAction;

public abstract class Particles extends GameObject {
    private final ParticleEffect particleEffect;
    private boolean isComplete;
    protected Array<Runnable> onCompleteActions;
    public Particles(Level level, float x, float y, float width, float height, ParticleEffect prototype) {
        this.level = level;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = level.world.createBody(bodyDef);

        PolygonShape colliderShape = new PolygonShape();
        colliderShape.setAsBox(width/2, height/2, new Vector2(0, 0), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = colliderShape;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        colliderShape.dispose();

        particleEffect = new ParticleEffect(prototype);
        particleEffect.setPosition(x, y);
        particleEffect.start();

        onCompleteActions = new Array<>();
        onCompleteActions.add(() -> new DelayedAction(getDestructionDelay(), this::destruct));
    }
    
    public void render(float deltaTime) {
        if (isComplete)
            return;

        particleEffect.update(deltaTime);
        particleEffect.draw(MyGdxGame.getInstance().batch, deltaTime);
        if (particleEffect.isComplete())
            complete();
    }

    public void complete() {
        onCompleteActions.forEach(Runnable::run);
        onCompleteActions.clear();
    }

    protected void destruct() {
        isComplete = true;
        level.world.destroyBody(body);
        particleEffect.dispose();
    }

    public abstract float getDestructionDelay();

    public void addOnCompleteAction(Runnable runnable) {
        onCompleteActions.add(runnable);
    }

    public void removeOnCompleteAction(Runnable runnable) {
        onCompleteActions.removeValue(runnable, true);
    }

    public void clearOnCompleteActions() {
        onCompleteActions.clear();
    }
}
