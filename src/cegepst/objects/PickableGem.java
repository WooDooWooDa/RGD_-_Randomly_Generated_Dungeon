package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PickableGem extends StaticEntity {

    private static final String MONEY_PATH = "images/items.png";
    private BufferedImage bufferedImage;
    private Image itemImage;
    private int value = 10;

    public PickableGem(int x, int y) {
        teleport(x, y);
        setDimension(16, 16);
        loadSpriteSheet();
        loadItemImage(10, 11);
    }

    public int getValue() {
        return value;
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(itemImage, x, y);
    }

    private void loadSpriteSheet() {
        try {
            bufferedImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MONEY_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadItemImage(int xPositionInSheet, int yPositionInSheet) {
        itemImage = bufferedImage.getSubimage(xPositionInSheet * width, yPositionInSheet * height, width, height);
    }

}
