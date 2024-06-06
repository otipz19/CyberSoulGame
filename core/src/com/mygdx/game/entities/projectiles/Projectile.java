package com.mygdx.game.entities.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.utils.DelayedAction;

public abstract class Projectile extends Entity implements ITriggerListener, IRenderable {
    protected Entity owner;
    protected final float initialSpeedX;
    protected final float initialSpeedY;
    protected Array<Runnable> onExplosionActions;
    protected boolean hasCollided;

    public Projectile(Entity owner, float x, float y, float width, float height, float initialSpeed, float initialAngle, boolean isAffectedByGravity) {
        this.owner = owner;
        this.level = owner.getLevel();
        this.width = width;
        this.height = height;
        this.initialSpeedX = initialSpeed * MathUtils.cos(initialAngle);
        this.initialSpeedY = initialSpeed * MathUtils.sin(initialAngle);

        body = BodyCreator.createProjectileBody(level.world, ColliderCreator.create(x, y, width, height), isAffectedByGravity);
        body.getFixtureList().first().setUserData(this);

        onExplosionActions = new Array<>();
        onExplosionActions.add(() -> new DelayedAction(getDestructionDelay(), this::destruct));

        level.addProjectile(this);
        body.setLinearVelocity(initialSpeedX, initialSpeedY);
    }

    @Override
    public void render(float deltaTime) {
        if (hasCollided)
            body.setActive(false);
        animator.animate(MyGdxGame.getInstance().batch, body.getPosition().x, body.getPosition().y, width, height, deltaTime);
    }

    protected abstract void collideWith(Entity entity);

    protected void destruct() {
        level.world.destroyBody(body);
    }

    public abstract float getDestructionDelay();

    public void addOnExplosionAction(Runnable action){
        onExplosionActions.add(action);
    }

    public void removeOnExplosionAction(Runnable action){
        onExplosionActions.removeValue(action, true);
    }

    public void clearOnExplosionActions(){
        onExplosionActions.clear();
    }

    @Override
    public void onTriggerEnter(GameObject other) {
        if (other == owner)
            return;

        if (other instanceof ProjectileCollidable) {
            if (other instanceof Entity entity)
                collideWith(entity);
            hasCollided = true;
            onExplosionActions.forEach(Runnable::run);
            onExplosionActions.clear();
        }
    }

    @Override
    public void onTriggerExit(GameObject other) {}
}
