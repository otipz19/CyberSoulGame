package com.mygdx.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Arrays;
/**
 * Extends AssetManager to provide custom loading functionalities for game assets.
 */
public class MyAssetManager extends AssetManager {
    public MyAssetManager() {
        setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        setLoader(ParticleEffect.class, new ParticleEffectLoader(new InternalFileHandleResolver()));
    }

    public void loadAll() {
        Arrays.stream(Assets.class.getDeclaredClasses()).forEach(assetsClass -> {
            Class assetType = switch(assetsClass.getSimpleName()) {
                case "Textures" -> Texture.class;
                case "TiledMaps" -> TiledMap.class;
                case "TextureAtlases" -> TextureAtlas.class;
                case "Skins" -> Skin.class;
                case "ParticleEffects" -> ParticleEffect.class;
                case "Music" -> Music.class;
                case "Sound" -> Sound.class;
                default -> throw new IllegalStateException("Unexpected value: " + assetsClass.getSimpleName());
            };

            Arrays.stream(assetsClass.getDeclaredFields()).forEach(assetField -> {
                try {
                    String assetName = (String) assetField.get(null);
                    load(assetName, assetType);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("MyAssetManager failed... :( " + e);
                }
            });
        });

        finishLoading();
    }
}
