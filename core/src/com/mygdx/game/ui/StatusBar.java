package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;


/**
 * Represents a status bar widget that extends LibGDX's ProgressBar.
 * It is used to display a value within a specified range, styled according to the provided style.
 */
public class StatusBar extends ProgressBar {

    /**
     * Constructs a StatusBar instance with the specified style.
     *
     * @param style The style name used to customize the appearance of the status bar.
     */
    public StatusBar(String style) {
        super(0, 100, 1, false, MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class), style);
    }

    /**
     * Sets the value of the status bar as a percentage of its maximum value.
     *
     * @param valuePercent The value as a percentage (0.0 to 1.0) to set the status bar.
     */
    public void setValuePercent(float valuePercent){
        setValue(100 * valuePercent);
    }
}
