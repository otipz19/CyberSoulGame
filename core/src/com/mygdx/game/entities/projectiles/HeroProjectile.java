package com.mygdx.game.entities.projectiles;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;

public abstract class HeroProjectile extends Projectile {
    private float damage;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public HeroProjectile(Entity owner, float x, float y, float width, float height, float initialSpeed, float initialAngle, boolean isAffectedByGravity) {
        super(owner, x, y, width, height, initialSpeed, initialAngle, isAffectedByGravity);
    }

    @Override
    protected void collideWith(Entity entity) {
        if (entity instanceof Enemy enemy)
            enemy.addResourcesEffect(new AbsoluteInstantDamageEffect<>(damage));
    }
}
