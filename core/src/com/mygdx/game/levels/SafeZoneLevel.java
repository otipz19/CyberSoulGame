package com.mygdx.game.levels;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.portals.FirstPortal;
import com.mygdx.game.entities.portals.Portal;
import com.mygdx.game.entities.portals.SecondPortal;
import com.mygdx.game.entities.portals.ThirdPortal;
import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.PlayerDataManager;

public class SafeZoneLevel extends Level {
    public SafeZoneLevel() {
        super(AssetsNames.SAFEZONE_LEVEL_TILEMAP);
        enablePortalsToUnlockedLevels();
    }

    private void enablePortalsToUnlockedLevels() {
        int maxLevel = PlayerDataManager.getInstance().getMaxLevel().ordinal();
        for (Portal portal : mapBinder.getPortals()) {
            portal.disable();
            switch (portal) {
                case FirstPortal firstPortal -> portal.enable();
                case SecondPortal secondPortal when maxLevel >= 1 -> portal.enable();
                case ThirdPortal thirdPortal when maxLevel >= 2 -> portal.enable();
                default -> {
                }
            }
        }
        if(MyGdxGame.IS_DEBUG_MODE) {
            for (Portal portal : mapBinder.getPortals()) {
                portal.enable();
            }
        }
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[]{
                AssetsNames.GREENZONE_PARALLAX_1,
                AssetsNames.GREENZONE_PARALLAX_2,
                AssetsNames.GREENZONE_PARALLAX_3,
                AssetsNames.GREENZONE_PARALLAX_4,
                AssetsNames.GREENZONE_PARALLAX_5
        }, levelHeight);
    }

    @Override
    protected Vector2 getPlayerSpawnInWorldCoordinates() {
        Rectangle bounds = mapBinder.getPlayerSpawns()
                .filter(spawn -> spawn.getFromLevel().equals(PlayerDataManager.getInstance().getPreviousLevel()))
                .findFirst().orElseThrow().getBounds();
        return coordinatesProjector.unproject(bounds.x, bounds.y + bounds.height);
    }

    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(AssetsNames.GREENZONE_BG_MUSIC);
        if (PlayerDataManager.getInstance().getMaxLevel().ordinal() != 0)
            soundPlayer.playSound(AssetsNames.TELEPORT_SOUND);
    }
}
