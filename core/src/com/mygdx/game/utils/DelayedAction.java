package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;

public class DelayedAction {
    private final float delayInSeconds;
    private final Runnable action;

    public DelayedAction(float delay, Runnable action) {
        this.delayInSeconds = delay;
        this.action = action;
        new Thread(this::delay).start();
    }

    private void delay() {
        try {
            int oldScreenId = MyGdxGame.getInstance().getScreenId();
            long delayInMilliseconds = (long)(delayInSeconds * 1000);
            Thread.sleep(delayInMilliseconds);
            if (MyGdxGame.getInstance().getScreenId() == oldScreenId)
                Gdx.app.postRunnable(action);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
