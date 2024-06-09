package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;

public abstract class UpgradeBar {
    protected float currentValue;
    protected float step;
    protected int price;
    protected Label valueLabel;

    public UpgradeBar(float currentValue, float step, int price) {
        this.currentValue = currentValue;
        this.step = step;
        this.price = price;
    }

    public void createRow(Table table, String style) {
        if (valueLabel != null)
            return;

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        table.setSkin(skin);
        table.setFillParent(true);

        Image icon = new Image(skin, style + "-icon");

        valueLabel = new Label(String.format("%.1f", currentValue), skin);
        valueLabel.setAlignment(Align.center);

        TextButton button = new TextButton(String.format("+%.1f", step), skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                upgrade();
                updateValue();
            }
        });

        Label priceLabel = new Label(String.format(" %d ", price), skin );
        priceLabel.setAlignment(Align.center);

        Image soulIcon = new Image(skin,  "soul-icon");

        table.add(icon)
                .size(50)
                .pad(10, 10, 10, 10);
        table.add(valueLabel)
                .pad(0, 0, 0, 25)
                .growX();
        table.add(soulIcon)
                .size(50)
                .pad(10, 10, 10, 0);
        table.add(priceLabel);
        table.add().expandX();
        table.add(button)
                .height(40)
                .growX()
                .pad(0, 0, 0, 10);;
        table.row();
    }

    public abstract void upgrade();

    public void updateValue() {
        valueLabel.setText(String.format("%.1f", currentValue));
    }

}