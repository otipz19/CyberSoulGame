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
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.obstacles.EntryObstacle;
import com.mygdx.game.entities.obstacles.Surface;
import com.mygdx.game.entities.portals.FirstPortal;
import com.mygdx.game.entities.portals.Portal;
import com.mygdx.game.entities.portals.SecondPortal;
import com.mygdx.game.entities.portals.ThirdPortal;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.map.EnemyData;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.physics.ContactListener;
import com.mygdx.game.ui.LevelUI;
import com.mygdx.game.map.ObstacleData;
import com.mygdx.game.map.XMLLevelObjectsParser;
import com.mygdx.game.utils.PlayerDataManager;
import com.mygdx.game.entities.enemies.Enemy;

public abstract class Level implements Screen {
    public Hero hero;
    public Enemy enemy;
    public World world;
    public LevelUI ui;

    protected MyGdxGame game;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected LevelCamera camera;
    protected ScreenViewport viewport;
    protected CoordinatesProjector coordinatesProjector;

    protected float accumulator;
    protected static final float TIME_STEP = 1 / 60f;
    protected static final int VELOCITY_ITERATIONS = 6;
    protected static final int POSITION_ITERATIONS = 2;
    protected Box2DDebugRenderer box2dRenderer;

    protected int levelWidth;
    protected int levelHeight;
    protected float unitScale;

    private boolean isPaused;

    protected XMLLevelObjectsParser objectsParser;
    protected final Array<EntryObstacle> obstacles = new Array<>();
    protected final Array<Portal> portals = new Array<>();

    private final String tileMapName;

    public boolean isPaused(){
        return isPaused;
    }

    public void setPaused(boolean value){
        isPaused = value;
    }

    public void togglePause(){
        isPaused = !isPaused;
    }

    public Level(String tileMapName) {
        this.game = MyGdxGame.getInstance();
        this.tileMapName = tileMapName;
        initResources();
        createMap();
        createCamera();
        createHero();
        createEntities();
//        createBackground();
        createUI();
        box2dRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
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
        objectsParser.getObstaclesData().forEach(obstacleData -> {
            if (obstacleData.getType().equals(ObstacleData.Type.ENTRY)) {
                var collider = ColliderCreator.create(obstacleData.getBounds(), coordinatesProjector);
                obstacles.add(new EntryObstacle(this, collider, obstacleData));
                collider.dispose();
            }
        });
        enemy = new Enemy(this, new EnemyData(), 6, 60f, 1, 1, 4, 9, hero);
        objectsParser.getPortalsData().forEach(portalData -> {
            Portal portal;
            switch (portalData.getType()) {
                case FIRST -> portal = new FirstPortal(this, portalData, coordinatesProjector);
                case SECOND -> portal = new SecondPortal(this, portalData, coordinatesProjector);
                default -> portal = new ThirdPortal(this, portalData, coordinatesProjector);
            }
            portals.add(portal);
        });
    }

//    protected void createBackground() {
//        background = game.assetManager.get(AssetsNames.GREENZONE_BACKGROUND_FULL);
//    }

    protected void createUI() {
        ui = new LevelUI(this);
    }

    public final void render(float delta) {
        if (isPaused)
            delta = 0;
        ScreenUtils.clear(Color.WHITE);
        updateCamera(delta);
//        renderBackground(delta);
        renderMap(delta);
        renderEntities(delta);
        box2dRenderer.render(world, camera.combined);
        renderUI(delta);
        doPhysicsStep(delta);
    }

    protected void updateCamera(float delta) {
        if (delta != 0)
            camera.setPositionSmoothly(hero.getCameraPosition());
        camera.update();
    }

//    protected void renderBackground(float delta) {
//        game.batch.setProjectionMatrix(camera.combined);
//        game.batch.begin();
//        game.batch.draw(background, 0, 0, 90, 34);
//        game.batch.end();
//    }


    protected void renderMap(float delta) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    protected void renderEntities(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        enemy.render(delta);
        for (var obstacle : obstacles) {
            obstacle.render(delta);
        }
        for (var portal: portals) {
            portal.render(delta);
        }
        hero.render(delta);
        game.batch.end();
    }

    protected void renderUI(float delta) {
        HeroResourcesManager resourcesManager = hero.getResourcesManager();
        ui.updateStatistics(resourcesManager.getHealthPercent(), resourcesManager.getShieldPercent(), 1, resourcesManager.getSouls());
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        ui.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        world.dispose();
        hero.dispose();
        ui.dispose();
        mapRenderer.dispose();
        box2dRenderer.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}