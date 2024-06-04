package com.mygdx.game.entities.attacks;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.ICollisionListener;

 interface Attack extends ICollisionListener, Disposable {
     void execute();
     float getAttackTime();
     float getAttackDelay();
     float getAttackWidth();
     float getAttackHeight();
}
