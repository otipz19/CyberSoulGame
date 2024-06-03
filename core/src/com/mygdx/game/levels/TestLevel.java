package com.mygdx.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.camera.LevelCamera;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Hero;
import com.mygdx.game.entities.HeroData;
import com.mygdx.game.entities.EnemyData;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.physics.ContactListener;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.LevelObjectsParser;

public class TestLevel extends Level {
    private Texture background;

    public TestLevel(MyGdxGame game){
        this.game = game;
        createMap();
        hero = new Hero(this, new HeroData(), 17, 5, 1, 1);
        enemy = new Enemy(this, new EnemyData(),6,5.5f,1,1,4,9,hero);
        createCamera();
        background = game.assetManager.get(AssetsNames.GREENZONE_BACKGROUND_FULL);
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

    private void createCamera() {
        camera = new LevelCamera(levelWidth, levelHeight);
        camera.adjustZoomForViewportSize(levelWidth/2f, levelHeight/2f);

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
        enemy.render();
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
