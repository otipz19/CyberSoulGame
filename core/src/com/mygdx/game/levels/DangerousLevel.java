package com.mygdx.game.levels;

import com.mygdx.game.animation.concrete.portals.PortalAnimator;

/**
 * DangerousLevel is an abstract class that extends Level, representing a level in the game that contains
 * dangerous elements.
 */
public abstract class DangerousLevel extends Level {

    /**
     * Constructs a DangerousLevel with the specified tile map name.
     *
     * @param tileMapName The name of the tile map associated with the level.
     */
    public DangerousLevel(String tileMapName) {
        super(tileMapName);
        inactivateStartingPortal();
    }

    /**
     * Inactivates the starting portal in the level if it is disabled.
     * Iterates through all portals in the level and sets the animator state
     * to INACTIVATING for those that are disabled.
     */
    private void inactivateStartingPortal() {
        mapBinder.getPortals().forEach(portal -> {
            if (!portal.isEnabled()) {
                portal.getAnimator().setState(PortalAnimator.State.INACTIVATING);
            }
        });
    }
}
