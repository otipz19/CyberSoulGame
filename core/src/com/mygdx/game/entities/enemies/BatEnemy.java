package com.mygdx.game.entities.enemies;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.BatEnemyAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.entities.attacks.concrete.EnemyAttack;
import com.mygdx.game.map.data.EnemyData;

public class BatEnemy extends Enemy{

    public BatEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new BatEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.attack = new EnemyAttack(this);
    }
}
