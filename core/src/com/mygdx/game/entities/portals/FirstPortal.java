package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.FirstPortalAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;

public class FirstPortal extends Portal {
    public FirstPortal(Level level, PortalData portalData) {
        super(level, portalData, new FirstPortalAnimator());
    }
}
