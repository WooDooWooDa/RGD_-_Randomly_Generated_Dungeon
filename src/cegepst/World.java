package cegepst;

import cegepst.enemies.Slime;
import cegepst.enemies.WitherBoss;
import cegepst.enemies.Zombie;
import cegepst.enemies.ZombieSpawner;
import cegepst.engine.Buffer;
import cegepst.engine.entities.Blockade;
import cegepst.engine.entities.StaticEntity;
import cegepst.objects.Chest;
import cegepst.objects.Item;
import cegepst.objects.Merchant;
import cegepst.objects.Tag;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class World {

    public static int WORLD_WIDTH = 1000;
    public static int WORLD_HEIGHT = 800;

    private final BufferedImage[] biomeImages = new BufferedImage[5];
    private Image backGround;
    private ArrayList<Blockade> worldBorders;

    public World() {
        loadBackGrounds();
        worldBorders = new ArrayList<>();
        createBorders();
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(backGround, 0, 0);
        for (Blockade worldBorder : worldBorders) {
            worldBorder.draw(buffer);
        }
    }

    public void changeBiome(int biome) {
        backGround = biomeImages[biome - 1];
        WORLD_WIDTH = biomeImages[biome - 1].getWidth();
        WORLD_HEIGHT = biomeImages[biome - 1].getHeight();
    }

    public ArrayList<StaticEntity> createMobs(int difficulty) {
        ArrayList<StaticEntity> enemies = new ArrayList<>();
        Random rand = new Random();
        if (difficulty == 4) {
            enemies.add(new ZombieSpawner(rand.nextInt(WORLD_WIDTH), rand.nextInt(WORLD_HEIGHT)));
            enemies.add(new WitherBoss());
            return enemies;
        }
        int nbZombies = rand.nextInt(difficulty * 2) + 5;
        int nbSlimes = rand.nextInt(difficulty) + 8;
        for (int i = 0; i < nbZombies; i++) {
            enemies.add(new Zombie(rand.nextInt(WORLD_WIDTH), rand.nextInt(WORLD_HEIGHT)));
        }
        for (int i = 0; i < nbSlimes; i++) {
            enemies.add(new Slime(rand.nextInt(WORLD_WIDTH), rand.nextInt(WORLD_HEIGHT)));
        }
        for (int i = 0; i < difficulty; i++) {
            enemies.add(new ZombieSpawner(rand.nextInt(WORLD_WIDTH - 100) + 20, rand.nextInt(WORLD_HEIGHT)));
        }
        return enemies;
    }

    public ArrayList<StaticEntity> createMisc() {
        ArrayList<StaticEntity> entities = new ArrayList<>();
        Random rand = new Random();
        entities.add(new Chest(rand.nextInt(WORLD_WIDTH), rand.nextInt(WORLD_HEIGHT)));
        entities.add(new Chest(rand.nextInt(WORLD_WIDTH), rand.nextInt(WORLD_HEIGHT)));
        if (rand.nextInt(100) <= 1) {
            entities.add(new Merchant(rand.nextInt(WORLD_WIDTH - 50) + 20, rand.nextInt(WORLD_HEIGHT - 100) + 20));
        }
        for (int i = 0; i < 3; i++) {
            entities.add(new Tag(rand.nextInt(WORLD_WIDTH - 50) + 20, rand.nextInt(WORLD_HEIGHT - 100) + 20));
        }
        for (int i = 0; i < 5; i++) {
            entities.add(new Rock(rand.nextInt(WORLD_WIDTH), rand.nextInt(WORLD_HEIGHT)));
        }
        entities.add(Item.Factory.create(rand.nextInt(WORLD_WIDTH), rand.nextInt(WORLD_HEIGHT)));
        return entities;
    }

    private void createBorders() {
        createBorderTop();
        createBorderBottom();
        createBorderRight();
        createBorderLeft();
    }

    private void createBorderLeft() {
        Blockade border = new Blockade();
        border.setDimension(10, WORLD_HEIGHT);
        border.teleport(-border.getWidth() * 2,0);
        worldBorders.add(border);
    }

    private void createBorderRight() {
        Blockade border = new Blockade();
        border.setDimension(10, WORLD_HEIGHT);
        border.teleport(WORLD_WIDTH - border.getWidth() * 2, 0);
        worldBorders.add(border);
    }

    private void createBorderBottom() {
        Blockade border = new Blockade();
        border.setDimension(WORLD_WIDTH, 10);
        border.teleport(0, -border.getHeight());
        worldBorders.add(border);
    }

    private void createBorderTop() {
        Blockade border = new Blockade();
        border.setDimension(WORLD_WIDTH, 10);
        border.teleport(0, WORLD_HEIGHT);
        worldBorders.add(border);
    }

    private void loadBackGrounds() {
        biomeImages[0] = GameResources.getInstance().getBufferedImage("forestMap");
        biomeImages[1] = GameResources.getInstance().getBufferedImage("snowMap");
        biomeImages[2] = GameResources.getInstance().getBufferedImage("desertMap");
        biomeImages[3] = GameResources.getInstance().getBufferedImage("bossMap");
    }
}
