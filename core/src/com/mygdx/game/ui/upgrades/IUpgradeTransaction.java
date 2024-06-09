package com.mygdx.game.ui.upgrades;

public interface IUpgradeTransaction {
    void commit();
    void undo();
}
