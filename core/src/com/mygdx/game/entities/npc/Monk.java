package com.mygdx.game.entities.npc;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.npc.MonkAnimator;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.AbsoluteInstantDamageEffect;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.NpcData;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class Monk extends Npc {
    public Monk(Level level, NpcData npcData) {
        this.level = level;
        Collider collider = ColliderCreator.create(npcData.getBounds(), level.getCoordinatesProjector());
        this.body = BodyCreator.createSensorBody(level.world, collider, this);
        Vector2 worldSize = level.getCoordinatesProjector().toWorldSize(npcData.getBounds());
        this.width = worldSize.x;
        this.height = worldSize.y;
        this.animator = new MonkAnimator();
        animator.setDirection(Animator.Direction.LEFT);
    }

    @Override
    public void render(float deltaTime) {
        animate(deltaTime);
    }

    @Override
    public void interact(Entity interactionCause) {
        if(interactionCause instanceof Hero hero) {
            animator.setState(MonkAnimator.State.INTERACT);
            new AbsoluteInstantDamageEffect<>(100).apply(hero.getResourcesManager(), 0);
        }
    }
}
