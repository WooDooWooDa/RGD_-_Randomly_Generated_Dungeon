package cegepst.enemies;

import cegepst.engine.controls.Direction;

public class WalkingPath {

    public static final int LINEAR_UP_DOWN = 1;
    public static final int LINEAR_LEFT_RIGHT = 2;
    public static final int SQUARE = 3;

    private int walkRight;
    private int walkLeft;
    private int walkUp;
    private int walkDown;
    private int length;
    private int walkMode;
    private Direction currentDirection;

    public WalkingPath(int walkMode) {
        this.walkMode = walkMode;
        length = 0;
        currentDirection = Direction.UP;
        if (walkMode == LINEAR_LEFT_RIGHT) {
            currentDirection = Direction.LEFT;
        }
    }

    public void changeWalkMode(int mode) {
        walkMode = mode;
    }

    public void setHorizontalWalk(int length) {
        walkDown = length;
        walkUp = length;
    }

    public void setVerticalWalk(int length) {
        walkLeft = length;
        walkRight = length;
    }

    public Direction follow() {
        if (walkMode == SQUARE) {
            return moveInSquare();
        }
        if (walkMode == LINEAR_LEFT_RIGHT) {
            return moveLeftRight();
        }
        if (walkMode == LINEAR_UP_DOWN) {
            return moveUpDown();
        }
        return null;
    }

    private Direction moveInSquare() {
        length++;
        if (length > walkUp && currentDirection == Direction.UP) {
            length = 0;
            currentDirection = Direction.LEFT;
        }
        if (length > walkLeft && currentDirection == Direction.LEFT) {
            length = 0;
            currentDirection = Direction.DOWN;
        }
        if (length > walkDown && currentDirection == Direction.DOWN) {
            length = 0;
            currentDirection = Direction.RIGHT;
        }
        if (length > walkRight && currentDirection == Direction.RIGHT) {
            length = 0;
            currentDirection = Direction.UP;
        }
        return currentDirection;
    }

    private Direction moveLeftRight() {
        length++;
        if (length > walkLeft && currentDirection == Direction.LEFT) {
            length = 0;
            currentDirection = Direction.RIGHT;
        }
        if (length > walkRight && currentDirection == Direction.RIGHT) {
            length = 0;
            currentDirection = Direction.LEFT;
        }
        return currentDirection;
    }

    private Direction moveUpDown() {
        length++;
        if (length > walkDown && currentDirection == Direction.DOWN) {
            length = 0;
            currentDirection = Direction.UP;
        }
        if (length > walkUp && currentDirection == Direction.UP) {
            length = 0;
            currentDirection = Direction.DOWN;
        }
        return currentDirection;
    }
}
