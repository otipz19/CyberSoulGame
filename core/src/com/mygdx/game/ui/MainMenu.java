package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.PlayerDataManager;

public class MainMenu implements Screen {
    private final Stage ui;

    public MainMenu(boolean hasAlreadyPlayed) {
        ui = new Stage(new ScreenViewport());
        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(ui);

        Texture texture = MyGdxGame.getInstance().assetManager.get(AssetsNames.UI_MENU_BACKGROUND, Texture.class);
        Drawable background = new TextureRegionDrawable(texture);
        ui.addActor(new Image(background));

        Table table = new Table();
        table.setFillParent(true);
        table.left().bottom();

        Skin skin = MyGdxGame.getInstance().assetManager.get(AssetsNames.UI_SKIN, Skin.class);

        if (hasAlreadyPlayed) {
            TextButton continueButton = new TextButton("Continue", skin);
            continueButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    MyGdxGame.getInstance().restartCurrentLevel();
                }
            });

            table.add(continueButton).minWidth(200).growX().pad(5, 50, 5, 0);
            table.add().colspan(3).growX();
            table.row();
        }


        TextButton startButton = new TextButton("New game", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PlayerDataManager.getInstance().resetData();
                MyGdxGame.getInstance().changeLevel(MyGdxGame.Levels.SAFE);
            }
        });

        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                throw new RuntimeException("Not implemented :(");
            }
        });

        TextButton creditsButton = new TextButton("Credits", skin);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                throw new RuntimeException("Not implemented :(");
            }
        });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().exit();
            }
        });

        table.add(startButton).minWidth(200).growX().pad(5, 50, 5, 0);
        table.add().colspan(3).growX();
        table.row();
        table.add(optionsButton).minWidth(200).growX().pad(5, 50, 5, 0);
        table.add().colspan(3).growX();
        table.row();
        table.add(creditsButton).minWidth(200).growX().pad(5, 50, 5, 0);
        table.add().colspan(3).growX();
        table.row();
        table.add(exitButton).minWidth(200).growX().pad(5, 50, 50, 0);
        table.add().colspan(3).growX();

        ui.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        ui.act(deltaTime);
        ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        ui.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ui.dispose();
    }
}
