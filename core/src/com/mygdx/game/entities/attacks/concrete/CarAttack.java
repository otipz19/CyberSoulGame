package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.projectiles.CarProjectile;
import com.mygdx.game.entities.projectiles.EnemyProjectile;
import com.mygdx.game.entities.projectiles.Projectile;

public class CarAttack extends SideProjectileAttack {

    public CarAttack(Entity attacker) {
        super(attacker, ProjectileSpawnPoint.BOTTOM_LEFT, ProjectileSpawnPoint.BOTTOM_RIGHT);
    }

    @Override
    public float getAttackTime() {
        return 0.4f;
    }

    @Override
    public float getAttackDelay() {
        return 0.15f;
    }

    @Override
    public float getProjectileWidth() {
        return 1.2f;
    }

    @Override
    public float getProjectileHeight() {
        return 0.3f;
    }

    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        EnemyProjectile projectile = new CarProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(25);
        return projectile;
    }
}
