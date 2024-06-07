package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.entities.resources.HeroResourcesManager;
import com.mygdx.game.entities.resources.RelativeInstantDamageEffect;
import com.mygdx.game.levels.Level;
import com.mygdx.game.physics.BodyCreator;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;

public class DeathZone extends GameObject implements ITriggerListener {
    public DeathZone(Level level, Rectangle bounds) {
        this.level = level;
        Collider collider = ColliderCreator.create(bounds, level.getCoordinatesProjector());
        this.body = BodyCreator.createSensorBody(level.world, collider, this);
    }

    @Override
    public void onTriggerEnter(GameObject other) {
        if (other instanceof Hero hero) {
            var damageEffect = new RelativeInstantDamageEffect<HeroResourcesManager>(1);
            damageEffect.apply(hero.getResourcesManager(), 0);
        }
    }

    @Override
    public void onTriggerExit(GameObject other) {

    }
}
