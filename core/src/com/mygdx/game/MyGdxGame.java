package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.utils.AssetNames;

public class MyGdxGame implements ApplicationListener {
    private static MyGdxGame instance;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

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

    public static MyGdxGame getInstance(){
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

        debugTexture = assetManager.get(AssetNames.GREENZONE_BACKGROUND_FULL);
        box2dRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(AssetNames.TEST_LEVEL_TILEMAP, TiledMap.class);
        assetManager.load(AssetNames.GREENZONE_BACKGROUND_FULL, Texture.class);
        assetManager.load(AssetNames.BIKER_RUN_SHEET, Texture.class);
        assetManager.load(AssetNames.BIKER_JUMP_SHEET, Texture.class);
        assetManager.load(AssetNames.BIKER_IDLE_SHEET, Texture.class);
        assetManager.finishLoading();
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        camera.update();
    }

    private void createMap() {
        world = new World(new Vector2(0, -10), true);

        map = assetManager.get(AssetNames.TEST_LEVEL_TILEMAP);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);
        mapRenderer.setView(camera);

        LevelObjectsParser parser = new LevelObjectsParser(AssetNames.TEST_LEVEL_TILEMAP, "colliders");
        for (Shape2D shape : parser.getShapes()) {
            Collider collider;
            if (shape instanceof Rectangle)
                collider = ColliderCreator.create((Rectangle)shape, camera::unproject);
            else if (shape instanceof Polygon)
                collider = ColliderCreator.create((Polygon)shape, camera::unproject);
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

        camera.position.set(simpleActor.body.getPosition().x, simpleActor.body.getPosition().y, 0);
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
