package com.mygdx.game.entities.portals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.PortalAnimator;
import com.mygdx.game.camera.CoordinatesProjector;
import com.mygdx.game.entities.InteractableEntity;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.PortalData;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public abstract class Portal extends InteractableEntity {
    private final PortalData portalData;
    private boolean isEnabled;

    private boolean hasActivated;

    public Portal(Level level, PortalData portalData, CoordinatesProjector projector, PortalAnimator animator) {
        this.level = level;
        this.portalData = portalData;
        this.isEnabled = portalData.isEnabled();
        Collider collider = ColliderCreator.create(portalData.getBounds(), projector);
        body = BodyCreator.createStaticBody(level.world, collider, 0, 0, 0);
        Fixture fixture = body.getFixtureList().first();
        fixture.setSensor(true);
        fixture.setUserData(this);
        Vector2 projectedSize = projector.toWorldSize(portalData.getBounds());
        this.width = projectedSize.x;
        this.height = projectedSize.y;
        this.animator = animator;
    }

    @Override
    public void render(float deltaTime) {
        animate(deltaTime);
        hasActivated = animator.getState() == PortalAnimator.State.ACTIVATING && animator.isAnimationFinished();
    }

    @Override
    public void interact() {
        if (isEnabled) {
            if (!hasActivated) {
                animator.setState(PortalAnimator.State.ACTIVATING);
            } else {
                MyGdxGame.getInstance().goToNewLevel(portalData.getDestination());
            }
        }
    }

    public void enable() {
        isEnabled = true;
    }

    public void disable() {
        isEnabled = false;
    }
}
