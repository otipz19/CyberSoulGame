package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.projectiles.HeroProjectile;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.entities.projectiles.PunkScytheProjectile;

public class PunkAttack2 extends SideProjectileAttack {

    public PunkAttack2(Entity attacker) {
        super(attacker, ProjectileSpawnPoint.MIDDLE_LEFT, ProjectileSpawnPoint.MIDDLE_RIGHT);
    }

    @Override
    public float getAttackTime() {
        return 0.8f;
    }

    @Override
    public float getAttackDelay() {
        return 0.45f;
    }

    @Override
    public float getProjectileWidth() {
        return 0.75f;
    }

    @Override
    public float getProjectileHeight() {
        return 0.75f;
    }

    @Override
    public float getEnergyConsumption() {
        return 20f;
    }

    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        HeroProjectile projectile = new PunkScytheProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(30);
        return projectile;
    }
}
