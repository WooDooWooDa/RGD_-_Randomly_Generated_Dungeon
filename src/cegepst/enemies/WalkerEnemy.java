package cegepst.enemies;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.entities.MovableEntity;

import java.awt.*;

public class  WalkerEnemy extends MovableEntity {

    private WalkingPath path;

    public WalkerEnemy() {
        teleport(400, 500);
        setDimension(10, 10);
        setSpeed(1);
        path = new WalkingPath(WalkingPath.SQUARE);
        setWalkingPathLength();
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        move(path.follow());
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, width, height, Color.WHITE);
    }

    private void setWalkingPathLength() {
        path.setDownUpWalk(100);
        path.setLeftRightWalk(100);
    }
}
