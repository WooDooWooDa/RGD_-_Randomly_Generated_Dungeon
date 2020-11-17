package cegepst.objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Armor extends Item {

    private int armorPoint;

    public Armor(int x, int y, int level) {
        super(level, getArmorName(), getImage());
        teleport(x, y);
        setDimension(16, 16);
        setArmorPoint(level);
    }

    public boolean isBetterThan(Armor other) {
        if (this.armorPoint > other.armorPoint) {
            return true;
        }
        return false;
    }

    private static Image getImage() {
        //return ImageIO.read(.getClass().getClassLoader().getResourceAsStream("images/armorSheet.png"));
        return null;
    }

    private static String getArmorName() {
        return "name";
    }

    private void setArmorPoint(int level) {
        Random random = new Random();
        if (level == 2) {
            armorPoint = random.nextInt(15 + 1 - 5) + 5;
        } else if (level == 3) {
            armorPoint = random.nextInt(25 + 1 - 10) + 10;
        } else if (level == 4) {
            armorPoint = random.nextInt(35 + 1 - 20) + 20;
        } else if (level == 5) {
            armorPoint = random.nextInt(50 + 1 - 35) + 35;
        } else {
            armorPoint = random.nextInt(10 + 1 - 2) + 2;
        }
    }

}
