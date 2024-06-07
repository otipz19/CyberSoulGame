package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.levels.*;
import com.mygdx.game.ui.MainMenu;
import com.mygdx.game.utils.MyAssetManager;
import com.mygdx.game.utils.PlayerDataManager;
import com.mygdx.game.utils.PlayerPreferencesManager;

public class MyGdxGame extends Game {
    public static final boolean IS_DEV_MODE = true;
    public static final boolean IS_DEBUG_MODE = true;

    private static MyGdxGame instance;
    private InputMultiplexer inputMultiplexer;
    private Level currentLevel;
    public MyAssetManager assetManager;
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
        assetManager = new MyAssetManager();
        assetManager.loadAll();
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
