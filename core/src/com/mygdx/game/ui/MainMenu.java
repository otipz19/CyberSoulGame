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
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;
import org.w3c.dom.Text;
/**
 * Represents the main menu screen of the game, providing options for starting a new game,
 * continuing from a saved state, accessing game settings, viewing credits, and exiting the game.
 */
public class MainMenu implements Screen {
    private final Stage stage;
    private final Table menuButtons;
    private final HeroSelectionUI heroSelectionUI;
    private final SettingsUI settingsUI;
    private final CreditsUI creditsUI;
    private final GuideUI guideUI;

    public MainMenu(boolean hasAlreadyPlayed) {
        stage = new Stage(new ScreenViewport());
        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(stage);

        Texture texture = MyGdxGame.getInstance().assetManager.get(Assets.Textures.UI_MENU_BACKGROUND, Texture.class);
        Drawable background = new TextureRegionDrawable(texture);
        stage.addActor(new Image(background));

        menuButtons = new Table();
        menuButtons.setFillParent(true);
        menuButtons.left().bottom();

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        if (hasAlreadyPlayed) {
            TextButton continueButton = getContinueButton(skin);

            menuButtons.add(continueButton)
                    .minWidth(200)
                    .growX()
                    .pad(5, 50, 5, 0);
            menuButtons.add().colspan(3).growX();
            menuButtons.row();
        }

        TextButton startButton = getStartButton(skin);
        TextButton guideBtn = getGuideButton(skin);
        TextButton optionsButton = getOptionsButton(skin);
        TextButton creditsButton = getCreditsButton(skin);
        TextButton exitButton = getExitButton(skin);

        menuButtons.add(startButton)
                .minWidth(200)
                .growX()
                .pad(5, 50, 5, 0);
        menuButtons.add()
                .colspan(3)
                .growX();
        menuButtons.row();
        menuButtons.add(guideBtn)
                .minWidth(200)
                .growX()
                .pad(5, 50, 5, 0);
        menuButtons.add()
                .colspan(3)
                .growX();
        menuButtons.row();
        menuButtons.add(optionsButton)
                .minWidth(200)
                .growX()
                .pad(5, 50, 5, 0);
        menuButtons.add()
                .colspan(3)
                .growX();
        menuButtons.row();
        menuButtons.add(creditsButton)
                .minWidth(200)
                .growX()
                .pad(5, 50, 5, 0);
        menuButtons.add()
                .colspan(3)
                .growX();
        menuButtons.row();
        menuButtons.add(exitButton)
                .minWidth(200)
                .growX()
                .pad(5, 50, 50, 0);
        menuButtons.add()
                .colspan(3)
                .growX();

        stage.addActor(menuButtons);

        heroSelectionUI = new HeroSelectionUI(stage);

        settingsUI = new SettingsUI(stage);
        settingsUI.addOnHideAction(() -> stage.addActor(menuButtons));

        creditsUI = new CreditsUI(stage);
        creditsUI.addOnHideAction(() -> { stage.addActor(menuButtons); SoundPlayer.getInstance().setBackgroundMusic(Assets.Music.MENU_MUSIC);});

        guideUI = new GuideUI(stage);
        guideUI.addOnHideAction(() -> stage.addActor(menuButtons));

        SoundPlayer.getInstance().setBackgroundMusic(Assets.Music.MENU_MUSIC);
    }

    private TextButton getContinueButton(Skin skin) {
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                MyGdxGame.getInstance().restartCurrentLevel();
            }
        });
        return continueButton;
    }

    private TextButton getStartButton(Skin skin) {
        TextButton startButton = new TextButton("New game", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                PlayerDataManager.getInstance().resetData();
                menuButtons.remove();
                stage.addActor(heroSelectionUI);
            }
        });
        return startButton;
    }

    private TextButton getGuideButton(Skin skin) {
        TextButton guideBtn = new TextButton("How to Play", skin);
        guideBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addActor(guideUI);
            }
        });
        return guideBtn;
    }

    private TextButton getOptionsButton(Skin skin) {
        TextButton optionsButton = new TextButton("Settings", skin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                menuButtons.remove();
                stage.addActor(settingsUI);
            }
        });
        return optionsButton;
    }

    private TextButton getCreditsButton(Skin skin) {
        TextButton creditsButton = new TextButton("Credits", skin);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                menuButtons.remove();
                SoundPlayer.getInstance().setBackgroundMusic(Assets.Music.CREDIT_MUSIC);
                creditsUI.registerAsInputProcessor();
                creditsUI.reset();
                stage.addActor(creditsUI);
            }
        });
        return creditsButton;
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
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        SoundPlayer.getInstance().update();
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
