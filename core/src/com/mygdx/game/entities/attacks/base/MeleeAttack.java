package com.mygdx.game.entities.attacks.base;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.ICollisionListener;
/**
 * MeleeAttack is an interface representing a melee attack.
 * It extends the Attack interface and provides methods to retrieve the dimensions of the attack area.
 */
interface MeleeAttack extends Attack {
     float getAttackWidth();
     float getAttackHeight();
}
