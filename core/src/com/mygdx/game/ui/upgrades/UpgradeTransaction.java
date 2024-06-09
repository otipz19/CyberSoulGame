package com.mygdx.game.ui.upgrades;

public abstract class UpgradeTransaction {
    protected final int price;

    public UpgradeTransaction(int price) {
        this.price = price;
    }

    public abstract void commit();
    public abstract void undo();
}
