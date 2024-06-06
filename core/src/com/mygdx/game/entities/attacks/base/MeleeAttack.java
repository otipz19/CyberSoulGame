package com.mygdx.game.entities.attacks.base;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.ICollisionListener;

interface MeleeAttack extends Attack {
     float getAttackWidth();
     float getAttackHeight();
}
