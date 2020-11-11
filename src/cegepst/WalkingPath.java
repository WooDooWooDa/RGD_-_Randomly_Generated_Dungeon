package cegepst;

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
    }

    public void setDownUpWalk(int length) {
        walkDown = length;
        walkUp = length;
    }

    public void setLeftRightWalk(int length) {
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

        return currentDirection;
    }

    private Direction moveUpDown() {

        return currentDirection;
    }
}
