package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class TextureData {
    public Texture texture;
    public float width;
    public float height;

    public TextureData(Texture texture, float width, float height){
        this.texture = texture;
        this.width = width;
        this.height = height;
    }
}
