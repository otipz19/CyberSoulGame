package com.mygdx.game.ui;

import static com.mygdx.game.ui.UIConstants.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;


public class StatusBar extends Table {
    private final ProgressBar bar;

    public StatusBar(String icon, Color foregroundColor, float barHeight) {
        Image ico = new Image(new TextureRegionDrawable(MyGdxGame.getInstance().assetManager.get(icon, Texture.class)));
        add(ico).size(barHeight*1.5f).pad(5);

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = BASE_TEXTURE.tint(BACKGROUND);
        progressBarStyle.knob = BASE_TEXTURE.tint(foregroundColor);
        progressBarStyle.knobBefore = progressBarStyle.knob;
        progressBarStyle.background.setMinHeight(barHeight);
        progressBarStyle.knob.setMinHeight(barHeight);
        bar = new ProgressBar(0, 100, 1, false, progressBarStyle);
        add(bar).pad(0, 0, 0,5);
    }

    public void setValuePercent(float valuePercent){
        bar.setValue(100*valuePercent);
    }
}
