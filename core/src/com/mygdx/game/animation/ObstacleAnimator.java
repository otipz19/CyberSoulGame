package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.utils.AssetsNames;

public class ObstacleAnimator extends Animator {
    public enum State implements Animator.State{
        DEFAULT,
    }

    public ObstacleAnimator() {
        super(createAnimationsMap());
    }

    private static AnimationsMap createAnimationsMap(){
        var animations = new AnimationsMap();
        animations.put(State.DEFAULT, new AnimationBuilder(AssetsNames.ENTRY_OBSTACLE)
                .rows(1)
                .cols(8)
                .frameDuration(1 / 10f)
                .playMode(Animation.PlayMode.LOOP)
                .build());
        animations.startAnimation = animations.get(State.DEFAULT);
        return animations;
    }
}
