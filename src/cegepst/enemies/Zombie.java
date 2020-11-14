package cegepst.enemies;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

import java.awt.*;

public class Zombie extends MovableEntity {

    private final int MOVE_COOLDOWN;

    private int deltaX;
    private int deltaY;
    private int moveCooldown;

    public Zombie(int x, int y, int moveSpeed) {
        MOVE_COOLDOWN = moveSpeed;
        moveCooldown = MOVE_COOLDOWN;
        teleport(x, y);
        setDimension(32,32);
        setSpeed(2);
    }

    public void update(int playerX, int playerY) {
        super.update();
        --moveCooldown;
        if (moveCooldown <= 0) {
            moveCooldown = MOVE_COOLDOWN;
            getPositionToPlayer(playerX, playerY);
            determineDirectionToPlayer();
            if (!((deltaY == 0) && (deltaX == 0))) {
                move(getDirection());
            }
            if (!hasMoved() && (getDirection() == Direction.DOWN || getDirection() == Direction.UP)) {
                moveLeft();
            }
            if (!hasMoved() && (getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT)) {
                moveDown();
            }
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, width, height, Color.white);
    }

    private void determineDirectionToPlayer() {
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                setDirection(Direction.LEFT);
            } else {
                setDirection(Direction.RIGHT);
            }
        } else {
            if (deltaY > 0) {
                setDirection(Direction.UP);
            } else {
                setDirection(Direction.DOWN);
            }
        }
    }

    private void getPositionToPlayer(int playerX, int playerY) {
        deltaX = x - playerX;
        deltaY = y - playerY;
    }
}
