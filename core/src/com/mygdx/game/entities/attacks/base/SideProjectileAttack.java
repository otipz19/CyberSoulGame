package com.mygdx.game.entities.attacks.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.projectiles.Projectile;
import com.mygdx.game.utils.DelayedAction;

public abstract class SideProjectileAttack extends SideAttack implements RangeAttack {
    protected Entity attacker;
    protected ProjectileSpawnPoint leftSpawn;
    protected ProjectileSpawnPoint rightSpawn;

    public SideProjectileAttack(Entity attacker, ProjectileSpawnPoint leftSpawn, ProjectileSpawnPoint rightSpawn) {
        this.attacker = attacker;
        this.leftSpawn = leftSpawn;
        this.rightSpawn = rightSpawn;
    }

    @Override
    public void execute() {
        Vector2 spawn;
        float angle;
        if (facingRight) {
            spawn = rightSpawn.getPoint(attacker, getProjectileWidth(), getProjectileHeight());
            angle = 0;
        }
        else {
            spawn = leftSpawn.getPoint(attacker, getProjectileWidth(), getProjectileHeight());
            angle = MathUtils.PI;
        }
        new DelayedAction(getAttackDelay(), () -> createProjectile(attacker, spawn.x, spawn.y, angle));
    }

    public abstract Projectile createProjectile(Entity owner, float x, float y, float initialAngle);
}
