package com.mygdx.game.entities.portals;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.concrete.portals.PortalAnimator;
import com.mygdx.game.animation.concrete.portals.ThirdPortalAnimator;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;

/**
 * Represents the last portal entity in the game, extending the functionality of the Portal class.
 * Uses the ThirdPortalAnimator for animation.
 */
public class LastPortal extends Portal {

    /**
     * Constructs a LastPortal object with the specified Level and PortalData, using the ThirdPortalAnimator for animation.
     *
     * @param level      The Level where the portal exists.
     * @param portalData The data defining the properties of the portal.
     */
    public LastPortal(Level level, PortalData portalData) {
        super(level, portalData, new ThirdPortalAnimator());
    }

    /**
     * Handles interaction with the portal by the specified entity.
     * If the interaction is with a Hero and the portal is enabled:
     * - If the portal has not yet been activated, it sets the animator state to ACTIVATING and plays a charging sound.
     * - If the portal has already been activated, it saves the Hero's data, indicating completion of the game.
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
                MyGdxGame.getInstance().gameCompleted();
            }
        }
    }
}
