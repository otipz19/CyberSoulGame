package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.CoordinatesProjector;
import com.mygdx.game.utils.LevelObjectsParser;

import java.util.stream.Stream;

public class MyGdxGame implements ApplicationListener {
    private static MyGdxGame instance;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private ScreenViewport viewport;

    public AssetManager assetManager;
    private SpriteBatch batch;
    private SimpleActor simpleActor;

    private Texture debugTexture;

    public World world;
    private float accumulator;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private Box2DDebugRenderer box2dRenderer;

    public static MyGdxGame getInstance() {
        return instance;
    }

    @Override
    public void create() {
        instance = this;

        Box2D.init();
        batch = new SpriteBatch();

        loadAssets();
        createCamera();
        createMap();

        simpleActor = new SimpleActor(this, 17, 5);

        debugTexture = assetManager.get(AssetsNames.GREENZONE_BACKGROUND_FULL);
        box2dRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(AssetsNames.TEST_LEVEL_TILEMAP, TiledMap.class);
        assetManager.load(AssetsNames.GREENZONE_BACKGROUND_FULL, Texture.class);
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
                        AssetsNames.BIKER_RUN_ATTACK_SHEET)
                .forEach(str -> assetManager.load(str, Texture.class));
        assetManager.finishLoading();
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.setWorldWidth(30);
        viewport.setWorldHeight(20);
        camera.update();
    }

    private void createMap() {
        world = new World(new Vector2(0, -10), true);

        map = assetManager.get(AssetsNames.TEST_LEVEL_TILEMAP);
        MapProperties mapProperties = map.getProperties();
        int tileSize = (int) mapProperties.get("tileheight");
        int heightInTiles = (int) mapProperties.get("height");
        float unitScale = 1f / tileSize;
        float mapHeight = tileSize * heightInTiles;

        viewport.setUnitsPerPixel(unitScale);

        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        mapRenderer.setView(camera);

        LevelObjectsParser parser = new LevelObjectsParser(AssetsNames.TEST_LEVEL_TILEMAP, "colliders");
        CoordinatesProjector coordinatesProjector = new CoordinatesProjector(unitScale, mapHeight);
        for (Shape2D shape : parser.getShapes()) {
            Collider collider;
            if (shape instanceof Rectangle)
                collider = ColliderCreator.create((Rectangle) shape, coordinatesProjector);
            else if (shape instanceof Polygon)
                collider = ColliderCreator.create((Polygon) shape, coordinatesProjector);
            else
                throw new RuntimeException("Shape is not supported");

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(collider.getX(), collider.getY());

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = collider.getShape();
            fixtureDef.friction = 1;
            fixtureDef.density = 1;
            fixtureDef.restitution = 0;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);

            collider.dispose();
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(debugTexture, 0, 0, 30, 20);
        batch.end();
        mapRenderer.render();
        batch.begin();
        simpleActor.render();
        batch.end();
        box2dRenderer.render(world, camera.combined);

        camera.position.set(simpleActor.getCameraPosition());
        camera.update();
        mapRenderer.setView(camera);

        accumulator += Gdx.graphics.getDeltaTime();
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public TiledMap getMap() {
        return map;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
