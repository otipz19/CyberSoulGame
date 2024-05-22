package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame implements ApplicationListener {
    private static final String TILEMAP_FILE_NAME = "first-level.tmx";

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private AssetManager assetManager;
    private SpriteBatch batch;
    private SimpleActor simpleActor;

    private Texture debugTexture;

    public World world;
    private float accumulator;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, -10), true);

        batch = new SpriteBatch();
        loadAssets();
        createMap();
        createCamera();
        createMapRenderer();
        simpleActor = new SimpleActor(this, 10, 10);
        debugTexture = assetManager.get("background-1.png");

        for (MapObject mapObject : map.getLayers().get("colliders").getObjects()) {
            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                BodyDef bodyDef = new BodyDef();
//                Vector3 worldPos = camera.unproject(new Vector3(rectangle.x, rectangle.y, 0));
                Vector2 worldPos = new Vector2(rectangle.x / 32, rectangle.y / 32);
                Vector2 worldSize = new Vector2(rectangle.width / 32, rectangle.height / 32);
                bodyDef.position.set(worldPos.x + worldSize.x / 2, worldPos.y - worldSize.y / 2);

                Body body = world.createBody(bodyDef);

                PolygonShape rectanglePolygonShape = new PolygonShape();
                rectanglePolygonShape.setAsBox(worldSize.x / 2, worldSize.y / 2);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = rectanglePolygonShape;
                fixtureDef.friction = 1;
                fixtureDef.density = 1;
                fixtureDef.restitution = 0;

                body.createFixture(fixtureDef);
                rectanglePolygonShape.dispose();

                body.setUserData(new TextureData(debugTexture, worldSize.x, worldSize.y));
            } /*else if (mapObject instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) mapObject).getPolygon();
            }*/
        }
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(TILEMAP_FILE_NAME, TiledMap.class);
        assetManager.load("hero.png", Texture.class);
        assetManager.load("background-1.png", Texture.class);
        assetManager.finishLoading();
    }

    private void createMap() {
        map = assetManager.get(TILEMAP_FILE_NAME);
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        camera.update();
    }

    private void createMapRenderer() {
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);
        mapRenderer.setView(camera);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(debugTexture, 0, 0, 30, 20);
        batch.end();
        mapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        simpleActor.render();

//        Array<Body> bodies = new Array<>();
//        world.getBodies(bodies);
//        for (Body body : bodies) {
//            if (body != null && body.getUserData() != null && body.getUserData() instanceof TextureData) {
//                TextureData textureData = (TextureData) body.getUserData();
//                batch.draw(textureData.texture, body.getPosition().x - textureData.width / 2,
//                        body.getPosition().y + textureData.height / 2, textureData.width, textureData.height);
//            }
//        }

        batch.end();
        camera.position.set(simpleActor.body.getPosition().x, simpleActor.body.getPosition().y, 0);
        mapRenderer.setView(camera);
        camera.update();

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
