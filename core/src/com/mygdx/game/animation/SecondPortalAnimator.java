package com.mygdx.game.animation;

import com.mygdx.game.utils.AssetsNames;

public class SecondPortalAnimator extends PortalAnimator {

    @Override
    protected int getCols() {
        return 12;
    }

    @Override
    protected String getInactiveSheetName() {
        return AssetsNames.PORTAL_SECOND_INACTIVE_SPRITESHEET;
    }

    @Override
    protected String getActivatingSheetName() {
        return AssetsNames.PORTAL_SECOND_ACTIVATING_SPRITESHEET;
    }
}
