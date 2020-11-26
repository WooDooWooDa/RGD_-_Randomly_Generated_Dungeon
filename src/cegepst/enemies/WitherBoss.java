package cegepst.enemies;

import cegepst.Animator;
import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import java.util.ArrayList;
import java.util.Random;

public class WitherBoss extends Enemy {

    private static final String BOSS_PATH = "images/witherboss.png";
    private static final int MAX_HEALTH = 1000;
    private static final int PHYSICAL_ATTACK_RATE = 80;

    private final Animator animator;
    private WalkingPath path;
    private final Random rand;

    private int deltaX;
    private int deltaY;
    private int physicalAttackRate = PHYSICAL_ATTACK_RATE;

    public WitherBoss() {
        teleport(300, 300);
        setDimension(150,150);
        health = MAX_HEALTH;
        damage = 40;
        animator = new Animator(this, BOSS_PATH, 4);
        path = new WalkingPath(3);
        rand = new Random();
    }

    @Override
    public int dealDamage() {
        physicalAttackRate = PHYSICAL_ATTACK_RATE;
        return damage;
    }

    @Override
    public boolean canAttack() {
        return false;
    }

    public boolean canPhysicalAttack() {
        return physicalAttackRate == 0;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public ArrayList<StaticEntity> spawnWitherSkulls() {
        ArrayList<StaticEntity> skulls = new ArrayList<>();
        return skulls;
    }

    public void update(int playerX, int playerY) {
        physicalAttackRate--;
        if (physicalAttackRate <= 0) {
            physicalAttackRate = 0;
        }
        getPositionToPlayer(playerX, playerY);
        setImageDirection();
        changeWalkPath();
        move(path.follow());
    }

    @Override
    public void draw(Buffer buffer) {
        drawHealthBar(buffer);
        buffer.drawImage(animator.animate(), x, y);
    }

    private void drawHealthBar(Buffer buffer) {

    }

    private void changeWalkPath() {
        if (rand.nextInt(200) < 50) {
            int mode = rand.nextInt(100);
            if (mode > 66) {
                path.changeWalkMode(1);
            } else if (mode > 33) {
                path.changeWalkMode(2);
            } else path.changeWalkMode(3);
        }
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
