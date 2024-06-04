package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utils.AssetsNames;

public class SoulParticles extends Particles implements ICollisionListener {

    private int soulsAmount;
    public SoulParticles(Level level, float x, float y, int soulsAmount){
        super(level, x, y, 0.5f, 0.5f, MyGdxGame.getInstance().assetManager.get(AssetsNames.SOUL_PARTICLES, ParticleEffect.class));
        this.soulsAmount = soulsAmount;
    }

    @Override
    public float getDestructionDelay() {
        return 0f;
    }

    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof Hero hero) {
            hero.getResourcesManager().changeSouls(soulsAmount);
            complete();
        }
    }

    @Override
    public void onCollisionExit(Entity other) {

    }
}
