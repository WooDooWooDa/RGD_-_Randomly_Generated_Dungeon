package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entities.MovableEntity;

import java.awt.*;

public class WalkerEnemy extends MovableEntity {

    private WalkingPath path;

    public WalkerEnemy() {
        teleport(400, 500);
        setDimension(10, 10);
        setSpeed(1);
        path = new WalkingPath();
        setWalkingPath();
    }

    @Override
    public void update() {
        move(path.move());
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, width, height, Color.WHITE);
    }

    private void setWalkingPath() {
        path.setDownUpWalk(100);
        path.setLeftRightWalk(100);
    }
}
