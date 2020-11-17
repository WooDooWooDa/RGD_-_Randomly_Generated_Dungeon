package cegepst.enemies;

import cegepst.engine.Buffer;

public class Skeleton extends Enemy {

    public Skeleton(int x, int y) {
        teleport(x, y);
        setDimension(32,32);
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
