package com.mygdx.game.animation.concrete.portals;

import com.mygdx.game.utils.Assets;

/**
 * Animator class specifically for the second portal.
 * Inherits from PortalAnimator.
 */
public class SecondPortalAnimator extends PortalAnimator {

    /**
     * Specifies the number of columns in the sprite sheet for the portal.
     *
     * @return Number of columns.
     */
    @Override
    protected int getCols() {
        return 12;
    }

    /**
     * Specifies the sprite sheet for the inactive state of the portal.
     *
     * @return Path to the sprite sheet for the inactive state.
     */
    @Override
    protected String getInactiveSheetName() {
        return Assets.Textures.PORTAL_SECOND_INACTIVE_SPRITESHEET;
    }

    /**
     * Specifies the sprite sheet for the activating state of the portal.
     *
     * @return Path to the sprite sheet for the activating state.
     */
    @Override
    protected String getActivatingSheetName() {
        return Assets.Textures.PORTAL_SECOND_ACTIVATING_SPRITESHEET;
    }
}
