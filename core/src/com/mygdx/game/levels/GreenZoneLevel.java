package com.mygdx.game.levels;

import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.Assets;

/**
 * GreenZoneLevel is a subclass of DangerousLevel that represents a specific level in the game,
 * featuring a green zone environment.
 */
public class GreenZoneLevel extends DangerousLevel {

    /**
     * Constructs a GreenZoneLevel using the predefined tile map associated with the green zone level.
     */
    public GreenZoneLevel() {
        super(Assets.TiledMaps.GREENZONE_LEVEL_TILEMAP);
    }

    /**
     * Creates and returns the parallax background specific to the green zone level.
     *
     * @return The ParallaxBackground object representing the background of the green zone level.
     */
    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[] {
                Assets.Textures.GREENZONE_PARALLAX_1,
                Assets.Textures.GREENZONE_PARALLAX_2,
                Assets.Textures.GREENZONE_PARALLAX_3,
                Assets.Textures.GREENZONE_PARALLAX_4,
                Assets.Textures.GREENZONE_PARALLAX_5
        }, levelHeight);
    }

    /**
     * Creates and sets up the music and sound effects specific to the green zone level.
     * This includes setting the background music and playing a teleport sound effect.
     */
    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(Assets.Music.GREENZONE_BG_MUSIC);
        soundPlayer.playSound(Assets.Sound.TELEPORT_SOUND);
    }
}
