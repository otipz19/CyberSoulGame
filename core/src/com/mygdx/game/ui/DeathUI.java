package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;

public class DeathUI extends UILayer {
    private boolean canLeave;

    public DeathUI(Stage stage) {
        super(stage);

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN, Skin.class);

        setSkin(skin);
        setFillParent(true);
        setBackground("background");

        Label loseLabel = new Label("You died", skin, "end");
        loseLabel.setAlignment(Align.center);
        loseLabel.setVisible(false);
        loseLabel.addAction(
                Actions.sequence(
                        Actions.alpha(0),
                        Actions.run(() -> loseLabel.setVisible(true)),
                        Actions.fadeIn(0.5f),
                        Actions.delay(0.5f),
                        Actions.run(() -> canLeave = true)
                )
        );

        Label clickLabel = new Label("Click to continue...", skin, "end");
        clickLabel.setAlignment(Align.center);
        clickLabel.setVisible(false);
        clickLabel.addAction(
                Actions.sequence(
                        Actions.alpha(0),
                        Actions.run(() -> clickLabel.setVisible(true)),
                        Actions.forever(
                                Actions.sequence(
                                        Actions.fadeIn(1f),
                                        Actions.delay(0.5f),
                                        Actions.fadeOut(1f)
                                )
                        )
                )
        );

        add(loseLabel)
                .growX()
                .pad(0, 0, 15, 0);
        row();
        add(clickLabel)
                .growX()
                .pad(15, 0, 0, 0);

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (canLeave)
                    MyGdxGame.getInstance().levelFailed();
            }
        });
    }
}
