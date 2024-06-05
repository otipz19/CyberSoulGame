package com.mygdx.game.entities.portals;

import com.mygdx.game.animation.concrete.portals.ThirdPortalAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.PortalData;

public class ThirdPortal extends Portal {
    public ThirdPortal(Level level, PortalData portalData, CoordinatesProjector projector) {
        super(level, portalData, projector, new ThirdPortalAnimator());
    }
}
