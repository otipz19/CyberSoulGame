package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.utils.AssetsNames;

public class EntryObstacleAnimator extends Animator {
    public enum State implements Animator.State{
        CLOSED,
        CLOSING,
        OPENING,
    }

    public EntryObstacleAnimator() {
        super(createAnimationsMap());
    }

    private static AnimationsMap createAnimationsMap(){
        var animations = new AnimationsMap();
        animations.put(State.CLOSED, new AnimationBuilder(AssetsNames.ENTRY_OBSTACLE_CLOSED)
                .rows(1)
                .cols(1)
                .frameDuration(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.CLOSING, new AnimationBuilder(AssetsNames.ENTRY_OBSTACLE_CLOSING)
                .rows(1)
                .cols(5)
                .frameDuration(1 / 10f)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.OPENING, new AnimationBuilder(AssetsNames.ENTRY_OBSTACLE_OPENING)
                .rows(1)
                .cols(5)
                .frameDuration(1 / 10f)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.startAnimation = animations.get(State.CLOSED);
        return animations;
    }
}
