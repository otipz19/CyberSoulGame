package com.mygdx.game.animation.concrete.portals;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;

/**
 * Abstract animator class specifically for portals.
 * Extends Animator and defines states such as INACTIVE, ACTIVATING, and INACTIVATING.
 */
public abstract class PortalAnimator extends Animator {

    /**
     * Enumeration of possible states for the portal animator.
     */
    public enum State implements Animator.State {
        INACTIVE,
        ACTIVATING,
        INACTIVATING
    }

    /**
     * Creates and initializes a map of animations for the portal.
     *
     * @return AnimationsMap containing animations for different states of the portal.
     */
    @Override
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap map = new AnimationsMap();

        // Inactive state animation
        map.put(State.INACTIVE, new AnimationBuilder(getInactiveSheetName())
                .cols(1)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());

        // Activating state animation
        map.put(State.ACTIVATING, new AnimationBuilder(getActivatingSheetName())
                .cols(getCols())
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .build());

        // Inactivating state animation (reverse of activating)
        map.put(State.INACTIVATING, new AnimationBuilder(getActivatingSheetName())
                .cols(getCols())
                .rows(1)
                .playMode(Animation.PlayMode.REVERSED)
                .blocked()
                .build());

        // Set starting animation
        map.setStartState(State.INACTIVE);

        return map;
    }

    /**
     * Abstract method to be implemented by subclasses to provide the number of columns
     * in the sprite sheet for the portal.
     *
     * @return Number of columns in the sprite sheet.
     */
    protected abstract int getCols();

    /**
     * Abstract method to be implemented by subclasses to provide the path to the sprite sheet
     * for the inactive state of the portal.
     *
     * @return Path to the sprite sheet for the inactive state.
     */
    protected abstract String getInactiveSheetName();

    /**
     * Abstract method to be implemented by subclasses to provide the path to the sprite sheet
     * for the activating state of the portal.
     *
     * @return Path to the sprite sheet for the activating state.
     */
    protected abstract String getActivatingSheetName();
}
