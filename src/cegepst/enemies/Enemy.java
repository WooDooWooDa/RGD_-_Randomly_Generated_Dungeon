package cegepst.enemies;

import cegepst.engine.entities.MovableEntity;

public abstract class Enemy extends MovableEntity {

    public int health;
    public int damage;

    public void receivedDamage(int damage) {
        health -= damage;
    }

    public abstract int dealDamage();

    public abstract boolean canAttack();

    public abstract boolean isAlive();

}
