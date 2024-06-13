package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Assets;

import java.util.Arrays;

public class GuideUI extends UILayer {
    private final Container<Image> imageContainer;
    private final Image[] images;
    private final Button leftBtn;
    private final Button rightBtn;

    private int curImage;

    public GuideUI(Stage stage) {
        super(stage);
        registerAsInputProcessor();

        Skin skin = MyGdxGame.getInstance().assetManager.get(Assets.Skins.UI_SKIN);
        setSkin(skin);

        setFillParent(true);
        setBackground("background-menu");


        Label title = new Label("HOW TO PLAY", skin);
        title.setAlignment(Align.center);

        this.images = loadImages();
        this.imageContainer = new Container<>(images[0]);

        leftBtn = buildLeftBtn();
        rightBtn = buildRightBtn();

        Button closeBtn = new TextButton("CLOSE", skin);
        closeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hideLayer();
            }
        });

        pad(25, 25, 25, 25);
        add();
        add(title)
                .minWidth(640);
        row();
        add(leftBtn)
                .maxSize(96, 96);
        add(imageContainer)
                .pad(25, 0, 25, 0)
                .minSize(640, 360);
        add(rightBtn)
                .maxSize(96, 96);
        row();
        add();
        add(closeBtn)
                .center()
                .minWidth(640);
    }

    private Button buildLeftBtn() {
        Texture texture = MyGdxGame.getInstance().assetManager.get(Assets.Textures.GUIDE_LEFT_ARROW);
        Button leftBtn = new ImageButton(new TextureRegionDrawable(texture));
        leftBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(curImage > 0) {
                    imageContainer.removeActor(images[curImage]);
                    imageContainer.setActor(images[--curImage]);
                    rightBtn.setDisabled(false);
                    if(curImage == 0) {
                        leftBtn.setDisabled(true);
                    }
                }
            }
        });
        return leftBtn;
    }

    private Button buildRightBtn() {
        Texture texture = MyGdxGame.getInstance().assetManager.get(Assets.Textures.GUIDE_RIGHT_ARROW);
        Button rightBtn = new ImageButton(new TextureRegionDrawable(texture));
        rightBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(curImage < images.length - 1) {
                    imageContainer.removeActor(images[curImage]);
                    imageContainer.setActor(images[++curImage]);
                    leftBtn.setDisabled(false);
                    if(curImage == images.length - 1) {
                        rightBtn.setDisabled(true);
                    }
                }
            }
        });
        return rightBtn;
    }

    private Image[] loadImages() {
        return Arrays.stream(new String[]{
                        Assets.Textures.GUIDE_RUN,
                        Assets.Textures.GUIDE_JUMP,
                        Assets.Textures.GUIDE_ATTACK
                })
                .map(name -> MyGdxGame.getInstance().assetManager.get(name, Texture.class))
                .map(texture -> new Image(texture))
                .toArray(Image[]::new);
    }
}
