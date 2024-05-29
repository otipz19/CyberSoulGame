package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UIConstants {
    public static final BitmapFont FONT = new BitmapFont();
    public static final Color BACKGROUND = new Color(0.5f, 0.5f, 0.5f, 0.6f);
    public static final TextureRegionDrawable BASE_TEXTURE;

    static {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        BASE_TEXTURE = new TextureRegionDrawable(new Texture(pixmap));
    }

}
