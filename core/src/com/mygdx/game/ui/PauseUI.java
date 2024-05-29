package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.levels.Level;

import static com.mygdx.game.ui.UIConstants.BACKGROUND;
import static com.mygdx.game.ui.UIConstants.BASE_TEXTURE;

public class PauseUI extends Table implements InputProcessor {
    private Stage stage;
    private Level level;
    public PauseUI(Stage stage, Level level) {
        this.stage = stage;
        this.level = level;

        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(this);

        setFillParent(true);
        setBackground(BASE_TEXTURE.tint(BACKGROUND));

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = UIConstants.FONT;
        buttonStyle.up = BASE_TEXTURE.tint(new Color(0.2f, 0.2f, 0.2f, 0.9f));
        buttonStyle.over = BASE_TEXTURE.tint(new Color(0.1f, 0.1f, 0.1f, 0.9f));

        TextButton resumeButton = new TextButton("Resume", buttonStyle);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                togglePause();
            }
        });

        TextButton restartButton = new TextButton("Restart", buttonStyle);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().restartCurrentLevel();
            }
        });

        TextButton exitButton = new TextButton("Exit", buttonStyle);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().exit();
            }
        });

        add().expandX();
        add(resumeButton).growX().pad(5);
        add().expandX();
        row();
        add().expandX();
        add(restartButton).growX().pad(5);
        add().expandX();
        row();
        add().expandX();
        add(exitButton).growX().pad(5);
        add().expandX();
    }

    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE)
            togglePause();
        return false;
    }

    private void togglePause(){
        if (level.getPaused())
            this.remove();
        else
            stage.addActor(this);
        level.togglePause();
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
