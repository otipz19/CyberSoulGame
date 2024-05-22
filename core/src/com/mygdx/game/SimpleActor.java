package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class SimpleActor {
    private MyGdxGame game;
    private Texture sprite;
    public Rectangle bounds;
    private float horizontalVelocity = 10f;
    private float gravity = 10f;
    public Body body;

    public SimpleActor(MyGdxGame game, float x, float y){
        this.game = game;
        sprite = game.getAssetManager().get("hero.png");
        bounds = new Rectangle(x, y, 1, 1.5f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = game.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.75f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 1;
        fixtureDef.restitution = 0;

        Fixture fixture = body.createFixture(fixtureDef);
        
        shape.dispose();
    }

    public void render(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bounds.setX(bounds.getX() - delta(horizontalVelocity));
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            bounds.setX(bounds.getX() + delta(horizontalVelocity));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            bounds.setY(bounds.getY() + delta(gravity * 2));
        }
        boolean hasCollided = false;
        for(Shape2D collider: game.colliders){
            if(collider.contains(bounds.x, bounds.y)){
                hasCollided = true;
                break;
            }
        }
//        for(MapObject mapObject: game.getMap().getLayers().get("colliders").getObjects()){
//            Shape2D collider;
//            if(mapObject instanceof RectangleMapObject){
//                collider = ((RectangleMapObject)mapObject).getRectangle();
//            } else {
//                collider = ((PolygonMapObject)mapObject).getPolygon();
//            }
//            Vector3 projectedPos = game.getCamera().project(new Vector3(bounds.x, bounds.y, 0));
//            if(collider.contains(projectedPos.x, projectedPos.y)){
//                hasCollided = true;
//                break;
//            }
//        }
        if(!hasCollided){
            bounds.setY(bounds.getY() - delta(gravity));
        }
        game.getBatch().draw(sprite, bounds.getX(), bounds.getY(), bounds.width, bounds.height);
    }

    private float delta(float value){
        return value * Gdx.graphics.getDeltaTime();
    }
}
