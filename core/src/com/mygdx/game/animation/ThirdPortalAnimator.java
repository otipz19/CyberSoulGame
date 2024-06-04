package com.mygdx.game.animation;

import com.mygdx.game.utils.AssetsNames;

public class ThirdPortalAnimator extends PortalAnimator {
    @Override
    protected int getCols() {
        return 7;
    }

    @Override
    protected String getInactiveSheetName() {
        return AssetsNames.PORTAL_THIRD_INACTIVE_SPRITESHEET;
    }

    @Override
    protected String getActivatingSheetName() {
        return AssetsNames.PORTAL_THIRD_ACTIVATING_SPRITESHEET;
    }
}
