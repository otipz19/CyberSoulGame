package com.mygdx.game.entities.enemies;

import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.enemies.CarEnemyAnimator;
import com.mygdx.game.levels.Level;
import com.mygdx.game.entities.attacks.concrete.EnemyAttack;
import com.mygdx.game.map.data.EnemyData;

public class CarEnemy extends Enemy{
    public CarEnemy(Level level, EnemyData enemyData, float width, float height) {
        super(level, enemyData, width, height);

        this.animator = new CarEnemyAnimator();
        animator.setDirection(movementController.isFacingRight() ? Animator.Direction.RIGHT : Animator.Direction.LEFT);

        this.attack = new EnemyAttack(this);
    }
}
