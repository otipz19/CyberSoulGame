package com.mygdx.game.entities.attacks.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.attacks.ProjectileSpawnPoint;
import com.mygdx.game.entities.projectiles.Projectile;

public abstract class ProjectileAttack implements Attack {
    protected Entity attacker;
    protected ProjectileSpawnPoint leftSpawn;
    protected ProjectileSpawnPoint rightSpawn;
    protected boolean facingRight;
    public ProjectileAttack(Entity attacker, ProjectileSpawnPoint leftSpawn, ProjectileSpawnPoint rightSpawn) {
        this.attacker = attacker;
        this.leftSpawn = leftSpawn;
        this.rightSpawn = rightSpawn;
    }

    public void setDirection(boolean facingRight) {
        this.facingRight = facingRight;
    }

    @Override
    public void execute() {
        Vector2 spawn;
        float angle;
        if (facingRight) {
            spawn = rightSpawn.getPoint(attacker);
            angle = 0;
        }
        else {
            spawn = leftSpawn.getPoint(attacker);
            angle = MathUtils.PI;
        }
        createProjectile(attacker, spawn.x, spawn.y, angle);
    }

    public abstract Projectile createProjectile(Entity owner, float x, float y, float initialAngle);
}
