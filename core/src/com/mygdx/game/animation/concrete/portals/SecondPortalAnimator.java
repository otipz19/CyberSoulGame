package com.mygdx.game.animation.concrete.portals;

import com.mygdx.game.utils.Assets;

public class SecondPortalAnimator extends PortalAnimator {

    @Override
    protected int getCols() {
        return 12;
    }

    @Override
    protected String getInactiveSheetName() {
        return Assets.Textures.PORTAL_SECOND_INACTIVE_SPRITESHEET;
    }

    @Override
    protected String getActivatingSheetName() {
        return Assets.Textures.PORTAL_SECOND_ACTIVATING_SPRITESHEET;
    }
}
