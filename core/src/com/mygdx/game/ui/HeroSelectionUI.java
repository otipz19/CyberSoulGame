package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;
import com.mygdx.game.utils.PlayerPreferencesManager;
/**
 * Represents the user interface for selecting a hero character before starting the game.
 * Allows the player to choose between different hero options (e.g., biker, punk).
 */
public class HeroSelectionUI extends UILayer {
    public HeroSelectionUI(Stage stage) {
        super(stage);
        registerAsInputProcessor();

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        setSkin(skin);
        setFillParent(true);
        setBackground("background-menu");

        ImageButton bikerButton = new ImageButton(skin, "biker-selection");
        bikerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PlayerDataManager.getInstance().setHero(MyGdxGame.Heroes.BIKER);
                MyGdxGame.getInstance().goToNewLevel(MyGdxGame.Levels.SAFE);
            }
        });

        ImageButton punkButton = new ImageButton(skin, "punk-selection");
        punkButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PlayerDataManager.getInstance().setHero(MyGdxGame.Heroes.PUNK);
                MyGdxGame.getInstance().goToNewLevel(MyGdxGame.Levels.SAFE);
            }
        });

        add().growX();
        add(bikerButton)
                .minWidth(200)
                .minHeight(300)
                .pad(20);
        add(punkButton)
                .minWidth(200)
                .minHeight(300)
                .pad(20);
        add().growX();

    }
}
