package com.mygdx.game.entities.portals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.animation.PortalAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ICollisionListener;
import com.mygdx.game.entities.InteractableEntity;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.PortalData;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class Portal extends InteractableEntity {
    public Portal(Level level, PortalData portalData, CoordinatesProjector projector) {
        this.level = level;
        Collider collider = ColliderCreator.create(portalData.getBounds(), projector);
        body = BodyCreator.createStaticBody(level.world, collider, 0, 0, 0);
        Fixture fixture = body.getFixtureList().first();
        fixture.setSensor(true);
        fixture.setUserData(this);
        Vector2 projectedSize = projector.toWorldSize(portalData.getBounds());
        this.width = projectedSize.x;
        this.height = projectedSize.y;
        animator = new PortalAnimator();
    }

    @Override
    public void render(float deltaTime) {
        animate(deltaTime);
    }

    @Override
    public void interact() {
        if (animator.getState() != PortalAnimator.State.ACTIVATING) {
            animator.setState(PortalAnimator.State.ACTIVATING);
        } else {
            animator.setState(PortalAnimator.State.INACTIVATING);
        }
    }
}
