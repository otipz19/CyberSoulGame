package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
    public static final boolean IS_DEV_MODE = true;
    public static final boolean IS_DEBUG_MODE = true;

    private static MyGdxGame instance;
    private InputMultiplexer inputMultiplexer;
    private Level currentLevel;
    public AssetManager assetManager;
    public SpriteBatch batch;
    private LevelChangeDelegate levelChangeDelegate;
    private int screenId;

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
        Stream.of(AssetsNames.GREENZONE_BG_MUSIC,
                        AssetsNames.CREDIT_MUSIC,
                        AssetsNames.POWERSTATION_BG_MUSIC,
                        AssetsNames.INDUSTRIALZONE_BG_MUSIC,
                        AssetsNames.BG_MUSIC1,
                        AssetsNames.INTRO_MUSIC,
                        AssetsNames.WINNER_MUSIC,
                        AssetsNames.FUN_MENU_MUSIC,
                        AssetsNames.DEFAULT_MENU_MUSIC)
                .forEach(str -> assetManager.load(str, Music.class));
        Stream.of(AssetsNames.JUMP_SOUND,
                        AssetsNames.ATTACK_SOUND,
                        AssetsNames.ATTACK_COMBO_SOUND,
                        AssetsNames.ATTACK_KICK_SOUND,
                        AssetsNames.TELEPORT_SOUND,
                        AssetsNames.DRINKING_SOUND,
                        AssetsNames.PORTAL_CHARGING_SOUND,
                        AssetsNames.DASH_SOUND,
                        AssetsNames.HERO_HURT_SOUND,
                        AssetsNames.RANGE_ATTACK_SOUND,
                        AssetsNames.ATTACK_SWORD_SOUND,
                        AssetsNames.ATTACK_SWORD_HIT_SOUND,
                        AssetsNames.PICKING_SOUL_SOUND)
                .forEach(str -> assetManager.load(str, Sound.class));
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
                        AssetsNames.BIKER_DASH_SHEET,
                        AssetsNames.ENTRY_OBSTACLE_CLOSED,
                        AssetsNames.ENTRY_OBSTACLE_CLOSING,
                        AssetsNames.ENTRY_OBSTACLE_OPENING,
                        AssetsNames.HAMMER_OBSTACLE_CLOSED,
                        AssetsNames.HAMMER_OBSTACLE_CLOSING,
                        AssetsNames.HAMMER_OBSTACLE_OPENING,
                        AssetsNames.STONE_6,
                        AssetsNames.FENCE_6,
                        AssetsNames.MONSTER_ATTACK1_SHEET,
                        AssetsNames.MONSTER_ATTACK2_SHEET,
                        AssetsNames.MONSTER_ATTACK3_SHEET,
                        AssetsNames.MONSTER_DEATH_SHEET,
                        AssetsNames.MONSTER_HURT_SHEET,
                        AssetsNames.MONSTER_WALK_SHEET,
                        AssetsNames.MONSTER_IDLE_SHEET,
                        AssetsNames.MONSTER2_IDLE_SHEET,
                        AssetsNames.MONSTER2_ATTACK1_SHEET,
                        AssetsNames.MONSTER2_ATTACK2_SHEET,
                        AssetsNames.MONSTER2_ATTACK3_SHEET,
                        AssetsNames.MONSTER2_DEATH_SHEET,
                        AssetsNames.MONSTER2_HURT_SHEET,
                        AssetsNames.MONSTER2_WALK_SHEET,
                        AssetsNames.MONSTER3_IDLE_SHEET,
                        AssetsNames.MONSTER3_ATTACK1_SHEET,
                        AssetsNames.MONSTER3_ATTACK2_SHEET,
                        AssetsNames.MONSTER3_ATTACK3_SHEET,
                        AssetsNames.MONSTER3_DEATH_SHEET,
                        AssetsNames.MONSTER3_HURT_SHEET,
                        AssetsNames.MONSTER3_WALK_SHEET,
                        AssetsNames.PORTAL_FIRST_INACTIVE_SPRITESHEET,
                        AssetsNames.PORTAL_SECOND_INACTIVE_SPRITESHEET,
                        AssetsNames.PORTAL_THIRD_INACTIVE_SPRITESHEET,
                        AssetsNames.PORTAL_FIRST_ACTIVATING_SPRITESHEET,
                        AssetsNames.PORTAL_SECOND_ACTIVATING_SPRITESHEET,
                        AssetsNames.PORTAL_THIRD_ACTIVATING_SPRITESHEET,
                        AssetsNames.GREENZONE_PARALLAX_1,
                        AssetsNames.GREENZONE_PARALLAX_2,
                        AssetsNames.GREENZONE_PARALLAX_3,
                        AssetsNames.GREENZONE_PARALLAX_4,
                        AssetsNames.GREENZONE_PARALLAX_5,
                        AssetsNames.INDUSTRIALZONE_PARALLAX_1,
                        AssetsNames.INDUSTRIALZONE_PARALLAX_3,
                        AssetsNames.INDUSTRIALZONE_PARALLAX_4,
                        AssetsNames.INDUSTRIALZONE_PARALLAX_5,
                        AssetsNames.POWERSTATION_PARALLAX_1,
                        AssetsNames.POWERSTATION_PARALLAX_2,
                        AssetsNames.POWERSTATION_PARALLAX_3,
                        AssetsNames.POWERSTATION_PARALLAX_4,
                        AssetsNames.POWERSTATION_PARALLAX_5)
                .forEach(str -> assetManager.load(str, Texture.class));
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        Stream.of(AssetsNames.GREENZONE_LEVEL_TILEMAP,
                        AssetsNames.POWERSTATION_LEVEL_TILEMAP,
                        AssetsNames.INDUSTRIALZONE_LEVEL_TILEMAP,
                        AssetsNames.SAFEZONE_LEVEL_TILEMAP)
                .forEach(str -> assetManager.load(str, TiledMap.class));
        assetManager.setLoader(ParticleEffect.class, new ParticleEffectLoader(new InternalFileHandleResolver()));
        assetManager.load(AssetsNames.SOUL_PARTICLES, ParticleEffect.class);
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

    private void changeLevel(Levels level) {
        getScreen().dispose();
        inputMultiplexer.clear();
        PlayerDataManager.getInstance().setCurrentLevel(level);
        currentLevel = level.create();
        setScreen(currentLevel);
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
        getScreen().dispose();
        assetManager.dispose();
        batch.dispose();
    }

    @Override
    public void setScreen(Screen screen) {
        screenId++;
        super.setScreen(screen);
    }

    public int getScreenId() {
        return screenId;
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

    @FunctionalInterface
    private interface LevelChangeDelegate {
        void changeLevel();
    }
}
