package com.mygdx.game.animation.concrete.portals;

import com.mygdx.game.utils.AssetsNames;

public class FirstPortalAnimator extends PortalAnimator {
    @Override
    protected int getCols() {
        return 16;
    }

    @Override
    protected String getInactiveSheetName() {
        return AssetsNames.PORTAL_FIRST_INACTIVE_SPRITESHEET;
    }

    @Override
    protected String getActivatingSheetName() {
        return AssetsNames.PORTAL_FIRST_ACTIVATING_SPRITESHEET;
    }
}
