package cegepst;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class GameResources {

    private static GameResources instance;
    private HashMap<String, Image> allImages;
    private HashMap<String, BufferedImage> allBufferedImages;

    public static GameResources getInstance() {
        if (instance == null) {
            instance = new GameResources();
        }
        return instance;
    }

    public BufferedImage getBufferedImage(String key) {
        return allBufferedImages.get(key);
    }

    public Image getImage(String key) {
        return allImages.get(key);
    }

    private GameResources() {
        allImages = new HashMap<>();
        allBufferedImages = new HashMap<>();
        loadImagesResources();
        loadBufferedImagesResources();
    }

    private void loadImagesResources() {
        try {
            allImages.put("playerHud", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/playerHud.png")));
            allImages.put("ZombieSpawner", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/spawner.png")));
            allImages.put("witherSkull", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/witherSkull.png")));
            allImages.put("closeChest", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/chest.png")));
            allImages.put("openChest", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/open_chest.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBufferedImagesResources() {
        try {
            allBufferedImages.put("rocks", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/rock.png")));
            allBufferedImages.put("slime", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/slime.png")));
            allBufferedImages.put("witherBoss", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/witherboss.png")));
            allBufferedImages.put("CharactersSheet", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player.png")));
            allBufferedImages.put("items", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/allItems.png")));
            allBufferedImages.put("forestMap", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/maps/forestMap.png")));
            allBufferedImages.put("snowMap", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/maps/snowMap.png")));
            allBufferedImages.put("desertMap", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/maps/desertMap.png")));
            allBufferedImages.put("bossMap", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/maps/endBossMap.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
