package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.levels.Level;
import com.mygdx.game.ui.upgrades.UpgradeUI;
import com.mygdx.game.utils.PlayerDataManager;
/**
 * Represents the user interface for displaying and managing the in-game HUD and interactions during gameplay.
 * This includes status bars for health, shield, and energy, a counter for collected souls, and functionality
 * to manage pausing, upgrades, and displaying death UI.
 */
public class LevelUI extends Stage {
    private final Level level;
    private StatusBar healthBar;
    private StatusBar shieldBar;
    private StatusBar energyBar;
    private Counter soulsCounter;
    private final PauseUI pauseUI;
    private UpgradeUI upgradeUI;

    public LevelUI(Level level) {
        this.level = level;
        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(this);

        setViewport(new ScreenViewport());

        createStatusUI();
        pauseUI = new PauseUI(this, level);
    }

    private void createStatusUI() {
        healthBar = new StatusBar("health");
        shieldBar = new StatusBar("shield");
        energyBar = new StatusBar("energy");
        soulsCounter = new Counter("soul-icon", 40);

        Table status = new Table();
        status.setFillParent(true);

        status.left().top();
        status.add(healthBar)
                .left()
                .width(Value.percentWidth(0.3f, status))
                .pad(5, 5, 0, 0);
        status.add().expandX();
        status.add(soulsCounter);
        status.row();
        status.add(shieldBar)
                .left()
                .width(Value.percentWidth(0.3f, status))
                .pad(5, 5, 0, 0);
        status.row();
        status.add(energyBar)
                .left()
                .width(Value.percentWidth(0.3f, status))
                .pad(5, 5, 0, 0);

        addActor(status);
    }

    public void updateStatistics(float hpPercent, float shieldPercent, float energyPercent, int souls) {
        healthBar.setValuePercent(hpPercent);
        shieldBar.setValuePercent(shieldPercent);
        energyBar.setValuePercent(energyPercent);
        soulsCounter.setValue(souls);
    }

    public void showUpgradeUI(Level level) {
        if (upgradeUI != null)
            return;

        level.setPaused(true);
        blockPausing();
        upgradeUI = new UpgradeUI(this, level);
        this.addActor(upgradeUI);
    }

    public void hideUpgradeUI() {
        if (upgradeUI == null)
            return;

        upgradeUI.hideLayer();
        upgradeUI = null;
        unblockPausing();
        level.setPaused(false);
    }

    public void showDeathUI() {
        addActor(new DeathUI(this));
    }

    public void blockPausing() {
        pauseUI.unregisterAsInputProcessor();
    }

    public void unblockPausing() {
        pauseUI.registerAsInputProcessor();
    }
}
