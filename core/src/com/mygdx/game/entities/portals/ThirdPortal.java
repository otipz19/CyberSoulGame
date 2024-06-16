package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.ThirdPortalAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;
/**
 * Represents the third type of portal in the game world, extending the functionality of Portal.
 * It uses a specific animator, ThirdPortalAnimator, for its animation.
 */
public class ThirdPortal extends Portal {
    /**
     * Constructs a ThirdPortal object with the specified Level and PortalData,
     * using the ThirdPortalAnimator for animation.
     *
     * @param level      The Level where the portal exists.
     * @param portalData The data defining the properties of the portal.
     */
    public ThirdPortal(Level level, PortalData portalData) {
        super(level, portalData, new ThirdPortalAnimator());
    }
}
