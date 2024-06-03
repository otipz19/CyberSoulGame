package com.mygdx.game.entities.heroes;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.sensors.AttackZone;
import com.mygdx.game.entities.sensors.AttackZonePosition;
import com.mygdx.game.utils.DelayedAction;

 interface HeroAttack extends ICollisionListener, Disposable {
     void execute();
     float getAttackTime();
     float getAttackDelay();
     float getAttackWidth();
     float getAttackHeight();
}
