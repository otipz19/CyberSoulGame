package com.mygdx.game.levels;

import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.Assets;

public class IndustrialZoneLevel extends DangerousLevel {
    public IndustrialZoneLevel() {
        super(Assets.TiledMaps.INDUSTRIALZONE_LEVEL_TILEMAP);
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[] {
                Assets.Textures.INDUSTRIALZONE_PARALLAX_1,
                Assets.Textures.INDUSTRIALZONE_PARALLAX_3,
                Assets.Textures.INDUSTRIALZONE_PARALLAX_4,
                Assets.Textures.INDUSTRIALZONE_PARALLAX_5
        }, levelHeight);
    }

    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(Assets.Music.INDUSTRIALZONE_BG_MUSIC);
        soundPlayer.playSound(Assets.Sound.TELEPORT_SOUND);
    }
}
