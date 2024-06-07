package com.mygdx.game.entities.enemies;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.CarEnemyAnimator;
import com.mygdx.game.animation.concrete.EnemyAnimator;
import com.mygdx.game.entities.attacks.EnemyAttack;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.EnemyData;

public class MonsterEnemy extends Enemy{
    public MonsterEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new CarEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.attack = new EnemyAttack(this);
    }
}
