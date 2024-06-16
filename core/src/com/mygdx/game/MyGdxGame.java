/**
 * Main class representing the game application.
 */
package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.heroes.BikerHero;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.heroes.PunkHero;
import com.mygdx.game.levels.*;
import com.mygdx.game.ui.MainMenu;
import com.mygdx.game.ui.WinScreen;
import com.mygdx.game.utils.MyAssetManager;
import com.mygdx.game.utils.PlayerDataManager;
import com.mygdx.game.utils.PlayerPreferencesManager;

/**
 * The main class of the game, extending from the LibGDX Game class.
 * Manages game initialization, asset loading, level management, screen transitions,
 * player preferences, and game state.
 */
public class MyGdxGame extends Game {
    public static final boolean IS_DEV_MODE = false;
    public static final boolean IS_DEBUG_MODE = true;

    private static MyGdxGame instance;
    private InputMultiplexer inputMultiplexer;
    private Level currentLevel;
    public MyAssetManager assetManager;
    public SpriteBatch batch;
    private LevelChangeDelegate levelChangeDelegate;
    private int screenId;

    /**
     * Retrieves the singleton instance of MyGdxGame.
     *
     * @return The singleton instance of MyGdxGame.
     */
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

    /**
     * Transition to the main menu screen.
     */
    public void goToMainMenu() {
        levelChangeDelegate = this::showMainMenu;
    }

    private void showMainMenu() {
        if (getScreen() != null)
            getScreen().dispose();
        inputMultiplexer.clear();
        currentLevel = null;
        boolean hasAlreadyPlayed = PlayerDataManager.getInstance().getHero() != Heroes.NOT_SELECTED;
        setScreen(new MainMenu(hasAlreadyPlayed));
    }

    /**
     * Triggered when the game is completed, transition to the win screen.
     */
    public void gameCompleted() {
        PlayerDataManager.getInstance().setCurrentLevel(Levels.SAFE);
        levelChangeDelegate = this::showWinScreen;
    }

    private void showWinScreen() {
        if (getScreen() != null)
            getScreen().dispose();
        inputMultiplexer.clear();
        currentLevel = null;
        setScreen(new WinScreen());
    }

    /**
     * Transition to a new game level.
     *
     * @param level The level to transition to.
     */
    public void goToNewLevel(Levels level) {
        levelChangeDelegate = () -> changeLevel(level);
    }

    /**
     * Triggered when the player fails a level, transition back to the main menu.
     */
    public void levelFailed() {
        levelChangeDelegate = this::showMainMenu;
    }

    /**
     * Restart the current level.
     */
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

    /**
     * Adjust display mode based on player preferences (fullscreen or windowed).
     */
    public void changeDisplayMode() {
        if (PlayerPreferencesManager.getInstance().isFullScreen()) {
            Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
            Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
            Gdx.graphics.setFullscreenMode(displayMode);
        } else {
            Gdx.graphics.setWindowedMode(960, 640);
        }
    }

    /**
     * Exit the game.
     */
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

    /**
     * Retrieves the current screen ID.
     *
     * @return The current screen ID.
     */
    public int getScreenId() {
        return screenId;
    }

    /**
     * Enum representing different game levels.
     */
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

    /**
     * Enum representing different hero types in the game.
     */
    public enum Heroes {
        NOT_SELECTED {
            @Override
            public Hero create(Level level, float x, float y, float width, float height) {
                throw new RuntimeException("Cannot create a hero without player selection");
            }
        },
        BIKER {
            @Override
            public Hero create(Level level, float x, float y, float width, float height) {
                return new BikerHero(level, PlayerDataManager.getInstance().getHeroData(), x, y, width, height);
            }
        },
        PUNK {
            @Override
            public Hero create(Level level, float x, float y, float width, float height) {
                return new PunkHero(level, PlayerDataManager.getInstance().getHeroData(), x, y, width, height);
            }
        };

        /**
         * Abstract method to create a hero instance.
         *
         * @param level  The level where the hero will be created.
         * @param x      The x-coordinate of the hero's initial position.
         * @param y      The y-coordinate of the hero's initial position.
         * @param width  The width of the hero.
         * @param height The height of the hero.
         * @return The created Hero instance.
         */
        public abstract Hero create(Level level, float x, float y, float width, float height);
    }
}
