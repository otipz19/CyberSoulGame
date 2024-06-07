package com.mygdx.game.animation.concrete.portals;

import com.mygdx.game.utils.Assets;

public class ThirdPortalAnimator extends PortalAnimator {
    @Override
    protected int getCols() {
        return 7;
    }

    @Override
    protected String getInactiveSheetName() {
        return Assets.Textures.PORTAL_THIRD_INACTIVE_SPRITESHEET;
    }

    @Override
    protected String getActivatingSheetName() {
        return Assets.Textures.PORTAL_THIRD_ACTIVATING_SPRITESHEET;
    }
}
