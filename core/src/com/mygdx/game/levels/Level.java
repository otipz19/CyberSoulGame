package com.mygdx.game.levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.camera.LevelCamera;
import com.mygdx.game.entities.IRenderable;
import com.mygdx.game.entities.Surface;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.obstacles.EntryObstacle;
import com.mygdx.game.entities.obstacles.GateObstacle;
import com.mygdx.game.entities.obstacles.HammerObstacle;
import com.mygdx.game.entities.particles.Particles;
import com.mygdx.game.entities.portals.FirstPortal;
import com.mygdx.game.entities.portals.Portal;
import com.mygdx.game.entities.portals.SecondPortal;
import com.mygdx.game.entities.portals.ThirdPortal;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.map.EnemyData;
import com.mygdx.game.map.XMLLevelObjectsParser;
import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.physics.ContactListener;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.ui.LevelUI;
import com.mygdx.game.utils.DelayedAction;
import com.mygdx.game.utils.PlayerDataManager;

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
    protected XMLLevelObjectsParser objectsParser;
    protected int levelWidth;
    protected int levelHeight;
    protected float unitScale;

    public Hero hero;
    protected final Array<GateObstacle> obstacles = new Array<>();
    protected final Array<Enemy> enemies = new Array<>();
    protected final Array<Particles> particles = new Array<>();
    protected final Array<Projectile> projectiles = new Array<>();
    protected final Array<Portal> portals = new Array<>();

    public LevelUI ui;
    public SoundPlayer soundPlayer;
    private boolean isPaused;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean value) {
        isPaused = value;
        if (isPaused)
            soundPlayer.pauseSounds();
        else
            soundPlayer.unpauseSounds();
    }

    public void togglePause() {
        setPaused(!isPaused);
    }

    public Level(String tileMapName) {
        this.game = MyGdxGame.getInstance();
        this.tileMapName = tileMapName;
        initResources();
        createMap();
        createCamera();
        createHero();
        createEntities();
        parallaxBackground = createBackground();
        createMusicSound();
        createUI();
        if (MyGdxGame.IS_DEV_MODE) {
            box2dRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
        }
    }

    protected void initResources() {
        objectsParser = new XMLLevelObjectsParser(tileMapName);
    }

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

        objectsParser.getColliders().forEach(shape -> {
            Collider collider;
            if (shape instanceof Rectangle)
                collider = ColliderCreator.create((Rectangle) shape, coordinatesProjector);
            else if (shape instanceof Polygon)
                collider = ColliderCreator.create((Polygon) shape, coordinatesProjector);
            else
                throw new RuntimeException("Shape is not supported");

            new Surface(this, collider);
        });
    }

    protected void createCamera() {
        camera = new LevelCamera(levelWidth, levelHeight);
        camera.zoom = 0.5f;

        viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(unitScale);

        camera.update();
    }

    protected void createHero() {
        Vector2 spawn = coordinatesProjector.unproject(getPlayerSpawn());
        hero = new Hero(this, PlayerDataManager.getInstance().getHeroData(), spawn.x, spawn.y, 0.95f, 0.95f);
        camera.setPositionSharply(hero.getCameraPosition());
    }

    protected Vector2 getPlayerSpawn() {
        Rectangle bounds = objectsParser.getPlayerSpawns()
                .findFirst().orElseThrow().getBounds();
        return new Vector2(bounds.x, bounds.y + bounds.height);
    }

    protected void createEntities() {
        createObstacles();
        createEnemies();
        createPortals();
    }

    private void createObstacles() {
        objectsParser.getObstaclesData().forEach(obstacleData -> {
            var collider = ColliderCreator.create(obstacleData.getBounds(), coordinatesProjector);
            GateObstacle obstacle = switch (obstacleData.getType()) {
                case ENTRY -> new EntryObstacle(this, collider, obstacleData, coordinatesProjector);
                case HAMMER -> new HammerObstacle(this, collider, obstacleData, coordinatesProjector);
                default -> throw new RuntimeException("Not supported obstacle type!");
            };
            obstacles.add(obstacle);
        });
    }

    private void createEnemies() {
        // Should be changed
        Enemy enemy = new Enemy(this, new EnemyData(new Rectangle(20, 32, 0, 0), new Rectangle(18, 32, 10, 1), "DEFAULT"), 1, 1);
        enemies.add(enemy);
        enemy.addOnDeathAction(() -> new DelayedAction(enemy.getDeathDelay(), () -> enemies.removeValue(enemy, true)));
    }

    private void createPortals() {
        objectsParser.getPortalsData().forEach(portalData -> {
            Portal portal;
            switch (portalData.getType()) {
                case FIRST -> portal = new FirstPortal(this, portalData, coordinatesProjector);
                case SECOND -> portal = new SecondPortal(this, portalData, coordinatesProjector);
                case THIRD -> portal = new ThirdPortal(this, portalData, coordinatesProjector);
                default -> throw new RuntimeException("Not supported portal type!");
            }
            portals.add(portal);
        });
    }

    protected abstract ParallaxBackground createBackground();

    protected void createMusicSound() {
        soundPlayer = SoundPlayer.getInstance();
        soundPlayer.unpauseAll();
    }

    protected void createUI() {
        ui = new LevelUI(this);
    }

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

    protected void updateCamera(float delta) {
        if (delta != 0)
            camera.setPositionSmoothly(hero.getCameraPosition());
        camera.update();
    }

    protected void renderBackground(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        parallaxBackground.render();
        game.batch.end();
    }

    protected void renderMap(float delta) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    protected void renderEntities(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        renderEntities(delta, portals);
        renderEntities(delta, enemies);
        renderEntities(delta, projectiles);
        renderEntities(delta, particles);
        hero.render(delta);
        renderEntities(delta, obstacles);
        game.batch.end();
    }

    protected <T extends IRenderable> void renderEntities(float delta, Array<T> entities) {
        for (T entity : entities) {
            entity.render(delta);
        }
    }

    protected void updateMusicSound() {
        soundPlayer.update();
    }

    protected void renderUI(float delta) {
        HeroResourcesManager resourcesManager = hero.getResourcesManager();
        ui.updateStatistics(resourcesManager.getHealthPercent(), resourcesManager.getShieldPercent(), resourcesManager.getEnergyPercent(), resourcesManager.getSouls());
        ui.act(delta);
        ui.draw();
    }

    protected void doPhysicsStep(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    public void addParticleEffect(Particles particleEffect) {
        particles.add(particleEffect);
        particleEffect.addOnCompleteAction(() -> new DelayedAction(particleEffect.getDestructionDelay(), () -> particles.removeValue(particleEffect, true)));
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
        projectile.addOnExplosionAction(() -> new DelayedAction(projectile.getDestructionDelay(), () -> projectiles.removeValue(projectile, true)));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        ui.getViewport().update(width, height, true);
        parallaxBackground = createBackground();
    }

    @Override
    public void dispose() {
        world.dispose();
        hero.dispose();
        ui.dispose();
        soundPlayer.clearAll();
        mapRenderer.dispose();
        if (MyGdxGame.IS_DEV_MODE) {
            box2dRenderer.dispose();
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}