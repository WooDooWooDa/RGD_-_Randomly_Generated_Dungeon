package cegepst.objects;

import cegepst.GameResources;

import java.awt.*;
import java.util.Random;

public class Armor extends Item {

    private int armorPoint;
    private int healthPoint;

    public Armor(int x, int y, int level) {
        super(level, x, y, getImage(level - 1));
        setBonusPoint(level);
    }

    public boolean isBetterThan(Armor other) {
        if (other == null) {
            return true;
        }
        return this.armorPoint > other.armorPoint;
    }

    public int getArmorPoint() {
        return armorPoint;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    private static Image getImage(int xPositionInSheet) {
        return GameResources.getInstance().getBufferedImage("items").getSubimage(xPositionInSheet * 16, 16, 16, 16);
    }

    private void setBonusPoint(int level) {
        Random random = new Random();
        if (level == 2) {
            armorPoint = random.nextInt(15 + 1 - 5) + 5;
            healthPoint = random.nextInt(100 + 1 - 50) + 50;
            return;
        }
        if (level == 3) {
            armorPoint = random.nextInt(25 + 1 - 10) + 10;
            healthPoint = random.nextInt(200 + 1 - 100) + 100;
            return;
        }
        if (level == 4) {
            armorPoint = random.nextInt(35 + 1 - 20) + 20;
            healthPoint = random.nextInt(350 + 1 - 200) + 200;
            return;
        }
        if (level == 5) {
            armorPoint = random.nextInt(50 + 1 - 35) + 35;
            healthPoint = random.nextInt(500 + 1 - 350) + 350;
            return;
        }
        armorPoint = random.nextInt(10 + 1 - 2) + 2;
        healthPoint = random.nextInt(50) + 1;
    }

}
