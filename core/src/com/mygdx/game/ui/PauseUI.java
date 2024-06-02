package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utils.AssetsNames;

public class PauseUI extends UILayer {
    private final Level level;
    private final SettingsUI settingsUI;

    public PauseUI(Stage stage, Level level) {
        super(stage);
        this.level = level;

        Skin skin = MyGdxGame.getInstance().assetManager.get(AssetsNames.UI_SKIN, Skin.class);
        registerAsInputProcessor();

        setSkin(skin);
        setFillParent(true);
        setBackground("background-menu");

        TextButton resumeButton = getResumeButton(skin);
        TextButton restartButton = getRestartButton(skin);
        TextButton mainMenuButton = getMainMenuButton(skin);
        TextButton optionsButton = getOptionsButton(skin);
        TextButton exitButton = getExitButton(skin);

        add().expandX();
        add(resumeButton).growX().pad(5);
        add().expandX();
        row();
        add().expandX();
        add(restartButton).growX().pad(5);
        add().expandX();
        row();
        add().expandX();
        add(optionsButton).growX().pad(5);
        add().expandX();
        row();
        add().expandX();
        add(mainMenuButton).growX().pad(5);
        add().expandX();
        row();
        add().expandX();
        add(exitButton).growX().pad(5);
        add().expandX();

        settingsUI = new SettingsUI(stage);
        settingsUI.addOnHideAction(() -> {registerAsInputProcessor(); stage.addActor(this);});
    }

    private TextButton getResumeButton(Skin skin) {
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                togglePause();
            }
        });
        return resumeButton;
    }

    private TextButton getRestartButton(Skin skin) {
        TextButton restartButton = new TextButton("Restart", skin);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().restartCurrentLevel();
            }
        });
        return restartButton;
    }

    private TextButton getMainMenuButton(Skin skin) {
        TextButton mainMenuButton = new TextButton("Main menu", skin);
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().showMainMenu();
            }
        });
        return mainMenuButton;
    }

    private TextButton getOptionsButton(Skin skin) {
        TextButton optionsButton = new TextButton("Settings", skin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PauseUI.super.hideLayer();
                unregisterAsInputProcessor();
                stage.addActor(settingsUI);
            }
        });
        return optionsButton;
    }

    private TextButton getExitButton(Skin skin) {
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().exit();
            }
        });
        return exitButton;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE)
            togglePause();
        return false;
    }

    private void togglePause(){
        if (level.isPaused())
            hideLayer();
        else
            stage.addActor(this);
        level.togglePause();
    }
}
