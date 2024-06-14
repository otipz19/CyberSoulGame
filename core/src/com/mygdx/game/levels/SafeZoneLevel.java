package com.mygdx.game.levels;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.portals.FirstPortal;
import com.mygdx.game.entities.portals.Portal;
import com.mygdx.game.entities.portals.SecondPortal;
import com.mygdx.game.entities.portals.ThirdPortal;
import com.mygdx.game.parallax.ParallaxBackground;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;

/**
 * Represents a safe zone level in the game, providing a peaceful environment for the player.
 * Includes portals that lead to unlocked levels based on player progression.
 */
public class SafeZoneLevel extends Level {

    /**
     * Constructs a SafeZoneLevel instance, initializing it with the corresponding tiled map.
     * Enables portals to unlocked levels based on player progression.
     */
    public SafeZoneLevel() {
        super(Assets.TiledMaps.SAFEZONE_LEVEL_TILEMAP);
        enablePortalsToUnlockedLevels();
    }

    /**
     * Enables portals to unlocked levels based on the player's maximum unlocked level.
     * If debug mode is enabled, all portals are enabled for testing purposes.
     */
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
        if (MyGdxGame.IS_DEBUG_MODE) {
            for (Portal portal : mapBinder.getPortals()) {
                portal.enable();
            }
        }
    }

    /**
     * Creates the parallax background specific to the Safe Zone level.
     *
     * @return Parallax background instance for the Safe Zone level.
     */
    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(camera, new String[]{
                Assets.Textures.GREENZONE_PARALLAX_1,
                Assets.Textures.GREENZONE_PARALLAX_2,
                Assets.Textures.GREENZONE_PARALLAX_3,
                Assets.Textures.GREENZONE_PARALLAX_4,
                Assets.Textures.GREENZONE_PARALLAX_5
        }, levelHeight);
    }

    /**
     * Retrieves the spawn coordinates for the player in world coordinates.
     * Uses the spawn point from the previous level as determined by player data.
     *
     * @return Spawn coordinates for the player in world units.
     */
    @Override
    protected Vector2 getPlayerSpawnInWorldCoordinates() {
        Rectangle bounds = mapBinder.getPlayerSpawns()
                .filter(spawn -> spawn.getFromLevel().equals(PlayerDataManager.getInstance().getPreviousLevel()))
                .findFirst().orElseThrow().getBounds();
        return coordinatesProjector.unproject(bounds.x, bounds.y + bounds.height);
    }

    /**
     * Configures and initializes background music and sounds for the Safe Zone level.
     * Overrides the base method to set specific music and play teleport sound effects if necessary.
     */
    @Override
    protected void createMusicSound() {
        super.createMusicSound();
        soundPlayer.setBackgroundMusic(Assets.Music.GREENZONE_BG_MUSIC);
        if (PlayerDataManager.getInstance().getMaxLevel().ordinal() != 0)
            soundPlayer.playSound(Assets.Sound.TELEPORT_SOUND);
    }
}
