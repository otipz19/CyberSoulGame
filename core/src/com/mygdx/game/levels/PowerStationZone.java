package com.mygdx.game.levels;

import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.Assets;

public class PowerStationZone extends DangerousLevel {
    public PowerStationZone() {
        super(Assets.TiledMaps.POWERSTATION_LEVEL_TILEMAP);
    }

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

    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(Assets.Music.POWERSTATION_BG_MUSIC);
        soundPlayer.playSound(Assets.Sound.TELEPORT_SOUND);
    }
}
