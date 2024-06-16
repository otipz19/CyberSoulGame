package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;
/**
 * Represents a particle effect UI element that can be drawn on screen.
 * This class manages a {@link ParticleEffect} instance and its position offsets relative to the screen size.
 */
public class ParticleEffectsUI implements Disposable {
    private final ParticleEffect effect;
    private final float offsetX;
    private final float offsetY;

    public ParticleEffectsUI(String effectName, float screenWidth, float screenHeight, float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        effect = new ParticleEffect(MyGdxGame.getInstance().assetManager.get(effectName, ParticleEffect.class));
        effect.setPosition(screenWidth*offsetX, screenHeight*offsetY);
        effect.start();
    }

    public void draw(Batch batch, float deltaTime) {
        effect.draw(batch, deltaTime);
    }

    public void resize(float screenWidth, float screenHeight) {
        effect.setPosition(screenWidth*offsetX, screenHeight*offsetY);
    }

    public ParticleEffect getEffect() {
        return effect;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    @Override
    public void dispose() {
        effect.dispose();
    }
}
