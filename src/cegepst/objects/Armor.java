package cegepst.objects;

import cegepst.GameResources;

import java.awt.*;
import java.util.Random;

public class Armor extends Item {

    private int armorPoint;

    public Armor(int x, int y, int level) {
        super(level, x, y, getArmorName(), getImage(level - 1));
        setArmorPoint(level);
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

    private static Image getImage(int xPositionInSheet) {
        return GameResources.getInstance().getBufferedImage("items").getSubimage(xPositionInSheet * 16, 16, 16, 16);
    }

    private static String getArmorName() {
        return "name";
    }

    private void setArmorPoint(int level) {
        Random random = new Random();
        if (level == 2) {
            armorPoint = random.nextInt(15 + 1 - 5) + 5;
            return;
        }
        if (level == 3) {
            armorPoint = random.nextInt(25 + 1 - 10) + 10;
            return;
        }
        if (level == 4) {
            armorPoint = random.nextInt(35 + 1 - 20) + 20;
            return;
        }
        if (level == 5) {
            armorPoint = random.nextInt(50 + 1 - 35) + 35;
            return;
        }
        armorPoint = random.nextInt(10 + 1 - 2) + 2;
    }

}
