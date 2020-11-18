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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBufferedImagesResources() {
        try {
            allBufferedImages.put("items", ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/allItems.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
