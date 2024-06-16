package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.levels.Level;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;

/**
 * Represents a particle effect that spawns souls when collided with a Hero entity.
 * Extends the Particles class and implements the ICollisionListener interface.
 */
public class SoulParticles extends Particles implements ICollisionListener {

    /**
     * The static ParticleEffectPool used to manage and reuse ParticleEffect instances.
     */
    private final static ParticleEffectPool effectPool;

    static {
        ParticleEffect particleEffect = MyGdxGame.getInstance().assetManager.get(Assets.ParticleEffects.SOUL_PARTICLES, ParticleEffect.class);
        effectPool = new ParticleEffectPool(particleEffect, 1, 100);
    }

    /**
     * The amount of souls to spawn when collided with a Hero entity.
     */
    private int soulsAmount;

    /**
     * Constructs SoulParticles object.
     *
     * @param level       The Level where the particle effect exists.
     * @param x           The x-coordinate of the particle effect.
     * @param y           The y-coordinate of the particle effect.
     * @param soulsAmount The amount of souls to spawn when collided with a Hero entity.
     */
    public SoulParticles(Level level, float x, float y, int soulsAmount) {
        super(level, x, y, 0.5f, 0.5f);
        this.soulsAmount = soulsAmount;
    }

    /**
     * Creates and returns a ParticleEffect from the effect pool.
     *
     * @return The ParticleEffect obtained from the effect pool.
     */
    @Override
    public ParticleEffect createParticleEffect() {
        return effectPool.obtain();
    }

    /**
     * Retrieves the destruction delay for this particle effect (0 seconds for immediate destruction).
     *
     * @return The destruction delay in seconds.
     */
    @Override
    public float getDestructionDelay() {
        return 0f;
    }

    /**
     * Marks the particle effect as complete, frees its pooled instance, and grants souls to the Hero entity upon collision.
     *
     * @see ICollisionListener#onCollisionEnter(Entity)
     */
    @Override
    protected void destruct() {
        isComplete = true;
        level.world.destroyBody(body);
        ((ParticleEffectPool.PooledEffect) particleEffect).free();
    }

    /**
     * Grants the souls to the Hero entity upon collision and completes the particle effect.
     * Plays a sound effect when the Hero collects the souls.
     *
     * @param other The Entity with which the collision occurred.
     * @see ICollisionListener#onCollisionEnter(Entity)
     */
    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Hero hero) {
            hero.getResourcesManager().changeSouls(soulsAmount);
            complete();
            SoundPlayer.getInstance().playSound(Assets.Sound.PICKING_SOUL_SOUND);
        }
    }

    /**
     * Handles the onCollisionExit event.
     *
     * @param other The Entity with which the collision occurred.
     * @see ICollisionListener#onCollisionExit(Entity)
     */
    @Override
    public void onCollisionExit(Entity other) {
        // No action needed on collision exit for SoulParticles
    }
}
