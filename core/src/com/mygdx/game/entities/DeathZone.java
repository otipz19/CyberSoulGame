package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.entities.resources.RelativeInstantDamageEffect;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

/**
 * DeathZone is a type of GameObject that defines an area in the game where
 * entering triggers instant death or damage effects on Heroes.
 */
public class DeathZone extends GameObject implements ITriggerListener {

    /**
     * Constructs a DeathZone object within a specified level and bounds.
     *
     * @param level The Level in which this DeathZone exists.
     * @param bounds The rectangular bounds defining the area of the DeathZone.
     */
    public DeathZone(Level level, Rectangle bounds) {
        this.level = level;
        // Create a collider based on the provided bounds and level's coordinate projector
        Collider collider = ColliderCreator.create(bounds, level.getCoordinatesProjector());
        // Create a sensor body for the DeathZone in the game world
        this.body = BodyCreator.createSensorBody(level.world, collider, this);
    }

    /**
     * Called when a GameObject enters the DeathZone's trigger area.
     * If the GameObject is a Hero, makes them vulnerable and applies a relative
     * instant damage effect to reduce their health.
     *
     * @param other The GameObject that entered the DeathZone's trigger area.
     */
    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof Hero hero) {
            hero.getResourcesManager().setInvincible(false); // Make hero vulnerable
            var damageEffect = new RelativeInstantDamageEffect<HeroResourcesManager>(1); // Damage effect of 1 unit
            damageEffect.apply(hero.getResourcesManager(), 0); // Apply damage effect immediately
        }
    }

    /**
     * Called when a GameObject exits the DeathZone's trigger area.
     * Currently not implemented for DeathZone as it is a one-time trigger effect.
     *
     * @param other The GameObject that exited the DeathZone's trigger area.
     */
    @Override
    public void onTriggerExit(GameObject other) {
        // Not implemented for DeathZone
    }
}