package com.mygdx.game.sound;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.PlayerPreferencesManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages background music and sound effects playback, including volume control and pausing functionality.
 */
public class SoundPlayer {
    private static SoundPlayer instance;
    private Music music;
    private final Map<Sound, Array<Long>> soundIDs;

    private boolean isMusicPaused;
    private boolean isSoundsPaused;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private SoundPlayer() {
        soundIDs = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of SoundPlayer.
     *
     * @return The singleton instance of SoundPlayer.
     */
    public static SoundPlayer getInstance() {
        if (instance == null)
            instance = new SoundPlayer();
        return instance;
    }

    /**
     * Updates the volume settings of background music and all sounds based on player preferences.
     */
    public void update() {
        music.setVolume(PlayerPreferencesManager.getInstance().getMusicVolume());
        for (Sound sound : soundIDs.keySet()) {
            for (long id : soundIDs.get(sound)) {
                sound.setVolume(id, PlayerPreferencesManager.getInstance().getSoundVolume());
            }
        }
    }

    /**
     * Sets the background music to the specified music file, adjusting volume and looping settings.
     *
     * @param musicName The file name of the music asset.
     */
    public void setBackgroundMusic(String musicName) {
        clearBackgroundMusic();
        music = MyGdxGame.getInstance().assetManager.get(musicName, Music.class);
        music.setVolume(PlayerPreferencesManager.getInstance().getMusicVolume());
        music.setLooping(true);
        music.play();
        if (isMusicPaused)
            music.pause();
    }

    /**
     * Stops and clears the currently playing background music.
     */
    public void clearBackgroundMusic() {
        if (music != null) {
            music.stop();
            music = null;
        }
    }

    /**
     * Plays a sound effect specified by the sound file name.
     *
     * @param soundName The file name of the sound effect asset.
     */
    public void playSound(String soundName) {
        Sound sound = MyGdxGame.getInstance().assetManager.get(soundName, Sound.class);
        long id = sound.play();
        sound.setVolume(id, PlayerPreferencesManager.getInstance().getSoundVolume());
        soundIDs.compute(sound, (s, ids) -> {
            if (ids == null)
                ids = new Array<>();
            ids.add(id);
            return ids;
        });
        if (isSoundsPaused)
            sound.pause();
    }

    /**
     * Stops all currently playing sound effects.
     */
    public void clearSounds() {
        for (Sound sound : soundIDs.keySet()) {
            sound.stop();
        }
        soundIDs.clear();
    }

    /**
     * Stops all currently playing background music and sound effects.
     */
    public void clearAll() {
        clearBackgroundMusic();
        clearSounds();
    }

    /**
     * Pauses playback of all background music and sound effects.
     */
    public void pauseAll() {
        pauseBackgroundMusic();
        pauseSounds();
    }

    /**
     * Pauses playback of the background music.
     */
    public void pauseBackgroundMusic() {
        if (music != null)
            music.pause();
        isMusicPaused = true;
    }

    /**
     * Pauses playback of all sound effects.
     */
    public void pauseSounds() {
        for (Sound sound : soundIDs.keySet()) {
            sound.pause();
        }
        isSoundsPaused = true;
    }

    /**
     * Resumes playback of all background music and sound effects.
     */
    public void unpauseAll() {
        unpauseBackgroundMusic();
        unpauseSounds();
    }

    /**
     * Resumes playback of the background music.
     */
    public void unpauseBackgroundMusic() {
        if (music != null)
            music.play();
        isMusicPaused = false;
    }

    /**
     * Resumes playback of all sound effects.
     */
    public void unpauseSounds() {
        for (Sound sound : soundIDs.keySet()) {
            sound.resume();
        }
        isSoundsPaused = false;
    }
}
