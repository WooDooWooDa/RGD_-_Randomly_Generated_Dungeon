package cegepst.enemies;

import cegepst.Animator;
import cegepst.engine.Buffer;
import cegepst.engine.SoundPlayer;

import java.awt.*;
import java.util.Random;

public class Slime extends Enemy {

    private int maxHealth = 50;

    private final int ATTACK_RATE = 50;

    private WalkingPath path;
    private Animator animator;

    private int attack = ATTACK_RATE;

    public Slime(int x, int y) {
        teleport(x, y);
        setDimension(32, 32);
        setSpeed(1);
        damage = 5;
        health = maxHealth;
        animator = new Animator(this, "slime", 4);
        animator.setAnimationSpeed(16);
        path = new WalkingPath(new Random().nextInt(3) + 1);
        setWalkingPathLength();
    }

    @Override
    public void upgrade() {
        maxHealth += 50;
        health = maxHealth;
        damage += 10;
    }

    @Override
    public int dealDamage() {
        attack = ATTACK_RATE;
        return damage;
    }

    public boolean canAttack() {
        return attack == 0;
    }

    public boolean isAlive() {
        return health > 0;
    }
    
    public void update(int playerX, int playerY) {
        animator.update();
        --attack;
        if (attack < 0) {
            attack = 0;
        }
        move(path.follow());
        playSoundIfClose(playerX, playerY);
    }

    private void playSoundIfClose(int playerX, int playerY) {
        if ((playerX >= (x - width * 2) && playerX <= (x + width * 3)) && (playerY >= (y - height * 2) && playerY <= (y + height * 3))) {
            Random rand = new Random();
            int chance = rand.nextInt(200) + 1;
            if (chance <= 1) {
                SoundPlayer.play("sounds/slimeWalking.wav");
            }
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(animator.animate(), x, y);
        if (health < maxHealth) {
            buffer.drawRectangle(x , y - 10, (int)(32 * ((double)health / maxHealth)), 3, Color.RED);
        }
    }

    private void setWalkingPathLength() {
        Random rand = new Random();
        path.setHorizontalWalk(rand.nextInt(100) + 100);
        path.setVerticalWalk(rand.nextInt(100) + 100);
    }
}
