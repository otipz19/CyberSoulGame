package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.SecondPortalAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.PortalData;

public class SecondPortal extends Portal {
    public SecondPortal(Level level, PortalData portalData, CoordinatesProjector projector) {
        super(level, portalData, projector, new SecondPortalAnimator());
    }
}
