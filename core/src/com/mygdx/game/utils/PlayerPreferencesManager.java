package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PlayerPreferencesManager {
    private static final String PREFERENCES_FILE = "preferences";
    private static PlayerPreferencesManager instance;
    public static PlayerPreferencesManager getInstance(){
        if (instance == null)
            instance = new PlayerPreferencesManager();
        return instance;
    }

    private float musicVolume;
    private float soundVolume;
    private boolean isFullScreen;

    private PlayerPreferencesManager() {
        loadPreferences();
    }

    private void loadPreferences() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_FILE);
        musicVolume = preferences.getFloat("musicVolume", 1f);
        soundVolume = preferences.getFloat("soundVolume", 1f);
        isFullScreen = preferences.getBoolean("isFullScreen", true);
    }

    public void savePreferences() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_FILE);
        preferences.putFloat("musicVolume", musicVolume);
        preferences.putFloat("soundVolume", soundVolume);
        preferences.putBoolean("isFullScreen", isFullScreen);
        preferences.flush();
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }
}
