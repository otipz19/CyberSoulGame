package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.PlayerPreferencesManager;

public class SettingsUI extends UILayer {
    public SettingsUI(Stage stage) {
        super(stage);

        Skin skin = MyGdxGame.getInstance().assetManager.get(AssetsNames.UI_SKIN, Skin.class);

        setSkin(skin);
        setFillParent(true);
        setBackground("background-menu");

        Label screenLabel = new Label("Full screen", skin);
        screenLabel.setAlignment(Align.center);
        CheckBox screenCheckbox = getScreenCheckbox(skin);

        Label musicLabel = new Label("Music volume", skin);
        musicLabel.setAlignment(Align.center);
        Slider musicSlider = getMusicSlider(skin);

        Label soundLabel = new Label("Sound volume", skin);
        soundLabel.setAlignment(Align.center);
        Slider soundSlider = getSoundSlider(skin);

        TextButton closeButton = getCloseButton(skin);

        add(screenLabel)
                .growX()
                .minWidth(200)
                .pad(5, 20, 0, 10);
        add(screenCheckbox)
                .colspan(2)
                .pad(5, 10, 0, 20);
        row();
        add(musicLabel)
                .growX()
                .minWidth(200)
                .pad(5, 20, 0, 10);
        add(musicSlider)
                .colspan(2)
                .growX()
                .pad(5, 10, 0, 20);
        row();
        add(soundLabel)
                .growX()
                .minWidth(200)
                .pad(5, 20, 0, 10);
        add(soundSlider)
                .colspan(2)
                .growX()
                .pad(5, 10, 0, 20);
        row();
        add(closeButton)
                .colspan(3)
                .growX()
                .pad(5, 20, 5, 20);
    }

    private CheckBox getScreenCheckbox(Skin skin) {
        CheckBox screenCheckbox = new CheckBox("", skin, "preferences");
        screenCheckbox.setChecked(PlayerPreferencesManager.getInstance().isFullScreen());
        screenCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PlayerPreferencesManager.getInstance().setFullScreen(screenCheckbox.isChecked());
                MyGdxGame.getInstance().changeDisplayMode();
            }
        });
        return screenCheckbox;
    }

    private Slider getMusicSlider(Skin skin){
        Slider musicSlider = new Slider(0, 1, 0.01f, false, skin, "preferences");
        musicSlider.setValue(PlayerPreferencesManager.getInstance().getMusicVolume());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                float musicVolume = musicSlider.getValue();
                PlayerPreferencesManager.getInstance().setMusicVolume(musicVolume);
            }
        });
        return musicSlider;
    }

    private Slider getSoundSlider(Skin skin){
        Slider soundSlider = new Slider(0, 1, 0.01f, false, skin, "preferences");
        soundSlider.setValue(PlayerPreferencesManager.getInstance().getSoundVolume());
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                float soundVolume = soundSlider.getValue();
                PlayerPreferencesManager.getInstance().setSoundVolume(soundVolume);
            }
        });
        return soundSlider;
    }

    private TextButton getCloseButton(Skin skin) {
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                hideLayer();
            }
        });
        return closeButton;
    }
}
