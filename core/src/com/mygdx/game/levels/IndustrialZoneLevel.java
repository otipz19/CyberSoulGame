package com.mygdx.game.levels;

import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.Assets;

/**
 * IndustrialZoneLevel is a subclass of DangerousLevel that represents a specific level in the game,
 * featuring an industrial zone environment.
 */
public class IndustrialZoneLevel extends DangerousLevel {

    /**
     * Constructs an IndustrialZoneLevel using the predefined tile map associated with the industrial zone level.
     */
    public IndustrialZoneLevel() {
        super(Assets.TiledMaps.INDUSTRIALZONE_LEVEL_TILEMAP);
    }

    /**
     * Creates and returns the parallax background specific to the industrial zone level.
     *
     * @return The ParallaxBackground object representing the background of the industrial zone level.
     */
    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[] {
                Assets.Textures.INDUSTRIALZONE_PARALLAX_1,
                Assets.Textures.INDUSTRIALZONE_PARALLAX_3,
                Assets.Textures.INDUSTRIALZONE_PARALLAX_4,
                Assets.Textures.INDUSTRIALZONE_PARALLAX_5
        }, levelHeight);
    }

    /**
     * Creates and sets up the music and sound effects specific to the industrial zone level.
     * This includes setting the background music and playing a teleport sound effect.
     */
    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(Assets.Music.INDUSTRIALZONE_BG_MUSIC);
        soundPlayer.playSound(Assets.Sound.TELEPORT_SOUND);
    }
}
