package com.mygdx.game.entities.portals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.concrete.portals.PortalAnimator;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.InteractableEntity;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;

/**
 * Represents a portal entity in the game world that interacts with heroes and enables travel between levels.
 * It extends InteractableEntity to handle interactions and animation through its animator.
 */
public abstract class Portal extends InteractableEntity {

    /**
     * The data associated with this portal, defining its properties and behavior.
     */
    protected final PortalData portalData;

    /**
     * Indicates whether the portal is currently enabled.
     */
    protected boolean isEnabled;

    /**
     * Indicates whether the portal has been activated.
     */
    protected boolean hasActivated;

    /**
     * Constructs a Portal object with the specified Level, PortalData, and PortalAnimator.
     *
     * @param level      The Level where the portal exists.
     * @param portalData The data defining the properties of the portal.
     * @param animator   The animator responsible for animating the portal.
     */
    public Portal(Level level, PortalData portalData, PortalAnimator animator) {
        this.level = level;
        this.portalData = portalData;
        this.isEnabled = portalData.isEnabled();
        Collider collider = ColliderCreator.create(portalData.getBounds(), level.getCoordinatesProjector());
        body = BodyCreator.createStaticBody(level.world, collider, 0, 0, 0);
        Fixture fixture = body.getFixtureList().first();
        fixture.setSensor(true);
        fixture.setUserData(this);
        Vector2 projectedSize = level.getCoordinatesProjector().toWorldSize(portalData.getBounds());
        this.width = projectedSize.x;
        this.height = projectedSize.y;
        this.animator = animator;
    }

    /**
     * Renders the portal, updating its animation state based on the elapsed time.
     *
     * @param deltaTime The time elapsed since the last frame, in seconds.
     */
    @Override
    public void render(float deltaTime) {
        animate(deltaTime);
        hasActivated = animator.getState() == PortalAnimator.State.ACTIVATING && animator.isAnimationFinished();
    }

    /**
     * Handles interaction with the portal by the specified entity.
     * If the interaction is with a Hero and the portal is enabled:
     * - If the portal has not yet been activated, it sets the animator state to ACTIVATING and plays a charging sound.
     * - If the portal has already been activated, it saves the Hero's data and initiates a transition to a new level.
     *
     * @param interactionCause The entity interacting with the portal.
     */
    @Override
    public void interact(Entity interactionCause) {
        if (isEnabled && interactionCause instanceof Hero hero) {
            if (!hasActivated) {
                animator.setState(PortalAnimator.State.ACTIVATING);
                SoundPlayer.getInstance().playSound(Assets.Sound.PORTAL_CHARGING_SOUND);
            } else {
                PlayerDataManager.getInstance().setHeroData(hero.getData());
                MyGdxGame.getInstance().goToNewLevel(portalData.getDestination());
            }
        }
    }

    /**
     * Checks if the portal is currently enabled.
     *
     * @return true if the portal is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Enables the portal, allowing interaction with it.
     */
    public void enable() {
        isEnabled = true;
    }

    /**
     * Disables the portal, preventing interaction with it.
     */
    public void disable() {
        isEnabled = false;
    }
}
