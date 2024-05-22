package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.css.Rect;

public class MyGdxGame implements ApplicationListener {
    private static final String TILEMAP_FILE_NAME = "first-level.tmx";

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private AssetManager assetManager;
    private SpriteBatch batch;
    private SimpleActor simpleActor;

    private Texture debugTexture;

    public Array<Shape2D> colliders;

    public World world;
    private float accumulator;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        loadAssets();
        createMap();
        createCamera();
        createMapRenderer();
//		Vector3 pos = camera.unproject(new Vector3(400, 600, 0));
        simpleActor = new SimpleActor(this, 10, 10);
        debugTexture = assetManager.get("background-1.png");
		colliders = new Array<>();
        for (MapObject mapObject : map.getLayers().get("colliders").getObjects()) {
            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                float x = rectangle.x / 32;
                float y = rectangle.y / 32;
                float width = rectangle.width / 32;
                float height = rectangle.height / 32;
                Rectangle collider = new Rectangle(x, y, width, height);
                colliders.add(collider);
            } else if (mapObject instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) mapObject).getPolygon();
                float[] screenVertices = polygon.getTransformedVertices();
                float[] worldVertices = new float[screenVertices.length];
                for (int i = 0; i < screenVertices.length; i += 2) {
					worldVertices[i] = screenVertices[i] / 32;
					worldVertices[i + 1] = screenVertices[i + 1] / 32;
                }
				Polygon collider = new Polygon(worldVertices);
				colliders.add(collider);
            }
        }
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
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
        camera.setToOrtho(false, 15, 10);
//		camera.zoom = 0.5f;
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
//        for (MapObject mapObject : map.getLayers().get("colliders").getObjects()) {
//            if (mapObject instanceof RectangleMapObject) {
//                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
//                Vector3 worldPos = new Vector3(rectangle.getX() / 32, rectangle.getY() / 32, 0);
//                Vector3 worldSize = new Vector3(rectangle.width / 32, rectangle.height / 32, 0);
//                batch.draw(debugTexture, worldPos.x, worldPos.y, worldSize.x, worldSize.y);
//            } /*else if (mapObject instanceof PolygonMapObject){
////				Polygon polygon = ((PolygonMapObject)mapObject).getPolygon();
////				Rectangle bounds = polygon.getBoundingRectangle();
////				batch.draw(debugTexture, bounds.x, bounds.y, bounds.width, bounds.height);
////			}*/
//        }
        batch.end();
		camera.position.set(simpleActor.bounds.x, simpleActor.bounds.y, 0);
        mapRenderer.setView(camera);
		camera.update();

        accumulator += Gdx.graphics.getDeltaTime();
        while(accumulator >= TIME_STEP){
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
