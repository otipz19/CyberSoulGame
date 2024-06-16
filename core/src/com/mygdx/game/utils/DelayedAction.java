package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;

/**
 * Represents a delayed action that will execute after a specified delay period.
 */
public class DelayedAction {
    private final float delayInSeconds;
    private final Runnable action;

    /**
     * Constructs a DelayedAction object with the specified delay and action to execute.
     *
     * @param delay   The delay time in seconds before executing the action.
     * @param action  The action to execute after the delay.
     */
    public DelayedAction(float delay, Runnable action) {
        this.delayInSeconds = delay;
        this.action = action;
        new Thread(this::delay).start();
    }

    /**
     * Delays the execution of the action thread for the specified delay time.
     */
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
