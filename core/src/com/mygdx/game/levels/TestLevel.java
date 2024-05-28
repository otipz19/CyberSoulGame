package com.mygdx.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.camera.LevelCamera;
import com.mygdx.game.entities.Hero;
import com.mygdx.game.entities.HeroData;
import com.mygdx.game.entities.EntryObstacle;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.physics.ContactListener;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.BodyCreator;
import com.mygdx.game.utils.ObstacleData;
import com.mygdx.game.utils.XMLLevelObjectsParser;

public class TestLevel extends Level {
    private Texture background;
    private Array<EntryObstacle> obstacles = new Array<>();
    private XMLLevelObjectsParser objectsParser;

    public TestLevel(MyGdxGame game) {
        this.game = game;
        objectsParser = new XMLLevelObjectsParser(AssetsNames.TEST_LEVEL_TILEMAP);
        createMap();
        hero = new Hero(this, new HeroData(), 17, 5, 1, 1);
        createCamera();
        background = game.assetManager.get(AssetsNames.GREENZONE_BACKGROUND_FULL);
        createObstacles();
    }

    private void createObstacles() {
        objectsParser.getObstaclesData().forEach(obstacleData -> {
            if (obstacleData.getType().equals(ObstacleData.Type.ENTRY)) {
                var collider = ColliderCreator.create(obstacleData.getBounds(), coordinatesProjector);
                Body body = BodyCreator.createStaticBody(world, collider, 1, 1, 0);
                obstacles.add(new EntryObstacle(this, body));
            }
        });
    }

    private void createMap() {
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

            BodyCreator.createStaticBody(world, collider, 1, 1, 0);
        });
    }

    private void createCamera() {
        camera = new LevelCamera(levelWidth, levelHeight);
        camera.adjustZoomForViewportSize(levelWidth / 2f, levelHeight / 2f);

        viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(unitScale);

        camera.setPositionSharply(hero.getCameraPosition());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        camera.setPositionSmoothly(hero.getCameraPosition());
        camera.update();
        mapRenderer.setView(camera);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 30, 20);
        game.batch.end();
        mapRenderer.render();
        game.batch.begin();
        hero.render();
        for (var obstacle : obstacles) {
            obstacle.render();
        }
        game.batch.end();
        box2dRenderer.render(world, camera.combined);

        accumulator += Gdx.graphics.getDeltaTime();
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
