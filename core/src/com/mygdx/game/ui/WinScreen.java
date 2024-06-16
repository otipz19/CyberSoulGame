package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
/**
 * Represents the screen displayed when the player wins the game. It shows a congratulatory message,
 * plays winning music, displays particle effects, and allows the player to return to the main menu
 * by clicking on the screen after a certain delay.
 */
public class WinScreen implements Screen {
    private final Stage stage;
    private final Table table;
    private final Array<ParticleEffectsUI> particleEffects;
    private boolean canLeave;

    /**
     * Constructs the WinScreen with necessary UI elements and initializes particle effects.
     */
    public WinScreen() {
        stage = new Stage(new ScreenViewport());
        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(stage);

        SoundPlayer.getInstance().setBackgroundMusic(Assets.Music.WINNER_MUSIC);

        Texture texture = MyGdxGame.getInstance().assetManager.get(Assets.Textures.UI_MENU_BACKGROUND, Texture.class);
        Drawable background = new TextureRegionDrawable(texture);
        stage.addActor(new Image(background));

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        table = new Table(skin);
        table.setFillParent(true);
        table.setBackground("background");

        Label winLabel = new Label("You win!", skin, "end");
        winLabel.setAlignment(Align.center);

        Label thanksLabel = new Label("Thanks for playing!", skin, "end");
        thanksLabel.setAlignment(Align.center);

        Label clickLabel = new Label("Click to continue...", skin, "end");
        clickLabel.setAlignment(Align.center);

        addLabel(winLabel, 0f, false);
        addLabel(thanksLabel, 1.25f, false);
        addLabel(clickLabel, 2.5f, true);
        clickLabel.addAction(Actions.delay(3f, Actions.run(() -> canLeave = true)));

        stage.addActor(table);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (canLeave)
                    MyGdxGame.getInstance().goToMainMenu();
            }
        });

        particleEffects = new Array<>();
        particleEffects.add(createFireworkEffect(0.15f, 0.35f));
        particleEffects.add(createFireworkEffect(0.85f, 0.65f));
    }

    /**
     * Adds a label to the table with specified appearance delay and blinking effect.
     *
     * @param label The label to add.
     * @param appearanceDelay The delay before the label appears.
     * @param blinking Whether the label should blink.
     */
    private void addLabel(Label label, float appearanceDelay, boolean blinking) {
        label.setVisible(false);
        label.addAction(
                Actions.sequence(
                        Actions.delay(appearanceDelay),
                        Actions.alpha(0),
                        Actions.run(() -> label.setVisible(true)),
                        Actions.fadeIn(0.5f),
                        !blinking ? Actions.run(() -> {}) :
                                Actions.forever(
                                        Actions.sequence(
                                                Actions.delay(0.5f),
                                                Actions.fadeOut(1.f),
                                                Actions.fadeIn(1.f)
                                        )
                                )
                )
        );
        table.add(label)
                .growX()
                .pad(25);
        table.row();
    }

    /**
     * Creates a firework particle effect at the specified screen position.
     *
     * @param offsetX The horizontal offset as a percentage of screen width.
     * @param offsetY The vertical offset as a percentage of screen height.
     * @return The ParticleEffectsUI instance representing the created firework effect.
     */
    private ParticleEffectsUI createFireworkEffect(float offsetX, float offsetY){
        return new ParticleEffectsUI(Assets.ParticleEffects.FIREWORK_PARTICLES, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), offsetX, offsetY);
    }

    @Override
    public void show() {
        // Not used in this implementation
    }

    @Override
    public void render(float deltaTime) {
        SoundPlayer.getInstance().update();
        stage.act(deltaTime);
        stage.draw();
        stage.getBatch().begin();
        for (ParticleEffectsUI effect : particleEffects)
            effect.draw(stage.getBatch(), deltaTime);
        stage.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        for (ParticleEffectsUI effect : particleEffects)
            effect.resize(width, height);
    }

    @Override
    public void pause() {
        // Not used in this implementation
    }

    @Override
    public void resume() {
        // Not used in this implementation
    }

    @Override
    public void hide() {
        // Not used in this implementation
    }

    @Override
    public void dispose() {
        stage.dispose();
        for (ParticleEffectsUI effects : particleEffects)
            effects.dispose();
    }
}
