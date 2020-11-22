package cegepst;

import cegepst.enemies.Slime;
import cegepst.enemies.Zombie;
import cegepst.enemies.ZombieSpawner;
import cegepst.engine.Buffer;
import cegepst.engine.RenderingEngine;
import cegepst.engine.entities.Blockade;
import cegepst.engine.entities.StaticEntity;
import cegepst.objects.Chest;
import cegepst.objects.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class World {

    private static final String FOREST_MAP_PATH = "images/forestMap.png";
    private static final String SNOW_MAP_PATH = "images/snowMap.jpg";
    private Image[] biomeImages = new Image[5];
    private Image backGround;
    private ArrayList<Blockade> worldBorders;

    public World() {
        loadBackGround();
        worldBorders = new ArrayList<>();
        createBorders();
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(backGround, 0, 0);
        for (Blockade worldBorder : worldBorders) {
            worldBorder.draw(buffer);
        }
    }

    public void changeBackGround(int biome) {
        backGround = biomeImages[biome - 1];
    }

    public ArrayList<StaticEntity> createMobs(int difficulty) {
        ArrayList<StaticEntity> enemies = new ArrayList<>();
        Random rand = new Random();
        int nbZombies = rand.nextInt(5) + difficulty;
        int nbSlimes = rand.nextInt(8) + difficulty;
        for (int i = 0; i < nbZombies; i++) {
            enemies.add(new Zombie(rand.nextInt(RenderingEngine.SCREEN_WIDTH), rand.nextInt(RenderingEngine.SCREEN_HEIGHT)));
        }
        for (int i = 0; i < nbSlimes; i++) {
            enemies.add(new Slime(rand.nextInt(RenderingEngine.SCREEN_WIDTH), rand.nextInt(RenderingEngine.SCREEN_HEIGHT)));
        }
        for (int i = 0; i < difficulty; i++) {
            enemies.add(new ZombieSpawner(rand.nextInt(RenderingEngine.SCREEN_WIDTH), rand.nextInt(RenderingEngine.SCREEN_HEIGHT)));
        }
        return enemies;
    }

    public ArrayList<StaticEntity> createMisc() {
        ArrayList<StaticEntity> entities = new ArrayList<>();
        entities.add(new Chest(100, 200));
        entities.add(new Chest(200, 100));
        entities.add(Item.Factory.create(200, 200));
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
        border.setDimension(10, RenderingEngine.SCREEN_HEIGHT);
        border.teleport(-border.getWidth() / 2,0);
        worldBorders.add(border);
    }

    private void createBorderRight() {
        Blockade border = new Blockade();
        border.setDimension(10, RenderingEngine.SCREEN_HEIGHT);
        border.teleport(RenderingEngine.SCREEN_WIDTH - border.getWidth(), 0);
        worldBorders.add(border);
    }

    private void createBorderBottom() {
        Blockade border = new Blockade();
        border.setDimension(RenderingEngine.SCREEN_WIDTH, 10);
        border.teleport(0, 0);
        worldBorders.add(border);
    }

    private void createBorderTop() {
        Blockade border = new Blockade();
        border.setDimension(RenderingEngine.SCREEN_WIDTH, 10);
        border.teleport(0, RenderingEngine.SCREEN_HEIGHT - border.getHeight());
        worldBorders.add(border);
    }

    private void loadBackGround() {
        try {
            biomeImages[0] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(FOREST_MAP_PATH));
            biomeImages[1] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SNOW_MAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
