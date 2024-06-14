package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;

/**
 * Represents a UI component for displaying a counter with an icon.
 * The counter displays a numeric value alongside an icon image.
 */
public class Counter extends Table {

    private final Label label;

    /**
     * Constructs a Counter instance with a specified icon and size.
     *
     * @param icon     The name of the icon to display.
     * @param iconSize The size of the icon.
     */
    public Counter(String icon, float iconSize) {
        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        // Initialize the label for displaying the counter value
        label = new Label(" 0 ", skin);
        label.setAlignment(Align.right);
        add(label);

        // Add the icon image to the table
        add(new Image(skin.getDrawable(icon)))
                .size(iconSize)
                .pad(5, 0, 0, 0)
                .right();
    }

    /**
     * Sets the value to be displayed in the counter.
     *
     * @param value The numeric value to display.
     */
    public void setValue(int value) {
        label.setText(" " + value + " ");
    }
}
