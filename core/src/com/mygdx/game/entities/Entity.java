package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.base.Animator;

/**
 * Entity is an abstract class that extends GameObject and implements IRenderable,
 * providing common functionality and properties for game entities.
 */
public abstract class Entity extends GameObject implements IRenderable {

    /**
     * Animator responsible for handling entity animations.
     */
    protected Animator animator;

    /**
     * Width of the entity.
     */
    protected float width;

    /**
     * Height of the entity.
     */
    protected float height;

    /**
     * Retrieves the width of the entity.
     *
     * @return The width of the entity.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Sets the width of the entity.
     *
     * @param width The width to set for the entity.
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Retrieves the height of the entity.
     *
     * @return The height of the entity.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the height of the entity.
     *
     * @param height The height to set for the entity.
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Retrieves the position of the entity.
     *
     * @return The position of the entity as a Vector2.
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Retrieves the center position of the entity.
     *
     * @return The center position of the entity as a Vector2.
     */
    public Vector2 getCenter() {
        return new Vector2(body.getPosition().x + width / 2, body.getPosition().y + height / 2);
    }

    /**
     * Sets the position of the entity.
     *
     * @param x The x-coordinate to set for the entity's position.
     * @param y The y-coordinate to set for the entity's position.
     */
    public void setPosition(float x, float y) {
        body.getPosition().set(x, y);
    }

    /**
     * Sets the x-coordinate of the entity's position.
     *
     * @param x The x-coordinate to set for the entity's position.
     */
    public void setX(float x) {
        Vector2 position = getPosition();
        position.x = x;
        body.setTransform(position, body.getAngle());
    }

    /**
     * Sets the y-coordinate of the entity's position.
     *
     * @param y The y-coordinate to set for the entity's position.
     */
    public void setY(float y) {
        Vector2 position = getPosition();
        position.y = y;
        body.setTransform(position, body.getAngle());
    }

    /**
     * Retrieves the animator responsible for entity animations.
     *
     * @return The Animator object associated with the entity.
     */
    public Animator getAnimator() {
        return animator;
    }

    /**
     * Sets the animator responsible for entity animations.
     *
     * @param animator The Animator object to set for the entity.
     */
    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    /**
     * Animates the entity based on the elapsed time.
     *
     * @param deltaTime The time elapsed since the last frame update.
     */
    public void animate(float deltaTime) {
        animator.animate(MyGdxGame.getInstance().batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);
    }
}