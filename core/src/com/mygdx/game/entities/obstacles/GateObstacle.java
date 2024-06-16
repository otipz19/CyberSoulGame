package com.mygdx.game.entities.obstacles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.obstacles.GateObstacleAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.MortalEntity;
import com.mygdx.game.entities.Surface;
import com.mygdx.game.entities.projectiles.ProjectileCollidable;
import com.mygdx.game.entities.resources.RelativeInstantDamageEffect;
import com.mygdx.game.entities.resources.ResourcesManager;
import com.mygdx.game.entities.sensors.SensorPosition;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.ObstacleData;
import com.mygdx.game.physics.Collider;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a gate obstacle in the game, providing functionality to animate, damage entities,
 * and control passage through it.
 * Extends the Entity class and implements ICollisionListener and ProjectileCollidable interfaces.
 */
public abstract class GateObstacle extends Entity implements ICollisionListener, ProjectileCollidable {

    /**
     * Enumeration representing different states of the gate obstacle.
     * States include CLOSED, OPENING, OPENED, and CLOSING.
     */
    private enum State {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING,
    }

    private static final Map<State, Animator.State> objectToAnimatorState = new HashMap<>() {{
        put(State.OPENING, GateObstacleAnimator.State.OPENING);
        put(State.CLOSING, GateObstacleAnimator.State.CLOSING);
    }};

    private static final State[] STATES = {State.CLOSED, State.OPENING, State.OPENED, State.CLOSING};
    private int stateIndex;
    private State state;

    private static final float STATIC_STATES_PERIOD = 1f;
    private float period;
    private float elapsedTime;

    private final Fixture mainFixture;
    private final Array<MortalEntity<ResourcesManager>> entitiesToDamage = new Array<>();

    /**
     * Constructs a GateObstacle object.
     *
     * @param level        The level where the obstacle is located.
     * @param collider     The collider defining the physical boundaries of the obstacle.
     * @param obstacleData Data specific to this obstacle, such as its dimensions and position.
     */
    public GateObstacle(Level level, Collider collider, ObstacleData obstacleData) {
        this.level = level;
        this.body = new Surface(level, collider).getBody();
        animator = createAnimator();
        setupSize(obstacleData, level.getCoordinatesProjector());
        mainFixture = body.getFixtureList().first();
        createDamageFixture();
    }

    /**
     * Creates and returns the animator specific to this gate obstacle.
     * Subclasses must implement this method to provide a specific animator instance.
     *
     * @return The animator object for animating this gate obstacle.
     */
    protected abstract Animator createAnimator();

    /**
     * Creates a sensor fixture for detecting entities inside the gate obstacle's area.
     */
    private void createDamageFixture() {
        Shape colliderShape = SensorPosition.SLIM_INSIDE.getColliderShape(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = SensorPosition.SLIM_INSIDE.getColliderShape(width, height);
        fixtureDef.isSensor = true;

        Fixture damageFixture = body.createFixture(fixtureDef);
        damageFixture.setUserData(this);

        colliderShape.dispose();
    }

    /**
     * Sets up the size of the gate obstacle based on the provided obstacle data and projector.
     *
     * @param obstacleData Data defining the dimensions and position of the obstacle.
     * @param projector    Coordinates projector for converting between world and screen coordinates.
     */
    private void setupSize(ObstacleData obstacleData, CoordinatesProjector projector) {
        Vector2 worldSize = projector.toWorldSize(obstacleData.getBounds());
        this.width = worldSize.x;
        this.height = worldSize.y;
    }

    /**
     * Updates the gate obstacle's state and animation.
     *
     * @param deltaTime Time elapsed since the last update.
     */
    @Override
    public void render(float deltaTime) {
        updateState(deltaTime, this::onStateChanged);
        animate(deltaTime);
    }

    /**
     * Updates the state of the gate obstacle based on elapsed time and invokes the specified action
     * when the state changes.
     *
     * @param deltaTime     Time elapsed since the last update.
     * @param onStateChanged Action to perform when the state changes.
     */
    private void updateState(float deltaTime, Runnable onStateChanged) {
        elapsedTime += deltaTime;
        if (elapsedTime > period) {
            elapsedTime = 0;
            stateIndex++;
            state = getCurrentState();
            onStateChanged.run();
        }
    }

    /**
     * Retrieves the current state of the gate obstacle based on the state index.
     *
     * @return The current state of the gate obstacle.
     */
    private State getCurrentState() {
        return STATES[stateIndex % STATES.length];
    }

    /**
     * Handles actions to perform when the gate obstacle's state changes.
     */
    private void onStateChanged() {
        switch (state) {
            case CLOSED -> {
                allowPass(false);
                damageEntitiesInside();
                setStaticPeriod();
            }
            case OPENING -> {
                allowPass(false);
                updateAnimation();
                setAnimationPeriod();
            }
            case OPENED -> {
                allowPass(true);
                setStaticPeriod();
            }
            case CLOSING -> {
                allowPass(false);
                freezeEntitiesInside();
                updateAnimation();
                setAnimationPeriod();
            }
        }
    }

    /**
     * Sets whether entities can pass through the gate obstacle.
     *
     * @param allow True to allow passage, false otherwise.
     */
    private void allowPass(boolean allow) {
        mainFixture.setSensor(allow);
    }

    /**
     * Sets the period for static states of the gate obstacle.
     */
    private void setStaticPeriod() {
        period = STATIC_STATES_PERIOD;
    }

    /**
     * Sets the period for animated states of the gate obstacle based on the current animation duration.
     */
    private void setAnimationPeriod() {
        period = animator.getCurrentAnimationDuration();
    }

    /**
     * Damages entities currently inside the gate obstacle.
     */
    private void damageEntitiesInside() {
        entitiesToDamage.forEach(e -> {
            e.getResourcesManager().setInvincible(false);
            e.addResourcesEffect(new RelativeInstantDamageEffect<>(1));
        });
    }

    /**
     * Freezes entities currently inside the gate obstacle.
     */
    private void freezeEntitiesInside() {
        entitiesToDamage.forEach(e -> e.getBody().setType(BodyDef.BodyType.StaticBody));
    }

    /**
     * Updates the current animation state of the gate obstacle.
     */
    private void updateAnimation() {
        animator.setState(objectToAnimatorState.get(state));
    }

    /**
     * Handles collision events when another entity collides with the gate obstacle.
     *
     * @param other The entity that collided with the gate obstacle.
     */
    @Override
    public void onCollisionEnter(Entity other) {
        if (other instanceof MortalEntity) {
            try {
                var entity = (MortalEntity<ResourcesManager>) other;
                entitiesToDamage.add(entity);
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    /**
     * Handles collision exit events when another entity exits collision with the gate obstacle.
     *
     * @param other The entity that exited collision with the gate obstacle.
     */
    @Override
    public void onCollisionExit(Entity other) {
        if (other instanceof MortalEntity) {
            try {
                var entity = (MortalEntity<ResourcesManager>) other;
                entitiesToDamage.removeValue(entity, true);
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    /**
     * Checks if the gate obstacle is currently in the OPENED state.
     *
     * @return True if the gate obstacle is opened, false otherwise.
     */
    public boolean isOpened() {
        return getCurrentState() == State.OPENED;
    }
}
