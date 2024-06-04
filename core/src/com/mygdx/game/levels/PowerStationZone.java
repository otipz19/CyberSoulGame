package com.mygdx.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.AssetsNames;

public class PowerStationZone extends Level {
    public PowerStationZone() {
        super(AssetsNames.POWERSTATION_LEVEL_TILEMAP, new Vector2(1, 1));
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[] {
                AssetsNames.POWERSTATION_PARALLAX_1,
                AssetsNames.POWERSTATION_PARALLAX_2,
                AssetsNames.POWERSTATION_PARALLAX_3,
                AssetsNames.POWERSTATION_PARALLAX_4,
                AssetsNames.POWERSTATION_PARALLAX_5
        });
    }
}
