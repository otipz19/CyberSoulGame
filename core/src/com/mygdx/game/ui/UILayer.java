package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public abstract class UILayer extends Table implements InputProcessor {
    protected final Stage stage;
    private final Array<Runnable> onHideActions;

    public UILayer(Stage stage){
        this.stage = stage;
        onHideActions = new Array<>();
        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(this);
    }

    public void addOnHideAction(Runnable action){
        onHideActions.add(action);
    }

    public void removeOnHideAction(Runnable action){
        onHideActions.removeValue(action, true);
    }

    public void clearOnHideActions(){
        onHideActions.clear();
    }

    public void hideLayer(){
        onHideActions.forEach(Runnable::run);
        remove();
    }

    public boolean keyDown(int keycode) {
        return false;
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
