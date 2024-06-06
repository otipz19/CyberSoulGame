package com.mygdx.game.animation.concrete.portals;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.animation.base.AnimationBuilder;
import com.mygdx.game.animation.base.AnimationsMap;
import com.mygdx.game.animation.base.Animator;

public abstract class PortalAnimator extends Animator {
    public enum State implements Animator.State {
        INACTIVE,
        ACTIVATING,
        INACTIVATING
    }

    @Override
    protected AnimationsMap createAnimationsMap() {
        AnimationsMap map = new AnimationsMap();
        map.put(State.INACTIVE, new AnimationBuilder(getInactiveSheetName())
                .cols(1)
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .build());
        map.put(State.ACTIVATING, new AnimationBuilder(getActivatingSheetName())
                .cols(getCols())
                .rows(1)
                .playMode(Animation.PlayMode.NORMAL)
                .blocked()
                .build());
        map.put(State.INACTIVATING, new AnimationBuilder(getActivatingSheetName())
                .cols(getCols())
                .rows(1)
                .playMode(Animation.PlayMode.REVERSED)
                .blocked()
                .build());
        map.setStartState(State.INACTIVE);
        return map;
    }

    protected abstract int getCols();

    protected abstract String getInactiveSheetName();

    protected abstract String getActivatingSheetName();
}