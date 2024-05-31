package com.mygdx.game.utils;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.SerializationException;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.heroes.HeroData;

import java.io.*;

public class PlayerDataManager {
    private static final String SAVE_FILE = "save.dat";
    private static PlayerDataManager instance;
    public static PlayerDataManager getInstance(){
        if (instance == null)
            instance = new PlayerDataManager();
        return instance;
    }

    private HeroData heroData;
    private MyGdxGame.Levels currentLevel;
    private MyGdxGame.Levels maxLevel;

    private PlayerDataManager(){
        loadData();
    }

    private void loadData() {
        String currentLevelJSON = null, maxLevelJSON = null, heroDataJSON = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(SAVE_FILE))) {
            currentLevelJSON = fileReader.readLine();
            maxLevelJSON = fileReader.readLine();
            heroDataJSON = fileReader.readLine();
            Json json = new Json(JsonWriter.OutputType.json);
            currentLevel = json.fromJson(MyGdxGame.Levels.class, currentLevelJSON);
            maxLevel = json.fromJson(MyGdxGame.Levels.class, maxLevelJSON);
            heroData = json.fromJson(HeroData.class, heroDataJSON);
        } catch (IOException | SerializationException exception) {
            System.out.println("Can't read the save file");
            currentLevel = MyGdxGame.Levels.SAFE;
            maxLevel = MyGdxGame.Levels.SAFE;
            heroData = HeroData.getDefault();
        }
    }

    public void saveData() {
        Json json = new Json(JsonWriter.OutputType.json);
        String currentLevelJSON = json.toJson(currentLevel, MyGdxGame.Levels.class);
        String maxLevelJSON = json.toJson(maxLevel, MyGdxGame.Levels.class);
        String heroDataJSON = json.toJson(heroData, HeroData.class);
        try (FileWriter fileWriter = new FileWriter(SAVE_FILE)) {
            fileWriter.write(currentLevelJSON);
            fileWriter.write("\n");
            fileWriter.write(maxLevelJSON);
            fileWriter.write("\n");
            fileWriter.write(heroDataJSON);
        } catch (IOException e) {
            System.out.println("Can't write the save file");
        }
    }

    public void resetData() {
        File saveFile = new File(SAVE_FILE);
        currentLevel = MyGdxGame.Levels.SAFE;
        maxLevel = MyGdxGame.Levels.SAFE;
        heroData = HeroData.getDefault();
        saveFile.delete();
    }

    public HeroData getHeroData() {
        return heroData;
    }

    public void setHeroData(HeroData heroData) {
        this.heroData = heroData;
    }

    public MyGdxGame.Levels getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(MyGdxGame.Levels level) {
        this.currentLevel = level;
        if (currentLevel.compareTo(maxLevel) > 0)
            maxLevel = currentLevel;
    }

    public MyGdxGame.Levels getMaxLevel() {
        return maxLevel;
    }
}
