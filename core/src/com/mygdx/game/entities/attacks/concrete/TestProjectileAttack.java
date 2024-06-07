package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.entities.projectiles.TestProjectile;

public class TestProjectileAttack extends SideProjectileAttack {

    public TestProjectileAttack(Entity attacker) {
        super(attacker, ProjectileSpawnPoint.MIDDLE_LEFT, ProjectileSpawnPoint.MIDDLE_RIGHT);
    }

    @Override
    public float getAttackTime() {
        return 0;
    }

    @Override
    public float getAttackDelay() {
        return 0;
    }

    @Override
    public float getProjectileWidth() {
        return 0.5f;
    }

    @Override
    public float getProjectileHeight() {
        return 0.25f;
    }

    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        TestProjectile projectile = new TestProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(25);
        return projectile;
    }
}
