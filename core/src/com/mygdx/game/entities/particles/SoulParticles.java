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

public class SoulParticles extends Particles implements ICollisionListener {
    private final static ParticleEffectPool effectPool;
    static {
        ParticleEffect particleEffect = MyGdxGame.getInstance().assetManager.get(Assets.ParticleEffects.SOUL_PARTICLES, ParticleEffect.class);
        effectPool = new ParticleEffectPool(particleEffect, 1, 100);
    }

    private int soulsAmount;
    public SoulParticles(Level level, float x, float y, int soulsAmount){
        super(level, x, y, 0.5f, 0.5f);
        this.soulsAmount = soulsAmount;
    }

    @Override
    public ParticleEffect createParticleEffect() {
        return effectPool.obtain();
    }

    @Override
    public float getDestructionDelay() {
        return 0f;
    }

    @Override
    protected void destruct() {
        isComplete = true;
        level.world.destroyBody(body);
        ((ParticleEffectPool.PooledEffect)particleEffect).free();
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Hero hero) {
            hero.getResourcesManager().changeSouls(soulsAmount);
            complete();
            SoundPlayer.getInstance().playSound(Assets.Sound.PICKING_SOUL_SOUND);
        }
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
