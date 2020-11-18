package cegepst.objects;

import cegepst.GameResources;

import java.awt.*;
import java.util.Random;

public class Sword extends Item {

    private int attackDamage;
    private int attackRate;

    public Sword(int x, int y, int level) {
        super(x, y, getSwordName(), getImage(level - 1));
        setAttackPoint(level);
    }

    public boolean isBetterThan(Sword other) {
        return this.attackDamage > other.attackDamage;
    }

    public int dealDamage() {
        return attackDamage;
    }

    public boolean canAttack() {
        return true;
    }

    private static Image getImage(int x) {
        return GameResources.getInstance().getBufferedImage("items").getSubimage(x * 16,  4 * 16, 16, 16);
    }

    private static String getSwordName() {
        return "name";
    }

    private void setAttackPoint(int level) {
        Random random = new Random();
        if (level == 2) {
            attackDamage = random.nextInt(25 + 1 - 10) + 10;
            return;
        }
        if (level == 3) {
            attackDamage = random.nextInt(40 + 1 - 20) + 20;
            return;
        }
        if (level == 4) {
            attackDamage = random.nextInt(50 + 1 - 35) + 35;
            return;
        }
        if (level == 5) {
            attackDamage = random.nextInt(70 + 1 - 50) + 50;
            return;
        }
        attackDamage = random.nextInt(20 + 1 - 5) + 5;
    }
}
