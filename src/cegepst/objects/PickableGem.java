package cegepst.objects;

import cegepst.GameResources;
import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class PickableGem extends StaticEntity {

    private Image itemImage;
    private final int value;

    public PickableGem(int x, int y) {
        teleport(x, y);
        setInteractable();
        setDimension(16, 16);
        loadItemImage();
        value = new Random().nextInt(5) + 1;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(itemImage, x, y);
    }

    private void loadItemImage() {
        itemImage = GameResources.getInstance().getBufferedImage("items").getSubimage(10 * width, 11 * height, width, height);
    }

}
