package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.AssetsNames;
import static com.mygdx.game.ui.UIConstants.*;

public class Counter extends Table {
    private final Label label;

    public Counter(String icon, Color textColor, float textHeight) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = BASE_TEXTURE.tint(new Color(0.5f, 0.5f, 0.5f, 0.5f));
        labelStyle.background.setMinHeight(textHeight*1.25f);
        labelStyle.font = FONT;
        label = new Label(" 0 ", labelStyle);
        label.setColor(textColor);
        label.setHeight(textHeight);
        label.setAlignment(Align.right);
        add(label).pad(0, 5, 0,0);

        add(new Image(new TextureRegionDrawable(MyGdxGame.getInstance().assetManager.get(icon, Texture.class))))
                .size(textHeight*1.5f)
                .pad(5)
                .right();
    }

    public void setValue(int value){
        label.setText(" " + value + " ");
    }
}
