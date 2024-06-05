package com.mygdx.game.animation.concrete.obstacles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.utils.AssetsNames;

public abstract class GateObstacleAnimator extends Animator {
    public enum State implements Animator.State{
        CLOSED,
        CLOSING,
        OPENING,
    }

    @Override
    protected AnimationsMap createAnimationsMap(){
        var animations = new AnimationsMap();
        animations.put(State.CLOSED, new AnimationBuilder(getClosedStateSheet())
                .rows(1)
                .cols(1)
                .frameDuration(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.CLOSING, new AnimationBuilder(getClosingStateSheet())
                .rows(1)
                .cols(5)
                .frameDuration(1 / 10f)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.put(State.OPENING, new AnimationBuilder(getOpeningStateSheet())
                .rows(1)
                .cols(5)
                .frameDuration(1 / 10f)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        animations.startAnimation = animations.get(State.CLOSED);
        return animations;
    }

    protected abstract String getClosedStateSheet();
    protected abstract String getClosingStateSheet();
    protected abstract String getOpeningStateSheet();
}
