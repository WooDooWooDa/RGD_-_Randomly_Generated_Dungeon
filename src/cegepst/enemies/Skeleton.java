package cegepst.enemies;

import cegepst.engine.Buffer;

public class Skeleton extends Enemy {

    public Skeleton() {

    }

    @Override
    public int dealDamage() {
        return 0;
    }

    @Override
    public boolean canAttack() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Buffer buffer) {

    }
}
