package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.FirstPortalAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;

/**
 * Represents the first portal entity in the game, extending the functionality of the Portal class.
 * Uses the FirstPortalAnimator for animation.
 */
public class FirstPortal extends Portal {

    /**
     * Constructs a FirstPortal object with the specified Level and PortalData, using the FirstPortalAnimator for animation.
     *
     * @param level      The Level where the portal exists.
     * @param portalData The data defining the properties of the portal.
     */
    public FirstPortal(Level level, PortalData portalData) {
        super(level, portalData, new FirstPortalAnimator());
    }
}
