package com.mygdx.game.entities.npc;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.base.Animator;
import com.mygdx.game.animation.concrete.npc.MonkAnimator;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.NpcData;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

/**
 * Represents a Monk NPC entity in the game.
 * Inherits behavior from the Npc class and defines specific interactions and rendering.
 */
public class Monk extends Npc {

    /**
     * Constructs a Monk NPC with the specified level and NPC data.
     *
     * @param level   The level instance where the Monk NPC exists.
     * @param npcData The data defining the properties of the Monk NPC.
     */
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

    /**
     * Renders the Monk NPC, updating animation based on the elapsed time.
     *
     * @param deltaTime The time elapsed since the last frame, in seconds.
     */
    @Override
    public void render(float deltaTime) {
        animate(deltaTime);
    }

    /**
     * Handles interaction with another entity.
     * If the interaction cause is a Hero, triggers an interaction state and shows upgrade UI.
     *
     * @param interactionCause The entity causing the interaction.
     */
    @Override
    public void interact(Entity interactionCause) {
        if (interactionCause instanceof Hero hero) {
            animator.setState(MonkAnimator.State.INTERACT);
            level.ui.showUpgradeUI(level);
        }
    }
}
