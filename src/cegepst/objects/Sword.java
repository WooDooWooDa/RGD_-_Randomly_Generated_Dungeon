package cegepst.objects;

import cegepst.GameResources;

import java.awt.*;
import java.util.Random;

public class Sword extends Item {

    private int attackDamage;
    private final int attackRate;
    private int attackRange;

    private int rate;

    public Sword(int x, int y, int level) {
        super(level, x, y, getImage(level - 1));
        setBonusPoint(level);
        attackRate = 40 / level + 10;
    }

    public boolean isBetterThan(Sword other) {
        if (other == null) {
            return true;
        }
        return this.attackDamage > other.attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getDamage() {
        return attackDamage;
    }

    public void reset() {
        rate = 0;
    }

    public boolean canAttack() {
        return rate == attackRate;
    }

    public void update() {
        rate++;
        if (rate > attackRate) {
            rate = attackRate;
        }
    }

    private static Image getImage(int x) {
        return GameResources.getInstance().getBufferedImage("items").getSubimage(x * 16,  4 * 16, 16, 16);
    }

    private void setBonusPoint(int level) {
        Random random = new Random();
        if (level == 2) {
            attackDamage = random.nextInt(25 + 1 - 10) + 10;
            attackRange = 35;
            return;
        }
        if (level == 3) {
            attackDamage = random.nextInt(40 + 1 - 20) + 20;
            attackRange = 40;
            return;
        }
        if (level == 4) {
            attackDamage = random.nextInt(50 + 1 - 35) + 35;
            attackRange = 45;
            return;
        }
        if (level == 5) {
            attackDamage = random.nextInt(70 + 1 - 50) + 50;
            attackRange = 50;
            return;
        }
        attackDamage = random.nextInt(20 + 1 - 5) + 5;
        attackRange = 30;
    }
}
