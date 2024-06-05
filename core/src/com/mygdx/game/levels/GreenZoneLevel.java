package com.mygdx.game.levels;

import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.AssetsNames;

public class GreenZoneLevel extends DangerousLevel {
    public GreenZoneLevel() {
        super(AssetsNames.GREENZONE_LEVEL_TILEMAP);
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[] {
                AssetsNames.GREENZONE_PARALLAX_1,
                AssetsNames.GREENZONE_PARALLAX_2,
                AssetsNames.GREENZONE_PARALLAX_3,
                AssetsNames.GREENZONE_PARALLAX_4,
                AssetsNames.GREENZONE_PARALLAX_5
        }, levelHeight);
    }

    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(AssetsNames.GREENZONE_BG_MUSIC);
        soundPlayer.playSound(AssetsNames.TELEPORT_SOUND);
    }
}
