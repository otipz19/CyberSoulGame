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
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		loadAssets();
		createMap();
		createCamera();
		createMapRenderer();
//		Vector3 pos = camera.unproject(new Vector3(400, 600, 0));
		simpleActor = new SimpleActor(this, 10, 10);
		debugTexture = assetManager.get("background-1.png");
	}

	private void loadAssets(){
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
	}

	private void createMapRenderer() {
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);
		mapRenderer.setView(camera);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.WHITE);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(debugTexture, 0, 0, 30, 20);
		batch.end();
		mapRenderer.render();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		simpleActor.render();
//		for(MapObject mapObject: map.getLayers().get("colliders").getObjects()){
//			if(mapObject instanceof RectangleMapObject){
//				Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
//				batch.draw(debugTexture, rectangle.getX(), rectangle.getY(), rectangle.width, rectangle.height);
//			} /*else if (mapObject instanceof PolygonMapObject){
////				Polygon polygon = ((PolygonMapObject)mapObject).getPolygon();
////				Rectangle bounds = polygon.getBoundingRectangle();
////				batch.draw(debugTexture, bounds.x, bounds.y, bounds.width, bounds.height);
////			}*/
//		}
		batch.end();
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
	public void dispose () {
		assetManager.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}

	public AssetManager getAssetManager(){
		return assetManager;
	}

	public TiledMap getMap(){
		return map;
	}

	public OrthographicCamera getCamera(){
		return camera;
	}
}
