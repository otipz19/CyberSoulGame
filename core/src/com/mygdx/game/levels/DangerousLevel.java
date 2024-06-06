package com.mygdx.game.levels;

import com.mygdx.game.animation.concrete.portals.PortalAnimator;

public abstract class DangerousLevel extends Level {
    public DangerousLevel(String tileMapName) {
        super(tileMapName);
        inactivateStartingPortal();
    }

    private void inactivateStartingPortal() {
        portals.forEach(portal -> {
            if (!portal.isEnabled()) {
                portal.getAnimator().setState(PortalAnimator.State.INACTIVATING);
            }
        });
    }
}
