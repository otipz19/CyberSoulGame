package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
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
