package cegepst.enemies;

import cegepst.WalkingAnimator;
import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

public class Zombie extends MovableEntity {

    private static final String SPRITE_PATH = "images/player.png";
    private final int MOVE_COOLDOWN;
    private final WalkingAnimator animator;

    private int deltaX;
    private int deltaY;
    private int moveCooldown;

    public Zombie(int x, int y, int moveSpeed) {
        MOVE_COOLDOWN = moveSpeed;
        moveCooldown = MOVE_COOLDOWN;
        teleport(x, y);
        setDimension(32,32);
        animator = new WalkingAnimator(this, SPRITE_PATH, 96, 128);
        animator.setAnimationSpeed(moveSpeed * 5);
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
            if (hitsObstacleVertically()) {
                moveLeft();
            }
            if (hitsObstacleHorizontally()) {
                moveDown();
            }
            animator.update();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        if ((Math.abs(deltaY) == Math.abs(deltaX))) {
            if (deltaX > 0) {
                buffer.drawImage(animator.animateLeft(), x, y);
            } else {
                buffer.drawImage(animator.animateRight(), x, y);
            }
            return;
        }
        buffer.drawImage(animator.animate(getDirection()), x, y);
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

    private boolean hitsObstacleVertically() {
        return !hasMoved() && (getDirection() == Direction.DOWN || getDirection() == Direction.UP);
    }

    private boolean hitsObstacleHorizontally() {
        return !hasMoved() && (getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT);
    }


    private void getPositionToPlayer(int playerX, int playerY) {
        deltaX = x - playerX;
        deltaY = y - playerY;
    }
}
