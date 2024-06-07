package com.mygdx.game.levels;

import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.Assets;

public class GreenZoneLevel extends DangerousLevel {
    public GreenZoneLevel() {
        super(Assets.TiledMaps.GREENZONE_LEVEL_TILEMAP);
    }

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

    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(Assets.Music.GREENZONE_BG_MUSIC);
        soundPlayer.playSound(Assets.Sound.TELEPORT_SOUND);
    }
}
