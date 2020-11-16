package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

import java.awt.*;

public class Arrow extends MovableEntity {

    private int damage;

    public Arrow(Direction direction, int x, int y, int damage) {
        this.damage = damage;
        teleport(x, y);
        setSpeed(2);
        setDirection(direction);
        setDimensionForDirection();
        updatePositionAccordingToDirection();
    }

    public int dealDamage() {
        return damage;
    }

    @Override
    public void update() {
        super.update();
        move(getDirection());
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, width, height, Color.WHITE);
    }

    private void updatePositionAccordingToDirection() {
        switch (getDirection()) {
            case UP -> teleport(x + 9, y);
            case DOWN -> teleport(x + 9, y + 32);
            case LEFT -> teleport(x, y + 9);
            case RIGHT -> teleport(x + 32, y + 9);
        }
    }

    private void setDimensionForDirection() {
        if (getDirection() == Direction.UP || getDirection() == Direction.DOWN) {
            setDimension(9,15);
            return;
        }
        setDimension(15, 9);
    }
}
