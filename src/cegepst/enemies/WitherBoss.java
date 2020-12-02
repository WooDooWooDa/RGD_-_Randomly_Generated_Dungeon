package cegepst.enemies;

import cegepst.Animator;
import cegepst.Camera;
import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.StaticEntity;
import cegepst.objects.WitherSkull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WitherBoss extends Enemy {

    private static final String BOSS_PATH = "images/witherboss.png";
    private static final int PHYSICAL_ATTACK_RATE = 80;
    private static final int ATTACK_RATE = 200;

    private final Animator animator;
    private WalkingPath path;
    private final Random rand;

    private int maxHealth = 1000;
    private int deltaX;
    private int deltaY;
    private int physicalAttackRate = PHYSICAL_ATTACK_RATE;
    private int attack = ATTACK_RATE;

    public WitherBoss() {
        teleport(300, 300);
        setDimension(150,150);
        health = maxHealth;
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
    public void upgrade() {
        maxHealth += 200;
        health = maxHealth;
        damage += 20;
    }

    @Override
    public boolean canAttack() {
        return attack < 0;
    }

    public boolean canPhysicalAttack() {
        return physicalAttackRate == 0;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public ArrayList<StaticEntity> attack() {
        return spawnWitherSkulls();
    }

    public void update(int playerX, int playerY) {
        physicalAttackRate--;
        attack--;
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
        buffer.drawText("Wither Boss", Camera.getInstance().getX() + 320, Camera.getInstance().getY() + 115, Color.BLACK);
        buffer.drawRectangle(Camera.getInstance().getX() + 150, Camera.getInstance().getY() + 120, 500, 16, new Color(50, 0 ,0));
        buffer.drawRectangle(Camera.getInstance().getX() + 150, Camera.getInstance().getY() + 120, (int)(500 * ((double) health / maxHealth)), 16, Color.RED);
    }

    private ArrayList<StaticEntity> spawnWitherSkulls() {
        attack = ATTACK_RATE;
        ArrayList<StaticEntity> skulls = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int skullX = (x - width) + (rand.nextInt(width * 3));
            int skullY = (y - height) + (rand.nextInt(height * 3));
            skulls.addAll(createSkullPack(skullX, skullY));
        }
        return skulls;
    }

    private ArrayList<StaticEntity> createSkullPack(int skullx, int skully) {
        ArrayList<StaticEntity> skulls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            WitherSkull skull = new WitherSkull(skullx, skully, damage);
            if (i == 0) {
                skull.setDirection(Direction.UP);
            } else if (i == 1) {
                skull.setDirection(Direction.RIGHT);
            } else if (i == 2) {
                skull.setDirection(Direction.DOWN);
            } else {
                skull.setDirection(Direction.LEFT);
            }
            skulls.add(skull);
        }
        return skulls;
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
