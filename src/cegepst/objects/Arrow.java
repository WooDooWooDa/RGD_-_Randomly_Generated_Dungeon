package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

public class Arrow extends MovableEntity {

    public Arrow(Direction direction, int x, int y) {
        teleport(x, y);
        setDirection(direction);
        setDimensionForDirection();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Buffer buffer) {

    }

    private void setDimensionForDirection() {
        if (getDirection() == Direction.UP || getDirection() == Direction.DOWN) {
            setDimension(15,9);
            return;
        }
        setDimension(9, 15);
    }
}
