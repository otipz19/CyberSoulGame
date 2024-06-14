package com.mygdx.game.ui.upgrades;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.heroes.HeroData;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.levels.Level;
import com.mygdx.game.ui.LevelUI;
import com.mygdx.game.ui.UILayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;

/**
 * Represents the user interface for upgrading hero attributes.
 * Allows the player to upgrade health, shield, energy, and damage attributes of the hero.
 */
public class UpgradeUI extends UILayer {

    /**
     * Constructs an UpgradeUI instance.
     *
     * @param levelUI The LevelUI instance associated with the current level.
     * @param level   The Level instance representing the current game level.
     */
    public UpgradeUI(LevelUI levelUI, Level level) {
        super(levelUI);

        registerAsInputProcessor();

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        setSkin(skin);
        setFillParent(true);
        setBackground("background-menu");

        HeroResourcesManager resourcesManager = level.hero.getResourcesManager();
        HeroData defaultData = HeroData.getDefault();

        // Initialize upgrade bars for health, shield, energy, and damage
        UpgradeBar healthUpgrade = new UpgradeBar(defaultData.maxHealth,
                resourcesManager.getMaxHealth(),
                10,
                1,
                resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setMaxHealth(currentValue);
                resourcesManager.increaseHealth(resourcesManager.getMaxHealth());
            }

            @Override
            public void revert() {
                resourcesManager.setMaxHealth(currentValue);
            }
        };

        UpgradeBar shieldUpgrade = new UpgradeBar(defaultData.maxShield,
                resourcesManager.getMaxShield(),
                25,
                1,
                resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setMaxShield(currentValue);
                resourcesManager.setShield(resourcesManager.getMaxShield());
            }

            @Override
            public void revert() {
                resourcesManager.setMaxShield(currentValue);
            }
        };

        UpgradeBar energyUpgrade = new UpgradeBar(defaultData.maxEnergy,
                resourcesManager.getMaxEnergy(),
                10,
                1,
                resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setMaxEnergy(currentValue);
                resourcesManager.increaseEnergy(resourcesManager.getMaxEnergy());
            }

            @Override
            public void revert() {
                resourcesManager.setMaxEnergy(currentValue);
            }
        };

        UpgradeBar damageUpgrade = new UpgradeBar(defaultData.damageMultiplier,
                resourcesManager.getDamageMultiplier(),
                0.1f,
                1,
                resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setDamageMultiplier(currentValue);
            }

            @Override
            public void revert() {
                resourcesManager.setDamageMultiplier(currentValue);
            }
        };

        // Button to confirm and save upgrades
        TextButton saveButton = new TextButton("Confirm", skin);
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PlayerDataManager.getInstance().setHeroData(resourcesManager.getHeroData());
                levelUI.hideUpgradeUI();
            }
        });

        // Button to reset all upgrades
        TextButton resetButton = new TextButton("Reset", skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                healthUpgrade.reset();
                shieldUpgrade.reset();
                energyUpgrade.reset();
                damageUpgrade.reset();
            }
        });

        healthUpgrade.createRow(this, "health");
        shieldUpgrade.createRow(this, "shield");
        energyUpgrade.createRow(this, "energy");
        damageUpgrade.createRow(this, "damage");

        add(saveButton)
                .colspan(3)
                .width(Value.percentWidth(0.49f, this))
                .minWidth(300)
                .pad(0, 10, 0, 5);
        add(resetButton)
                .colspan(3)
                .width(Value.percentWidth(0.49f, this))
                .minWidth(300)
                .pad(0, 5, 0, 10);
    }
}
