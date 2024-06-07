package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.SecondPortalAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;

public class SecondPortal extends Portal {
    public SecondPortal(Level level, PortalData portalData) {
        super(level, portalData, new SecondPortalAnimator());
    }
}
