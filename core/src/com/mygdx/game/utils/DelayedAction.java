package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;

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
            long delayInMilliseconds = (long)(delayInSeconds * 1000);
            Thread.sleep(delayInMilliseconds);
            Gdx.app.postRunnable(action);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
