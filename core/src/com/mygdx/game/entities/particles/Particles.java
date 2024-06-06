package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.IRenderable;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.utils.DelayedAction;

public abstract class Particles extends GameObject implements IRenderable {
    protected final ParticleEffect particleEffect;
    protected boolean isComplete;
    protected Array<Runnable> onCompleteActions;

    public Particles(Level level, float x, float y, float width, float height) {
        this.level = level;
        this.body = BodyCreator.createSensorBody(level.world, ColliderCreator.create(x-width/2, y-width/2, width, height), this);

        particleEffect = createParticleEffect();
        particleEffect.setPosition(x, y);
        particleEffect.start();

        onCompleteActions = new Array<>();
        onCompleteActions.add(() -> new DelayedAction(getDestructionDelay(), this::destruct));

        level.addParticleEffect(this);
    }

    public abstract ParticleEffect createParticleEffect();

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
