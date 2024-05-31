package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.AssetsNames;
import com.mygdx.game.utils.PlayerDataManager;

import static com.mygdx.game.ui.UIConstants.BASE_TEXTURE;

public class MainMenu implements Screen { private Stage ui;

    public MainMenu(boolean hasAlreadyPlayed) {
        ui = new Stage(new ScreenViewport());
        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(ui);

        Texture texture = MyGdxGame.getInstance().assetManager.get(AssetsNames.UI_MENU_BACKGROUND, Texture.class);
        Drawable background = new TextureRegionDrawable(texture);
        ui.addActor(new Image(background));

        Table table = new Table();
        table.setFillParent(true);
        table.left().bottom();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = UIConstants.FONT;
        buttonStyle.up = BASE_TEXTURE.tint(new Color(0.2f, 0.2f, 0.2f, 0.9f));
        buttonStyle.over = BASE_TEXTURE.tint(new Color(0.1f, 0.1f, 0.1f, 0.9f));

        if (hasAlreadyPlayed) {
            TextButton continueButton = new TextButton("Continue", buttonStyle);
            continueButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    MyGdxGame.getInstance().restartCurrentLevel();
                }
            });

            table.add(continueButton).width(Value.percentWidth(0.33f, table)).pad(5, 50, 5, 0);
            table.row();
        }


        TextButton startButton = new TextButton("New game", buttonStyle);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PlayerDataManager.getInstance().resetData();
                MyGdxGame.getInstance().changeLevel(MyGdxGame.Levels.SAFE);
            }
        });

        TextButton optionsButton = new TextButton("Options", buttonStyle);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                throw new RuntimeException("Not implemented :(");
            }
        });

        TextButton creditsButton = new TextButton("Credits", buttonStyle);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                throw new RuntimeException("Not implemented :(");
            }
        });

        TextButton exitButton = new TextButton("Exit", buttonStyle);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().exit();
            }
        });

        table.add(startButton).width(Value.percentWidth(0.33f, table)).pad(5, 50, 5, 0);
        table.row();
        table.add(optionsButton).width(Value.percentWidth(0.33f, table)).pad(5, 50, 5, 0);
        table.row();
        table.add(creditsButton).width(Value.percentWidth(0.33f, table)).pad(5, 50, 5, 0);
        table.row();
        table.add(exitButton).width(Value.percentWidth(0.33f, table)).pad(5, 50, 50, 0);

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
