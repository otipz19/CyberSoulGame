package com.mygdx.game.entities.portals;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.animation.concrete.portals.PortalAnimator;
import com.mygdx.game.animation.concrete.portals.ThirdPortalAnimator;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.heroes.Hero;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PortalData;
import com.mygdx.game.sound.SoundPlayer;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.PlayerDataManager;

public class LastPortal extends Portal {
    public LastPortal(Level level, PortalData portalData) {
        super(level, portalData, new ThirdPortalAnimator());
    }

    @Override
    public void interact(Entity interactionCause) {
        if (isEnabled && interactionCause instanceof Hero hero) {
            if (!hasActivated) {
                animator.setState(PortalAnimator.State.ACTIVATING);
                SoundPlayer.getInstance().playSound(Assets.Sound.PORTAL_CHARGING_SOUND);
            } else {
                PlayerDataManager.getInstance().setHeroData(hero.getData());
                MyGdxGame.getInstance().gameCompleted();
            }
        }
    }
}
