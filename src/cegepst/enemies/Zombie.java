package cegepst.enemies;

import cegepst.WalkingAnimator;
import cegepst.engine.Buffer;
import cegepst.engine.SoundPlayer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

import java.awt.*;
import java.util.Random;

public class Zombie extends MovableEntity {

    public static int maxHealth = 100;
    private static final String SPRITE_PATH = "images/player.png";
    private final int MOVE_COOLDOWN;
    private final int GRLL_COOLDOWN = 100;
    private final int ATTACK_RATE = 120;
    private final WalkingAnimator animator;

    private int health = maxHealth;
    private int damage = 5;

    private int deltaX;
    private int deltaY;
    private int moveCooldown;
    private int grll = GRLL_COOLDOWN;
    private int attack = ATTACK_RATE;

    public Zombie(int x, int y, int moveSpeed) {
        MOVE_COOLDOWN = moveSpeed;
        moveCooldown = MOVE_COOLDOWN;
        teleport(x, y);
        setDimension(32,32);
        animator = new WalkingAnimator(this, SPRITE_PATH, 96, 128);
        animator.setAnimationSpeed(moveSpeed * 5);
    }

    public void receivedDamage(int damage) {
        health -= damage;
    }

    public int dealDamage() {
        attack = ATTACK_RATE;
        return damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean canAttack() {
        return attack == 0;
    }

    public void update(int playerX, int playerY) {
        super.update();
        --moveCooldown;
        --attack;
        if (attack <= 0) {
            attack = 0;
        }
        playSoundIfClose(playerX, playerY);
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
        if (health < maxHealth) {
            buffer.drawRectangle(x , y - 10, (int)(32 * ((double)health / maxHealth)), 3, Color.RED);
        }
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

    private void playSoundIfClose(int playerX, int playerY) {
        if ((playerX >= (x - width * 2) && playerX <= (x + width * 3)) && (playerY >= (y - height * 2) && playerY <= (y + height * 3))) {
            grll--;
            if (grll <= 0) {
                grll = 0;
            }
            Random rand = new Random();
            int chance = rand.nextInt(200) + 1;
            if (chance <= 1 && grll == 0) {
                grll = GRLL_COOLDOWN;
                if (rand.nextInt(2) == 1) {
                    SoundPlayer.play("sounds/zombies1.wav");
                } else {
                    SoundPlayer.play("sounds/zombies2.wav");
                }
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
