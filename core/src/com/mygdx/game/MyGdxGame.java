package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.levels.*;
import com.mygdx.game.ui.MainMenu;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.PlayerDataManager;
import com.mygdx.game.utils.PlayerPreferencesManager;

import java.util.stream.Stream;

public class MyGdxGame extends Game {
    private static MyGdxGame instance;
    private InputMultiplexer inputMultiplexer;
    private Level currentLevel;
    public AssetManager assetManager;
    public SpriteBatch batch;

    private LevelChangeDelegate levelChangeDelegate;

    public static MyGdxGame getInstance() {
        return instance;
    }

    @Override
    public void create() {
        instance = this;
        changeDisplayMode();
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        loadAssets();
        Box2D.init();
        batch = new SpriteBatch();
        showMainMenu();
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load(AssetsNames.UI_MENU_BACKGROUND, Texture.class);
        assetManager.load(AssetsNames.GREENZONE_BACKGROUND_FULL, Texture.class);
        assetManager.load(AssetsNames.UI_ATLAS, TextureAtlas.class);
        assetManager.load(AssetsNames.UI_SKIN, Skin.class, new SkinLoader.SkinParameter(AssetsNames.UI_ATLAS));
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
                        AssetsNames.ENTRY_OBSTACLE_OPENING,
                        AssetsNames.STONE_6,
                        AssetsNames.FENCE_6,
                        AssetsNames.MONSTER_ATTACK1_SHEET,
                        AssetsNames.MONSTER_ATTACK2_SHEET,
                        AssetsNames.MONSTER_ATTACK3_SHEET,
                        AssetsNames.MONSTER_DEATH_SHEET,
                        AssetsNames.MONSTER_HURT_SHEET,
                        AssetsNames.MONSTER_WALK_SHEET,
                        AssetsNames.MONSTER_IDLE_SHEET,
                        AssetsNames.PORTAL_FIRST_INACTIVE_SPRITESHEET,
                        AssetsNames.PORTAL_SECOND_INACTIVE_SPRITESHEET,
                        AssetsNames.PORTAL_THIRD_INACTIVE_SPRITESHEET,
                        AssetsNames.PORTAL_FIRST_ACTIVATING_SPRITESHEET,
                        AssetsNames.PORTAL_SECOND_ACTIVATING_SPRITESHEET,
                        AssetsNames.PORTAL_THIRD_ACTIVATING_SPRITESHEET)
                .forEach(str -> assetManager.load(str, Texture.class));
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
//        assetManager.load(AssetsNames.TEST_LEVEL_TILEMAP, TiledMap.class);
        Stream.of(AssetsNames.GREENZONE_LEVEL_TILEMAP,
                        AssetsNames.POWERSTATION_LEVEL_TILEMAP,
                        AssetsNames.INDUSTRIALZONE_LEVEL_TILEMAP,
                        AssetsNames.SAFEZONE_LEVEL_TILEMAP)
                .forEach(str -> assetManager.load(str, TiledMap.class));
        assetManager.finishLoading();
    }

    public void showMainMenu() {
        if (getScreen() != null)
            getScreen().dispose();
        inputMultiplexer.clear();
        currentLevel = null;
        boolean hasAlreadyPlayed = PlayerDataManager.getInstance().getMaxLevel() != Levels.SAFE;
        setScreen(new MainMenu(hasAlreadyPlayed));
    }

    private void changeLevel(Levels level) {
        getScreen().dispose();
        inputMultiplexer.clear();
        currentLevel = level.create();
        PlayerDataManager.getInstance().setCurrentLevel(level);
        setScreen(currentLevel);
    }

//    public void levelCompleted(HeroData heroData) {
//        PlayerDataManager.getInstance().setHeroData(heroData);
//        levelChangeDelegate = () -> changeLevel(Levels.SAFE);
//    }

    public void goToNewLevel(Levels level) {
        levelChangeDelegate = () -> changeLevel(level);
    }

    public void levelFailed() {
        PlayerDataManager.getInstance().resetData();
        levelChangeDelegate = this::showMainMenu;
    }

    public void restartCurrentLevel() {
        changeLevel(PlayerDataManager.getInstance().getCurrentLevel());
    }

    public void changeDisplayMode() {
        if (PlayerPreferencesManager.getInstance().isFullScreen()) {
            Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
            Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
            Gdx.graphics.setFullscreenMode(displayMode);
        } else {
            Gdx.graphics.setWindowedMode(960, 640);
        }
    }

    public void exit() {
        Gdx.app.exit();
    }

    @Override
    public void render() {
        super.render();
        if(levelChangeDelegate != null) {
            levelChangeDelegate.changeLevel();
            levelChangeDelegate = null;
        }
    }

    @Override
    public void dispose() {
        PlayerDataManager.getInstance().saveData();
        PlayerPreferencesManager.getInstance().savePreferences();
        currentLevel.dispose();
        assetManager.dispose();
        batch.dispose();
    }

    public enum Levels {
        SAFE {
            public Level create() {
                return new SafeZoneLevel();
            }
        },
        FIRST {
            public Level create() {
                return new GreenZoneLevel();
            }
        },
        SECOND {
            public Level create() {
                return new PowerStationZone();
            }
        },
        THIRD {
            public Level create() {
                return new IndustrialZoneLevel();
            }
        };

        public abstract Level create();
    }

    private interface LevelChangeDelegate {
        void changeLevel();
    }
}
