package com.mygdx.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.camera.LevelCamera;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.heroes.HeroData;
import com.mygdx.game.entities.obstacles.EntryObstacle;
import com.mygdx.game.entities.obstacles.Surface;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.physics.ContactListener;
import com.mygdx.game.ui.LevelUI;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.map.ObstacleData;
import com.mygdx.game.map.XMLLevelObjectsParser;

public class TestLevel extends Level {
    XMLLevelObjectsParser objectsParser;
    private Texture background;
    private Array<EntryObstacle> obstacles;

    public TestLevel(MyGdxGame game) {
        super(game);
    }

    @Override
    protected void initResources(){
        objectsParser = new XMLLevelObjectsParser(AssetsNames.TEST_LEVEL_TILEMAP);
        obstacles = new Array<>();
    }

    @Override
    protected void createMap(){
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactListener());

        map = game.assetManager.get(AssetsNames.TEST_LEVEL_TILEMAP);

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

    @Override
    protected void createCamera() {
        camera = new LevelCamera(levelWidth, levelHeight);
        camera.adjustZoomForViewportSize(levelWidth / 2f, levelHeight / 2f);

        viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(unitScale);
    }

    @Override
    protected void createHero() {
        HeroData heroData = new HeroData();
        heroData.health = 100;
        heroData.maxHealth = 100;
        heroData.shield = 50;
        heroData.maxShield = 50;
        heroData.souls = 0;
        heroData.shieldRestoreUnit = 2;
        hero = new Hero(this, heroData, 17, 5, 1, 1);
        camera.setPositionSharply(hero.getCameraPosition());
    }

    @Override
    protected void createEntities() {
        objectsParser.getObstaclesData().forEach(obstacleData -> {
            if (obstacleData.getType().equals(ObstacleData.Type.ENTRY)) {
                var collider = ColliderCreator.create(obstacleData.getBounds(), coordinatesProjector);
                obstacles.add(new EntryObstacle(this, collider));
                collider.dispose();
            }
        });
    }

    @Override
    protected void createBackground(){
        background = game.assetManager.get(AssetsNames.GREENZONE_BACKGROUND_FULL);
    }

    protected void createUI() {
        ui = new LevelUI(this);
    }

    @Override
    protected void updateCamera(float delta) {
        if (delta != 0)
            camera.setPositionSmoothly(hero.getCameraPosition());
        camera.update();
    }

    @Override
    protected void renderBackground(float delta)
    {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 30, 20);
        game.batch.end();
    }

    @Override
    protected void renderMap(float delta) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    protected void renderEntities(float delta)
    {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        hero.render(delta);
        for (var obstacle : obstacles) {
            obstacle.render(delta);
        }
        game.batch.end();
    }

    @Override
    protected void renderUI(float delta) {
        HeroResourcesManager resourcesManager = hero.getResourcesManager();
        ui.updateStatistics(resourcesManager.getHealthPercent(), resourcesManager.getShieldPercent(), resourcesManager.getSouls());
        ui.act(delta);
        ui.draw();
    }

    @Override
    protected void doPhysicsStep(float delta){
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
}
