package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.HeroRangeAttack;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.entities.projectiles.PunkSProjectile;

public class PunkAttack1 extends HeroRangeAttack {

    public PunkAttack1(Hero attacker) {
        super(attacker, ProjectileSpawnPoint.MIDDLE_LEFT, ProjectileSpawnPoint.MIDDLE_RIGHT);
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return 0.35f;
    }

    @Override
    public float getProjectileWidth() {
        return 0.5f;
    }

    @Override
    public float getProjectileHeight() {
        return 0.5f;
    }

    public float getEnergyConsumption() {
        return 10f;
    }

    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        PunkSProjectile projectile = new PunkSProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(15 * getDamageMultiplier());
        return projectile;
    }
}
