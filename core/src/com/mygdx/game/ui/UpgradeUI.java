package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.heroes.HeroData;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;

public class UpgradeUI extends UILayer {
    private final LevelUI levelUI;

    public UpgradeUI(LevelUI levelUI, Level level) {
        super(levelUI);
        this.levelUI = levelUI;

        registerAsInputProcessor();

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        setSkin(skin);
        setFillParent(true);
        setBackground("background-menu");

        HeroData heroData = PlayerDataManager.getInstance().getHeroData();

        HeroResourcesManager resourcesManager = level.hero.getResourcesManager();
        UpgradeBar healthUpgrade = new UpgradeBar(heroData.maxHealth, 10, 25, resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setMaxHealth(currentValue);
                resourcesManager.increaseHealth(resourcesManager.getMaxHealth());
            }
        };

        UpgradeBar shieldUpgrade = new UpgradeBar(heroData.maxShield, 25, 10, resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setMaxShield(currentValue);
                resourcesManager.setShield(resourcesManager.getMaxShield());
            }
        };

        UpgradeBar energyUpgrade = new UpgradeBar(heroData.maxEnergy, 10, 15, resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setMaxEnergy(currentValue);
                resourcesManager.increaseEnergy(resourcesManager.getMaxEnergy());
            }
        };

        UpgradeBar damageUpgrade = new UpgradeBar(1, 0.1f, 20, resourcesManager) {
            @Override
            public void upgrade() {
                resourcesManager.setDamageMultiplier(currentValue);
            }
        };

        TextButton saveButton = new TextButton("Confirm", skin);
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelUI.hideUpgradeUI();
            }
        });

        TextButton resetButton = new TextButton("Reset", skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelUI.hideUpgradeUI();
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
