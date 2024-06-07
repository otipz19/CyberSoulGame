package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;

public class Counter extends Table {
    private final Label label;

    public Counter(String icon, float iconSize) {
        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        label = new Label(" 0 ", skin);
        label.setAlignment(Align.right);
        add(label);

        add(new Image(skin.getDrawable(icon)))
                .size(iconSize)
                .pad(5, 0,0,0)
                .right();
    }

    public void setValue(int value){
        label.setText(" " + value + " ");
    }
}
