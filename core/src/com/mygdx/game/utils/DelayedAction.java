package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;

/**
 * Represents a delayed action that will execute after a specified delay period.
 */
public class DelayedAction {
    private float delayInSeconds;
    private final Runnable action;
    private boolean hasRun;

    /**
     * Constructs a DelayedAction object with the specified delay and action to execute.
     *
     * @param delay   The delay time in seconds before executing the action.
     * @param action  The action to execute after the delay.
     */
    public DelayedAction(float delay, Runnable action) {
        this.delayInSeconds = delay;
        this.action = action;
    }

    /**
     * Updates state of the delayed action.
     *
     * @param deltaTime time that has passed since the last update
     */
    public void update(float deltaTime) {
        if (hasRun)
            return;

        delayInSeconds = Math.max(0, delayInSeconds - deltaTime);
        if (delayInSeconds == 0) {
            action.run();
            hasRun = true;
        }
    }

    /**
     * Reports state of the delayed action
     *
     * @return boolean value that indicates whether action has already been completed
     */
    public boolean hasRun() {
        return hasRun;
    }
}
