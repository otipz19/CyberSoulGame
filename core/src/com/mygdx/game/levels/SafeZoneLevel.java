package com.mygdx.game.levels;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.portals.FirstPortal;
import com.mygdx.game.entities.portals.Portal;
import com.mygdx.game.entities.portals.SecondPortal;
import com.mygdx.game.entities.portals.ThirdPortal;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.PlayerDataManager;

public class SafeZoneLevel extends Level {
    public SafeZoneLevel() {
        super(AssetsNames.SAFEZONE_LEVEL_TILEMAP);
        int maxLevel = PlayerDataManager.getInstance().getMaxLevel().ordinal();
        for (Portal portal : portals) {
            portal.disable();
            switch (portal) {
                case FirstPortal firstPortal -> portal.enable();
                case SecondPortal secondPortal when maxLevel >= 1 -> portal.enable();
                case ThirdPortal thirdPortal when maxLevel >= 2 -> portal.enable();
                default -> {}
            }
        }
    }

    @Override
    protected Vector2 getPlayerSpawn() {
        Rectangle bounds = objectsParser.getPlayerSpawns()
                .filter(spawn -> spawn.getFromLevel().equals(PlayerDataManager.getInstance().getPreviousLevel()))
                .findFirst().orElseThrow().getBounds();
        return new Vector2(bounds.x, bounds.y + bounds.height);
    }
}
