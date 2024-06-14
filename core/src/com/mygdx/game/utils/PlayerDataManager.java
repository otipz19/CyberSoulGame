package com.mygdx.game.utils;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.heroes.HeroData;

import java.io.*;

/**
 * Manages the player's game data including hero selection, current and maximum levels,
 * and hero-specific data such as stats and inventory.
 */
public class PlayerDataManager {
    private static final String SAVE_FILE = "save.dat";
    private static PlayerDataManager instance;

    /**
     * Retrieves the singleton instance of PlayerDataManager.
     *
     * @return The singleton instance of PlayerDataManager.
     */
    public static PlayerDataManager getInstance() {
        if (instance == null)
            instance = new PlayerDataManager();
        return instance;
    }

    private HeroData heroData;
    private MyGdxGame.Heroes hero;
    private MyGdxGame.Levels previousLevel;
    private MyGdxGame.Levels currentLevel;
    private MyGdxGame.Levels maxLevel;

    /**
     * Private constructor to enforce singleton pattern and initialize data by loading from file.
     */
    private PlayerDataManager() {
        loadData();
    }

    /**
     * Loads player data from the save file.
     * If the file is missing or corrupted, resets data to default values.
     */
    private void loadData() {
        String heroJSON, currentLevelJSON, previousLevelJSON, maxLevelJSON, heroDataJSON;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(SAVE_FILE))) {
            heroJSON = fileReader.readLine();
            currentLevelJSON = fileReader.readLine();
            previousLevelJSON = fileReader.readLine();
            maxLevelJSON = fileReader.readLine();
            heroDataJSON = fileReader.readLine();
            Json json = new Json(JsonWriter.OutputType.json);
            hero = json.fromJson(MyGdxGame.Heroes.class, heroJSON);
            currentLevel = json.fromJson(MyGdxGame.Levels.class, currentLevelJSON);
            previousLevel = json.fromJson(MyGdxGame.Levels.class, previousLevelJSON);
            maxLevel = json.fromJson(MyGdxGame.Levels.class, maxLevelJSON);
            heroData = json.fromJson(HeroData.class, heroDataJSON);
        } catch (Exception exception) {
            System.out.println("Can't read the save file");
            resetData();
        }
    }

    /**
     * Saves current player data to the save file in JSON format.
     */
    public void saveData() {
        Json json = new Json(JsonWriter.OutputType.json);
        String heroJSON = json.toJson(hero, MyGdxGame.Heroes.class);
        String currentLevelJSON = json.toJson(currentLevel, MyGdxGame.Levels.class);
        String previousLevelJSON = json.toJson(previousLevel, MyGdxGame.Levels.class);
        String maxLevelJSON = json.toJson(maxLevel, MyGdxGame.Levels.class);
        String heroDataJSON = json.toJson(heroData, HeroData.class);
        try (FileWriter fileWriter = new FileWriter(SAVE_FILE)) {
            fileWriter.write(heroJSON);
            fileWriter.write("\n");
            fileWriter.write(currentLevelJSON);
            fileWriter.write("\n");
            fileWriter.write(previousLevelJSON);
            fileWriter.write("\n");
            fileWriter.write(maxLevelJSON);
            fileWriter.write("\n");
            fileWriter.write(heroDataJSON);
        } catch (IOException e) {
            System.out.println("Can't write the save file");
        }
    }

    /**
     * Resets player data to default values and deletes the save file.
     */
    public void resetData() {
        File saveFile = new File(SAVE_FILE);
        hero = MyGdxGame.Heroes.NOT_SELECTED;
        currentLevel = MyGdxGame.Levels.SAFE;
        previousLevel = MyGdxGame.Levels.SAFE;
        maxLevel = MyGdxGame.Levels.SAFE;
        heroData = HeroData.getDefault();
        saveFile.delete();
    }

    /**
     * Retrieves the current hero's data.
     *
     * @return The HeroData object representing the current hero's data.
     */
    public HeroData getHeroData() {
        return heroData;
    }

    /**
     * Sets the current hero's data.
     *
     * @param heroData The HeroData object containing the new hero's data.
     */
    public void setHeroData(HeroData heroData) {
        this.heroData = heroData;
    }

    /**
     * Retrieves the selected hero.
     *
     * @return The MyGdxGame.Heroes enum representing the selected hero.
     */
    public MyGdxGame.Heroes getHero() {
        return hero;
    }

    /**
     * Sets the selected hero.
     *
     * @param hero The MyGdxGame.Heroes enum representing the new selected hero.
     */
    public void setHero(MyGdxGame.Heroes hero) {
        this.hero = hero;
    }

    /**
     * Retrieves the current level the player is on.
     *
     * @return The MyGdxGame.Levels enum representing the current level.
     */
    public MyGdxGame.Levels getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level the player is on.
     *
     * @param level The MyGdxGame.Levels enum representing the new current level.
     */
    public void setCurrentLevel(MyGdxGame.Levels level) {
        if (level == currentLevel)
            return;
        this.previousLevel = currentLevel;
        this.currentLevel = level;
        if (currentLevel.compareTo(maxLevel) > 0)
            maxLevel = currentLevel;
    }

    /**
     * Retrieves the maximum level the player has achieved.
     *
     * @return The MyGdxGame.Levels enum representing the maximum level achieved.
     */
    public MyGdxGame.Levels getMaxLevel() {
        return maxLevel;
    }

    /**
     * Retrieves the previous level the player was on.
     *
     * @return The MyGdxGame.Levels enum representing the previous level.
     */
    public MyGdxGame.Levels getPreviousLevel() {
        return previousLevel;
    }
}
