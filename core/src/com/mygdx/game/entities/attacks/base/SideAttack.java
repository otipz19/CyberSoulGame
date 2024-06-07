package com.mygdx.game.entities.attacks.base;

public abstract class SideAttack implements Attack {
    protected boolean facingRight;
    public void setDirection(boolean facingRight) {
        this.facingRight = facingRight;
    }
}
