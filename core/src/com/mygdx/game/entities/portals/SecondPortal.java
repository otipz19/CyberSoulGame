package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.SecondPortalAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;

/**
 * Represents the second type of portal in the game world, extending the functionality of Portal.
 * It uses a specific animator, SecondPortalAnimator, for its animation.
 */
public class SecondPortal extends Portal {

    /**
     * Constructs a SecondPortal object with the specified Level and PortalData,
     * using the SecondPortalAnimator for animation.
     *
     * @param level      The Level where the portal exists.
     * @param portalData The data defining the properties of the portal.
     */
    public SecondPortal(Level level, PortalData portalData) {
        super(level, portalData, new SecondPortalAnimator());
    }
}
