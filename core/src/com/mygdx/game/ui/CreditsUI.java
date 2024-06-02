package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.AssetsNames;

public class CreditsUI extends UILayer {

    public static final String CREDITS =
            """
            
            Physics
            Mykola Prybysh
            
            
            Assets
            Oleksandr Tkachenko & Mykola Prybysh
            
            
            Animations
            Oleksandr Tkachenko
            
            
            Level design
            Oleksandr Tkachenko
            
            
            Enemy mechanics
            Stanislav Kylakevych
            
            
            Enemies AI
            Stanislav Kylakevych
            
            
            Player movement
            Stanislav Kylakevych & Mykola Prybysh
            
            
            User interface
            Mykola Prybysh
            
            
            Sound design
            ???
            
            
            Effects
            ???
            
            
            Special thanks
            Craftpix.net & Playground.com
            
            
            2024 CyberSoul team
            All rights reserved
            """;
    private final ScrollPane scrollPane;
    private boolean hasAlreadyScrolled;
    private float scrollingDelay;

    public CreditsUI(Stage stage) {
        super(stage);

        Skin skin = MyGdxGame.getInstance().assetManager.get(AssetsNames.UI_SKIN, Skin.class);

        setSkin(skin);
        setFillParent(true);
        setBackground("background");

        Label label = new Label(CREDITS, skin, "credits");
        label.setAlignment(Align.center);
        scrollPane = new ScrollPane(label, skin, "credits");

        add(scrollPane).grow();

        scrollingDelay = 0.2f;
    }

    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            unregisterAsInputProcessor();
            hideLayer();
        }
        return false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        scrollingDelay = Math.max(0, scrollingDelay - delta);
        if (hasAlreadyScrolled || scrollPane.isPanning() || scrollPane.getScrollY() < 0 || scrollingDelay > 0)
            return;

        final float scrollingSpeed = 1;
        if (scrollPane.getVisualScrollPercentY() < 1)
            scrollPane.setScrollY(scrollPane.getScrollY() + scrollingSpeed);
        else
            hasAlreadyScrolled = true;
    }

    public void reset() {
        scrollPane.setScrollY(0);
        scrollPane.updateVisualScroll();
        scrollingDelay = 0.2f;
        this.hasAlreadyScrolled = false;
    }
}
