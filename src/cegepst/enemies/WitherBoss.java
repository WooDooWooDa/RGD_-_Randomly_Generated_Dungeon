package cegepst.enemies;

import cegepst.Animator;
import cegepst.engine.Buffer;

public class WitherBoss extends Enemy {

    private static final String BOSS_PATH = "images/witherBoss.png";

    private final Animator animator;

    private int deltaX;
    private int deltaY;

    public WitherBoss() {
        teleport(300, 300);
        setDimension(100,100);
        health = 1000;
        damage = 40;
        animator = new Animator(this, BOSS_PATH, 4);
    }

    @Override
    public int dealDamage() {
        return damage;
    }

    @Override
    public boolean canAttack() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public void update(int playerX, int playerY) {
        getPositionToPlayer(playerX, playerY);
        setImageDirection();
    }

    @Override
    public void draw(Buffer buffer) {
        drawHealthBar(buffer);
        buffer.drawImage(animator.animate(), x, y);
    }

    private void drawHealthBar(Buffer buffer) {

    }

    private void setImageDirection() {
        if (deltaX > 0) {
            if (deltaY > 0) {
                animator.setCurrentAnimationFrame(1);
            } else {
                animator.setCurrentAnimationFrame(3);
            }
        } else {
            if (deltaY > 0) {
                animator.setCurrentAnimationFrame(2);
            } else {
                animator.setCurrentAnimationFrame(4);
            }
        }
    }

    private void getPositionToPlayer(int playerX, int playerY) {
        deltaX = x - playerX;
        deltaY = y - playerY;
    }
}
