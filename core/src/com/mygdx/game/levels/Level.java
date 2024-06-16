/**
 * Abstract class representing a game level.
 * Implements the Screen interface for rendering and lifecycle management.
 */
package com.mygdx.game.levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.camera.LevelCamera;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.particles.Particles;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.map.MapObjectsBinder;
import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.physics.ContactListener;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.ui.LevelUI;
import com.mygdx.game.utils.DelayedAction;
import com.mygdx.game.utils.PlayerDataManager;
import com.mygdx.game.utils.RenderUtils;

/**
 * Abstract class representing a game level with physics and entities.
 */
public abstract class Level implements Screen {

    protected MyGdxGame game;
    protected LevelCamera camera;
    protected ScreenViewport viewport;
    protected CoordinatesProjector coordinatesProjector;

    public World world;
    private ParallaxBackground parallaxBackground;
    protected float accumulator;
    protected static final float TIME_STEP = 1 / 60f;
    protected static final int VELOCITY_ITERATIONS = 6;
    protected static final int POSITION_ITERATIONS = 2;
    protected Box2DDebugRenderer box2dRenderer;

    private final String tileMapName;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected int levelWidth;
    protected int levelHeight;
    protected float unitScale;

    public Hero hero;
    MapObjectsBinder mapBinder;

    private final Array<Particles> particles = new Array<>();
    private final Array<Projectile> projectiles = new Array<>();


    public LevelUI ui;
    public SoundPlayer soundPlayer;
    private boolean isPaused;

    /**
     * Getter for the pause state of the level.
     *
     * @return true if the level is currently paused, false otherwise.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Setter for the pause state of the level.
     *
     * @param value true to pause the level, false to resume.
     */
    public void setPaused(boolean value) {
        isPaused = value;
        if (isPaused)
            soundPlayer.pauseSounds();
        else
            soundPlayer.unpauseSounds();
    }

    /**
     * Toggles the pause state of the level.
     */
    public void togglePause() {
        setPaused(!isPaused);
    }

    /**
     * Constructor for creating a new level instance.
     *
     * @param tileMapName Name of the Tiled map file to load.
     */
    public Level(String tileMapName) {
        this.game = MyGdxGame.getInstance();
        this.tileMapName = tileMapName;
        initMapBinder();
        createMap();
        createCamera();
        createHero();
        mapBinder.createEntities();
        parallaxBackground = createBackground();
        createMusicSound();
        createUI();
        if (MyGdxGame.IS_DEV_MODE) {
            box2dRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
        }
    }

    /**
     * Initializes the map binder to bind entities and objects to the map.
     */
    protected void initMapBinder() {
        mapBinder = new MapObjectsBinder(tileMapName, this);
    }

    /**
     * Creates the Tiled map and sets up the Box2D world for physics simulation.
     */
    protected void createMap() {
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactListener());

        map = game.assetManager.get(tileMapName);

        MapProperties mapProperties = map.getProperties();
        levelWidth = (int) mapProperties.get("width");
        levelHeight = (int) mapProperties.get("height");
        int tileSize = (int) mapProperties.get("tileheight");
        unitScale = 1f / tileSize;
        float mapHeight = tileSize * levelHeight;

        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        coordinatesProjector = new CoordinatesProjector(unitScale, mapHeight);

        mapBinder.createColliders();
    }

    /**
     * Creates the camera used for rendering the level.
     */
    protected void createCamera() {
        camera = new LevelCamera(levelWidth, levelHeight);
        camera.zoom = 0.5f;

        viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(unitScale);

        camera.update();
    }

    /**
     * Creates the player's hero character and initializes its position.
     */
    protected void createHero() {
        Vector2 spawn = getPlayerSpawnInWorldCoordinates();
        hero = PlayerDataManager.getInstance().getHero().create(this, spawn.x, spawn.y, 0.95f, 0.95f);
        camera.setPositionSharply(hero.getCenter());
    }

    /**
     * Retrieves the spawn coordinates for the player from the map.
     *
     * @return Spawn coordinates for the player in world units.
     */
    protected Vector2 getPlayerSpawnInWorldCoordinates() {
        Rectangle bounds = mapBinder.getPlayerSpawns()
                .findFirst().orElseThrow().getBounds();
        return coordinatesProjector.unproject(bounds.x, bounds.y + bounds.height);
    }

    /**
     * Abstract method to be implemented by subclasses for creating the parallax background.
     *
     * @return Parallax background specific to the level.
     */
    protected abstract ParallaxBackground createBackground();

    /**
     * Initializes the sound player and starts playing background music.
     */
    protected void createMusicSound() {
        soundPlayer = SoundPlayer.getInstance();
        soundPlayer.unpauseAll();
    }

    /**
     * Creates the user interface for the level, including UI elements and event handling.
     */
    protected void createUI() {
        ui = new LevelUI(this);
        hero.addOnDeathAction(() -> {
            ui.blockPausing();
            new DelayedAction(hero.getDeathDelay(), ui::showDeathUI);
        });
    }

    /**
     * Renders the level including background, map, entities, and UI.
     *
     * @param delta Time elapsed since the last frame in seconds.
     */
    public final void render(float delta) {
        if (isPaused)
            delta = 0;
        ScreenUtils.clear(Color.WHITE);
        updateCamera(delta);
        renderBackground(delta);
        renderMap(delta);
        renderEntities(delta);
        if (MyGdxGame.IS_DEV_MODE) {
            box2dRenderer.render(world, camera.combined);
        }
        updateMusicSound();
        renderUI(delta);
        doPhysicsStep(delta);
    }

    /**
     * Updates the camera position based on the player's movement.
     *
     * @param delta Time elapsed since the last frame in seconds.
     */
    protected void updateCamera(float delta) {
        if (delta != 0)
            camera.setPositionSmoothly(hero.getCenter());
        camera.update();
    }

    /**
     * Renders the parallax background of the level.
     *
     * @param delta Time elapsed since the last frame in seconds.
     */
    protected void renderBackground(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        parallaxBackground.render();
        game.batch.end();
    }

/**
 * Renders the Tiled map of the level.
 *
 * @param
 * @param delta Time elapsed since the last frame in seconds.
 */
protected void renderMap(float delta) {
    mapRenderer.setView(camera);
    mapRenderer.render();
}

    /**
     * Renders all entities in the level including portals, enemies, projectiles, particles, and obstacles.
     *
     * @param delta Time elapsed since the last frame in seconds.
     */
    protected void renderEntities(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        mapBinder.renderPortals(delta);
        mapBinder.renderEnemies(delta);
        RenderUtils.renderEntities(delta, projectiles);
        RenderUtils.renderEntities(delta, particles);
        mapBinder.renderAllNpc(delta);
        hero.render(delta);
        mapBinder.renderObstacles(delta);
        game.batch.end();
    }

    /**
     * Updates the state of the background music and sound effects.
     */
    protected void updateMusicSound() {
        soundPlayer.update();
    }

    /**
     * Renders the user interface of the level, including health, shield, energy, and other statistics.
     *
     * @param delta Time elapsed since the last frame in seconds.
     */
    protected void renderUI(float delta) {
        HeroResourcesManager resourcesManager = hero.getResourcesManager();
        ui.updateStatistics(
                resourcesManager.getHealthPercent(),
                resourcesManager.getShieldPercent(),
                resourcesManager.getEnergyPercent(),
                resourcesManager.getSouls());
        ui.act(delta);
        ui.draw();
    }

    /**
     * Performs a physics step for the Box2D world simulation.
     *
     * @param delta Time elapsed since the last frame in seconds.
     */
    protected void doPhysicsStep(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    /**
     * Adds a particle effect to the level.
     *
     * @param particleEffect Particle effect to add.
     */
    public void addParticleEffect(Particles particleEffect) {
        particles.add(particleEffect);
        particleEffect.addOnCompleteAction(() -> new DelayedAction(particleEffect.getDestructionDelay(), () -> particles.removeValue(particleEffect, true)));
    }

    /**
     * Adds a projectile to the level.
     *
     * @param projectile Projectile to add.
     */
    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
        projectile.addOnExplosionAction(() -> new DelayedAction(projectile.getDestructionDelay(), () -> projectiles.removeValue(projectile, true)));
    }

    /**
     * Handles screen resizing events by updating the viewport and background.
     *
     * @param width  New width of the screen.
     * @param height New height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        ui.getViewport().update(width, height, true);
        parallaxBackground = createBackground();
    }

    /**
     * Disposes of resources used by the level, including the Box2D world, hero, UI, sound player, map renderer, and debug renderer if in developer mode.
     */
    @Override
    public void dispose() {
        world.dispose();
        if (hero instanceof Disposable disposableHero)
            disposableHero.dispose();
        ui.dispose();
        soundPlayer.clearAll();
        mapRenderer.dispose();
        if (MyGdxGame.IS_DEV_MODE) {
            box2dRenderer.dispose();
        }
    }

    /**
     * Lifecycle method called when the screen is shown.
     * Currently not implemented.
     */
    @Override
    public void show() {
    }

    /**
     * Lifecycle method called when the game is paused.
     * Currently not implemented.
     */
    @Override
    public void pause() {
    }

    /**
     * Lifecycle method called when the game is resumed from pause.
     * Currently not implemented.
     */
    @Override
    public void resume() {
    }

    /**
     * Lifecycle method called when the screen is hidden.
     * Currently not implemented.
     */
    @Override
    public void hide() {
    }

    /**
     * Retrieves the coordinates projector used for converting coordinates between different systems.
     *
     * @return Coordinates projector instance.
     */
    public CoordinatesProjector getCoordinatesProjector() {
        return coordinatesProjector;
    }

    /**
     * Retrieves the hero character of the level.
     *
     * @return Hero character instance.
     */
    public Hero getHero() {
        return hero;
    }
}
