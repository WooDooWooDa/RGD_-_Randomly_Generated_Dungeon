package cegepst;

import cegepst.engine.controls.Direction;

public class WalkingPath {

    private int walkRight;
    private int walkLeft;
    private int walkUp;
    private int walkDown;
    private int length;
    private Direction currentDirection;

    public WalkingPath() {
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

    public Direction move() {
        length++;
        if (length > walkUp && currentDirection == Direction.UP) {
            currentDirection = Direction.LEFT;
        }
        if (length > walkLeft && currentDirection == Direction.LEFT) {
            currentDirection = Direction.DOWN;
        }
        return currentDirection;
    }
}
