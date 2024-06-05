package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.FirstPortalAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.PortalData;

public class FirstPortal extends Portal {
    public FirstPortal(Level level, PortalData portalData, CoordinatesProjector projector) {
        super(level, portalData, projector, new FirstPortalAnimator());
    }
}
