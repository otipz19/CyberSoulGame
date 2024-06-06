package com.mygdx.game.sound;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.PlayerPreferencesManager;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private static SoundPlayer instance;
    public static SoundPlayer getInstance(){
        if (instance == null)
            instance = new SoundPlayer();
        return instance;
    }
    private Music music;
    private final Map<Sound, Array<Long>> soundIDs;

    private boolean isMusicPaused;
    private boolean isSoundsPaused;

    private SoundPlayer() {
        soundIDs = new HashMap<>();
    }

    public void update() {
        music.setVolume(PlayerPreferencesManager.getInstance().getMusicVolume());
        for (Sound sound : soundIDs.keySet()) {
            for (long id : soundIDs.get(sound))
                sound.setVolume(id, PlayerPreferencesManager.getInstance().getSoundVolume());
        }
    }

    public void setBackgroundMusic(String musicName) {
        clearBackgroundMusic();
        music = MyGdxGame.getInstance().assetManager.get(musicName, Music.class);
        music.setVolume(PlayerPreferencesManager.getInstance().getMusicVolume());
        music.setLooping(true);
        music.play();
        if (isMusicPaused)
            music.pause();
    }

    public void clearBackgroundMusic() {
        if (music != null) {
            music.stop();
            music = null;
        }
    }

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

    public void clearSounds() {
        for (Sound sound : soundIDs.keySet()) {
            sound.stop();
        }
        soundIDs.clear();
    }

    public void clearAll() {
        clearBackgroundMusic();
        clearSounds();
    }

    public void pauseAll() {
        pauseBackgroundMusic();
        pauseSounds();
    }

    public void pauseBackgroundMusic() {
        if (music != null)
            music.pause();
        isMusicPaused = true;
    }

    public void pauseSounds() {
        for (Sound sound : soundIDs.keySet()) {
            sound.pause();
        }
        isSoundsPaused = true;
    }

    public void unpauseAll() {
        unpauseBackgroundMusic();
        unpauseSounds();
    }

    public void unpauseBackgroundMusic() {
        if (music != null)
            music.play();
        isMusicPaused = false;
    }

    public void unpauseSounds() {
        for (Sound sound : soundIDs.keySet()) {
            sound.resume();
        }
        isSoundsPaused = false;
    }
}
  