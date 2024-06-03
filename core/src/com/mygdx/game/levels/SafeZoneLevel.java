package com.mygdx.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.AssetsNames;

public class SafeZoneLevel extends Level {
    public SafeZoneLevel() {
        super(AssetsNames.SAFEZONE_LEVEL_TILEMAP, new Vector2(18, 32));
    }
}
