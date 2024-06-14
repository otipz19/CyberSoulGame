package com.mygdx.game.levels;

import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.Assets;

/**
 * Represents a specific level in the game set in the Power Station zone.
 * Extends DangerousLevel, configuring specific assets and background for this zone.
 */
public class PowerStationZone extends DangerousLevel {

    /**
     * Constructs a new PowerStationZone instance.
     * Initializes the level with the specified tiled map.
     */
    public PowerStationZone() {
        super(Assets.TiledMaps.POWERSTATION_LEVEL_TILEMAP);
    }

    /**
     * Creates the parallax background specific to the Power Station zone.
     *
     * @return Parallax background instance tailored for this zone.
     */
    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[] {
                Assets.Textures.POWERSTATION_PARALLAX_1,
                Assets.Textures.POWERSTATION_PARALLAX_2,
                Assets.Textures.POWERSTATION_PARALLAX_3,
                Assets.Textures.POWERSTATION_PARALLAX_4,
                Assets.Textures.POWERSTATION_PARALLAX_5
        }, levelHeight);
    }

    /**
     * Configures and initializes background music and sounds for the Power Station zone.
     * Overrides the base method to set specific music and play additional sound effects.
     */
    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(Assets.Music.POWERSTATION_BG_MUSIC);
        soundPlayer.playSound(Assets.Sound.TELEPORT_SOUND);
    }
}
