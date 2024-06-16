package com.mygdx.game.entities.attacks.base;

/**
 * RangeAttack is an interface extending the Attack interface,
 * specifically for ranged attack actions in the game.
 * It introduces methods to retrieve projectile dimensions.
 */
interface RangeAttack extends Attack {

     /**
      * Retrieves the width of the projectile used in the range attack.
      *
      * @return The width of the projectile.
      */
     float getProjectileWidth();

     /**
      * Retrieves the height of the projectile used in the range attack.
      *
      * @return The height of the projectile.
      */
     float getProjectileHeight();
}
