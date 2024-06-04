package com.mygdx.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.AssetsNames;

public class IndustrialZoneLevel extends DangerousLevel {
    public IndustrialZoneLevel() {
        super(AssetsNames.INDUSTRIALZONE_LEVEL_TILEMAP);
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[] {
                AssetsNames.INDUSTRIALZONE_PARALLAX_1,
                AssetsNames.INDUSTRIALZONE_PARALLAX_3,
                AssetsNames.INDUSTRIALZONE_PARALLAX_4,
                AssetsNames.INDUSTRIALZONE_PARALLAX_5
        }, levelHeight);
    }
}
