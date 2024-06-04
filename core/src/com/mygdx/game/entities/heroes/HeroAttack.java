package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.ICollisionListener;

 interface HeroAttack extends ICollisionListener, Disposable {
     void execute();
     float getAttackTime();
     float getAttackDelay();
     float getAttackWidth();
     float getAttackHeight();
}
