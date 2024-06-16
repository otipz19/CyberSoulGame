package com.mygdx.game.animation.concrete.npc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.utils.Assets;

/**
 * Animator class specifically for a monk NPC.
 */
public class MonkAnimator extends Animator {

    /**
     * Enumeration of possible states for the monk animator.
     */
    public enum State implements Animator.State {
        IDLE,
        INTERACT
    }

    /**
     * Creates and initializes a map of animations for the monk NPC.
     *
     * @return AnimationsMap containing animations for different states of the monk NPC.
     */
    @Override
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap map = new AnimationsMap();

        map.put(State.IDLE, new AnimationBuilder(Assets.Textures.MONK_IDLE_SHEET)
                .cols(4)
                .rows(1)
                .frameDuration(1 / 5f)
                .playMode(Animation.PlayMode.LOOP_PINGPONG)
                .build());

        map.put(State.INTERACT, new AnimationBuilder(Assets.Textures.MONK_INTERACT_SHEET)
                .cols(6)
                .rows(1)
                .frameDuration(1 / 12f)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked(State.IDLE)
                .build());

        map.setStartState(State.IDLE);

        return map;
    }
}
