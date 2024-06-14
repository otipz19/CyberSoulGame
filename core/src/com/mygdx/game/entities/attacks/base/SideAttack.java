package com.mygdx.game.entities.attacks.base;
/**
 * SideAttack is an abstract class representing an attack in a side-scrolling context.
 * It provides functionality to determine the direction of the attack.
 */
public abstract class SideAttack implements Attack {
    protected boolean facingRight;
    public void setDirection(boolean facingRight) {
        this.facingRight = facingRight;
    }
}
