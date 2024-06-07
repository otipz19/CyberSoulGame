package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;


public class StatusBar extends ProgressBar {

    public StatusBar(String style) {
        super(0, 100, 1, false, MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class), style);
    }

    public void setValuePercent(float valuePercent){
        setValue(100*valuePercent);
    }
}
