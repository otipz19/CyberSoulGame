package com.mygdx.game.levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.LevelCamera;
import com.mygdx.game.ui.LevelUI;

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

    public Level(){
        this.game = MyGdxGame.getInstance();
        initResources();
        createMap();
        createCamera();
        createHero();
        createEntities();
        createBackground();
        createUI();
        box2dRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }

    protected abstract void initResources();
    protected abstract void createMap();
    protected abstract void createCamera();
    protected abstract void createHero();
    protected abstract void createEntities();
    protected abstract void createBackground();
    protected abstract void createUI();

    @Override
    public final void render(float delta) {
        if (isPaused)
            delta = 0;
        ScreenUtils.clear(Color.WHITE);
        updateCamera(delta);
        renderBackground(delta);
        renderMap(delta);
        renderEntities(delta);
        box2dRenderer.render(world, camera.combined);
        renderUI(delta);
        doPhysicsStep(delta);
    }

    protected abstract void updateCamera(float delta);
    protected abstract void renderBackground(float delta);
    protected abstract void renderMap(float delta);
    protected abstract void renderEntities(float delta);
    protected abstract void renderUI(float delta);
    protected abstract void doPhysicsStep(float delta);

    public boolean isPaused(){
        return isPaused;
    }

    public void setPaused(boolean value){
        isPaused = value;
    }

    public void togglePause(){
        isPaused = !isPaused;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        box2dRenderer.dispose();
    }

}
