package com.mygdx.game.sound;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.PlayerPreferencesManager;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer extends ApplicationAdapter {
    private static SoundPlayer instance;
    public static SoundPlayer getInstance(){
        if (instance == null)
            instance = new SoundPlayer();
        return instance;
    }
    private Map<String, Sound> sounds = new HashMap<>();
    private Map<String, Music> musics = new HashMap<>();

    @Override
    public void create() {

    }

    @Override
    public void render() {
        for(Music music: instance.musics.values()){
            music.setVolume(PlayerPreferencesManager.getInstance().getMusicVolume());
        }
    }
    @Override
    public void dispose() {
        for(Sound sound: sounds.values()){
            sound.dispose();
        }
        for(Music music: musics.values()){
            music.dispose();
        }
        musics.clear();
        sounds.clear();
    }
    public Music getMusic(String fileName) {
        return musics.computeIfAbsent(fileName, fn-> Gdx.audio.newMusic(Gdx.files.internal(fn)));
    }
    public Sound getSound(String fileName) {
        if (sounds.containsKey(fileName)) {
            long id = sounds.get(fileName).play();
            sounds.get(fileName).setVolume(id,PlayerPreferencesManager.getInstance().getSoundVolume());
            return sounds.get(fileName);
        }
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(fileName));
        long id = sound.play();
        sound.setVolume(id,PlayerPreferencesManager.getInstance().getSoundVolume());
        sounds.put(fileName, sound);
        return sound;
    }
}
  