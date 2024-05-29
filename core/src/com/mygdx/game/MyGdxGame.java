package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.levels.TestLevel;
import com.mygdx.game.utils.AssetsNames;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class MyGdxGame extends Game {
    private static MyGdxGame instance;
    private Level currentLevel;
    public AssetManager assetManager;
    public SpriteBatch batch;

    public static MyGdxGame getInstance() {
        return instance;
    }

    @Override
    public void create() {
        instance = this;
        loadAssets();
        Box2D.init();
        batch = new SpriteBatch();
        changeLevel(new TestLevel(this));
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(AssetsNames.TEST_LEVEL_TILEMAP, TiledMap.class);
        assetManager.load(AssetsNames.GREENZONE_BACKGROUND_FULL, Texture.class);
        assetManager.load(AssetsNames.UI_HEART, Texture.class);
        assetManager.load(AssetsNames.UI_SHIELD, Texture.class);
        assetManager.load(AssetsNames.UI_SOUL, Texture.class);
        Stream.of(AssetsNames.BIKER_RUN_SHEET,
                        AssetsNames.BIKER_JUMP_SHEET,
                        AssetsNames.BIKER_IDLE_SHEET,
                        AssetsNames.BIKER_ATTACK1_SHEET,
                        AssetsNames.BIKER_ATTACK2_SHEET,
                        AssetsNames.BIKER_CLIMB_SHEET,
                        AssetsNames.BIKER_DEATH_SHEET,
                        AssetsNames.BIKER_DOUBLEJUMP_SHEET,
                        AssetsNames.BIKER_HURT_SHEET,
                        AssetsNames.BIKER_PUNCH_SHEET,
                        AssetsNames.BIKER_RUN_ATTACK_SHEET,
                        AssetsNames.ENTRY_OBSTACLE_CLOSED,
                        AssetsNames.ENTRY_OBSTACLE_CLOSING,
                        AssetsNames.ENTRY_OBSTACLE_OPENING)
                .forEach(str -> assetManager.load(str, Texture.class));
        assetManager.finishLoading();
    }

    public void changeLevel(Level level){
        currentLevel = level;
        setScreen(level);
    }

    public void restartCurrentLevel() {
        try {
            var levelConstructor = currentLevel.getClass().getDeclaredConstructor(MyGdxGame.class);
            changeLevel(levelConstructor.newInstance(this));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit(){
        System.exit(0);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
