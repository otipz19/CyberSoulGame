package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.animation.ObstacleAnimator;
import com.mygdx.game.levels.Level;

public class Obstacle extends Entity {
    public Obstacle(Level level, Body body){
        this.level = level;
        this.body = body;
        animator = new ObstacleAnimator();
        width = 2;
        height = 3;
    }

    @Override
    public void render() {
        animator.animate(level.game.batch, body.getPosition().x, body.getPosition().y, width, height);
    }
}
