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

/**
 * Abstract class representing a particle effect in the game.
 * Extends GameObject and implements IRenderable for rendering capabilities.
 */
public abstract class Particles extends GameObject implements IRenderable {

    /**
     * The particle effect associated with this object.
     */
    protected final ParticleEffect particleEffect;

    /**
     * Flag indicating whether the particle effect has completed.
     */
    protected boolean isComplete;

    /**
     * Array of actions to be executed upon completion of the particle effect.
     */
    protected Array<Runnable> onCompleteActions;

    /**
     * Constructs a Particles object.
     *
     * @param level  The level where the particle effect exists.
     * @param x      The x-coordinate of the particle effect.
     * @param y      The y-coordinate of the particle effect.
     * @param width  The width of the particle effect's collider.
     * @param height The height of the particle effect's collider.
     */
    public Particles(Level level, float x, float y, float width, float height) {
        this.level = level;
        this.body = BodyCreator.createSensorBody(level.world, ColliderCreator.create(x - width / 2, y - width / 2, width, height), this);

        particleEffect = createParticleEffect();
        particleEffect.setPosition(x, y);
        particleEffect.start();

        onCompleteActions = new Array<>();
        onCompleteActions.add(() -> new DelayedAction(getDestructionDelay(), this::destruct));

        level.addParticleEffect(this);
    }

    /**
     * Creates and returns the specific particle effect associated with this object.
     *
     * @return The ParticleEffect object created for this particle effect.
     */
    public abstract ParticleEffect createParticleEffect();

    /**
     * Renders the particle effect.
     *
     * @param deltaTime The time elapsed since the last frame, in seconds.
     */
    @Override
    public void render(float deltaTime) {
        if (isComplete)
            return;

        particleEffect.update(deltaTime);
        particleEffect.draw(MyGdxGame.getInstance().batch);
        if (particleEffect.isComplete())
            complete();
    }

    /**
     * Executes all actions registered to occur upon completion of the particle effect.
     * Clears the list of onCompleteActions afterward.
     */
    public void complete() {
        onCompleteActions.forEach(Runnable::run);
        onCompleteActions.clear();
    }

    /**
     * Marks the particle effect as complete, destroys its associated body and disposes of the particle effect.
     */
    protected void destruct() {
        isComplete = true;
        level.world.destroyBody(body);
        particleEffect.dispose();
    }

    /**
     * Retrieves the destruction delay for this particle effect.
     *
     * @return The delay in seconds before the particle effect is destructed.
     */
    public abstract float getDestructionDelay();

    /**
     * Adds an action to be executed upon completion of the particle effect.
     *
     * @param runnable The action to be added.
     */
    public void addOnCompleteAction(Runnable runnable) {
        onCompleteActions.add(runnable);
    }

    /**
     * Removes a specific action from the list of actions to be executed upon completion.
     *
     * @param runnable The action to be removed.
     */
    public void removeOnCompleteAction(Runnable runnable) {
        onCompleteActions.removeValue(runnable, true);
    }

    /**
     * Clears all actions registered to occur upon completion of the particle effect.
     */
    public void clearOnCompleteActions() {
        onCompleteActions.clear();
    }
}
