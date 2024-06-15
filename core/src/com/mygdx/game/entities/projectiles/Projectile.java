package com.mygdx.game.entities.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.*;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.ColliderCreator;

/**
 * Abstract class representing a projectile entity in the game.
 * Extends the Entity class and implements ITriggerListener and IRenderable interfaces.
 */
public abstract class Projectile extends Entity implements ITriggerListener, IRenderable {
    public static final float TIME_TO_LIVE = 5f;

    protected Entity owner;
    protected final float initialSpeedX;
    protected final float initialSpeedY;
    protected Array<Runnable> onExplosionActions;
    protected boolean hasCollided;
    protected float lifeDuration;

    /**
     * Constructs a Projectile object with the specified parameters.
     *
     * @param owner              The entity that fired this projectile.
     * @param x                  The initial x-coordinate of the projectile.
     * @param y                  The initial y-coordinate of the projectile.
     * @param width              The width of the projectile's hitbox.
     * @param height             The height of the projectile's hitbox.
     * @param initialSpeed       The initial speed of the projectile.
     * @param initialAngle       The initial angle (direction) of the projectile in radians.
     * @param isAffectedByGravity Whether the projectile is affected by gravity.
     */
    public Projectile(Entity owner, float x, float y, float width, float height, float initialSpeed, float initialAngle, boolean isAffectedByGravity) {
        this.owner = owner;
        this.level = owner.getLevel();
        this.width = width;
        this.height = height;
        this.initialSpeedX = initialSpeed * MathUtils.cos(initialAngle);
        this.initialSpeedY = initialSpeed * MathUtils.sin(initialAngle);

        body = BodyCreator.createProjectileBody(level.world, ColliderCreator.create(x, y, width, height), initialSpeedX, initialSpeedY, isAffectedByGravity);
        body.getFixtureList().first().setUserData(this);

        onExplosionActions = new Array<>();
        onExplosionActions.add(() -> level.addDelayedAction(getDestructionDelay(), this::destruct));

        level.addProjectile(this);
    }

    /**
     * Renders the projectile on the screen.
     *
     * @param deltaTime The time elapsed since the last frame in seconds.
     */
    @Override
    public void render(float deltaTime) {
        lifeDuration += deltaTime;
        if (lifeDuration > TIME_TO_LIVE)
            explode();
        Vector2 velocity = body.getLinearVelocity();
        if (velocity.x == 0 && velocity.y == 0 && !hasCollided)
            body.setLinearVelocity(initialSpeedX, initialSpeedY);
        body.setActive(!hasCollided);
        animator.animate(MyGdxGame.getInstance().batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);
    }

    /**
     * Handles the collision of this projectile with another entity.
     *
     * @param entity The entity with which this projectile collides.
     */
    protected abstract void collideWith(Entity entity);

    /**
     * Destroys the projectile by removing its body from the physics world.
     */
    protected void destruct() {
        level.world.destroyBody(body);
    }

    /**
     * Retrieves the delay before this projectile is destroyed after collision or action.
     *
     * @return The destruction delay in seconds.
     */
    public abstract float getDestructionDelay();

    /**
     * Adds an action to execute upon the projectile's explosion (collision or other trigger).
     *
     * @param action The action to add.
     */
    public void addOnExplosionAction(Runnable action){
        onExplosionActions.add(action);
    }

    /**
     * Removes a specific action from the list of actions to execute upon explosion.
     *
     * @param action The action to remove.
     */
    public void removeOnExplosionAction(Runnable action){
        onExplosionActions.removeValue(action, true);
    }

    /**
     * Clears all actions that are set to execute upon explosion.
     */
    public void clearOnExplosionActions(){
        onExplosionActions.clear();
    }

    /**
     * Handles the event when this projectile enters a trigger zone.
     *
     * @param other The game object triggering this event.
     */
    @Override
    public void onTriggerEnter(GameObject other) {
        if (other == owner)
            return;

        if (other instanceof ProjectileCollidable) {
            if (other instanceof Entity entity)
                collideWith(entity);
            explode();
        }
    }

    /**
     * Handles the explosion event.
     */
    protected void explode() {
        hasCollided = true;
        onExplosionActions.forEach(Runnable::run);
        onExplosionActions.clear();
    }

    /**
     * Handles the event when this projectile exits a trigger zone.
     *
     * @param other The game object triggering this event.
     */
    @Override
    public void onTriggerExit(GameObject other) {}

}