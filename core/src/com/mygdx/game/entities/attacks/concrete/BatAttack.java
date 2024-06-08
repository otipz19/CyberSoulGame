package com.mygdx.game.entities.attacks.concrete;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.AttackZonePosition;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.attacks.base.SideMeleeAttack;
import com.mygdx.game.entities.attacks.base.SideProjectileAttack;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.projectiles.*;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;

public class BatAttack extends SideProjectileAttack {
    public BatAttack(Entity attacker) {
        super(attacker, ProjectileSpawnPoint.MIDDLE_LEFT, ProjectileSpawnPoint.MIDDLE_RIGHT);
    }

    @Override
    public float getAttackTime() {
        return 0.6f;
    }

    @Override
    public float getAttackDelay() {
        return 0.25f;
    }

    @Override
    public float getProjectileWidth() {
        return 1.2f;
    }

    @Override
    public float getProjectileHeight() {
        return 0.6f;
    }

    @Override
    public Projectile createProjectile(Entity owner, float x, float y, float initialAngle) {
        EnemyProjectile projectile = new BatProjectile(owner, x, y, getProjectileWidth(), getProjectileHeight(), initialAngle);
        projectile.setDamage(30);
        return projectile;
    }
}
