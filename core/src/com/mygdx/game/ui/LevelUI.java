package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.utils.AssetsNames;

public class LevelUI extends Stage {
    private StatusBar healthBar;
    private StatusBar shieldBar;
    private Counter soulsCounter;
    public LevelUI() {
        setViewport(new ScreenViewport());

        healthBar = new StatusBar(AssetsNames.UI_HEART, new Color(1f, 0.15f, 0.45f, 0.7f), 25);
        shieldBar = new StatusBar(AssetsNames.UI_SHIELD, new Color(0.35f, 0.2f, 0.85f, 0.7f), 25);
        soulsCounter = new Counter(AssetsNames.UI_SOUL, new Color(1,1,1,1), 25);

        Table table = new Table();
        table.setFillParent(true);
        addActor(table);

        table.left().top();
        table.add(healthBar).left();
        table.add().expandX();
        table.add(soulsCounter);
        table.row();
        table.add(shieldBar).left();
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    public void updateStatistics(float hpPercent, float shieldPercent, int souls) {
        healthBar.setValuePercent(hpPercent);
        shieldBar.setValuePercent(shieldPercent);
        soulsCounter.setValue(souls);
    }
}
