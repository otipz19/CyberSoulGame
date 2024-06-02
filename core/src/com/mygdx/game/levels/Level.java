package com.mygdx.game.levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Hero;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.LevelCamera;

public abstract class Level implements Screen {
    public MyGdxGame game;
    public Hero hero;
    public Enemy enemy;
    public World world;

    protected TiledMap map;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected LevelCamera camera;
    protected ScreenViewport viewport;

    protected float accumulator;
    protected static final float TIME_STEP = 1 / 60f;
    protected static final int VELOCITY_ITERATIONS = 6;
    protected static final int POSITION_ITERATIONS = 2;
    protected Box2DDebugRenderer box2dRenderer;

    protected int levelWidth;
    protected int levelHeight;
    protected float unitScale;

    public Level(){
        box2dRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
