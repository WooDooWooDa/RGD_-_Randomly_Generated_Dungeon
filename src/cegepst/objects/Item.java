package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;
import cegepst.player.PlayerStats;

import java.awt.*;
import java.util.Random;

public class Item extends StaticEntity {

    private final int level;
    private final String name;
    private final Image image;

    protected Item(int level, int x, int y, String name, Image image) {
        setInteractable();
        this.level = level;
        teleport(x, y);
        setDimension(16, 16);
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
    }

    public static class Factory {
        private static Random random;

        public static Item create(int x, int y) {
             random = new Random();
            if (random.nextInt(2) == 1) {
                return new Armor(x, y, generateLevel());
            }
            return new Sword(x, y, generateLevel());
        }

        private static int generateLevel() {
            int playerLvl = PlayerStats.LVL;
            int chance = random.nextInt(100);
            if (playerLvl == 2) {
                if (chance <= 30) {
                    return 2;
                }
            } else if (playerLvl == 3) {
                if (chance <= 10) {
                    return 3;
                } else if (chance <= 30) {
                    return 2;
                }
            } else if (playerLvl == 4) {
                if (chance <= 5) {
                    return 4;
                } else if (chance <= 15) {
                    return 3;
                } else if (chance <= 40)
                    return 2;
            } else if (playerLvl >= 5) {
                if (chance <= 1) {
                    return 5;
                } else if (chance <= 14) {
                    return 4;
                } else if (chance <= 25) {
                    return 3;
                } else if (chance <= 30) {
                    return 2;
                }
            }
            return 1;
        }
    }

}
