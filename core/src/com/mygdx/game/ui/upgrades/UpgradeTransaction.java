package com.mygdx.game.ui.upgrades;

/**
 * Abstract class representing an upgrade transaction.
 * Subclasses define specific upgrade actions and their reversals.
 */
public abstract class UpgradeTransaction {
    protected final int price;

    public UpgradeTransaction(int price) {
        this.price = price;
    }

    public abstract void commit();
    public abstract void undo();
}
