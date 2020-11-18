package cegepst.objects;

import cegepst.GameResources;
import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PickableGem extends StaticEntity {

    private Image itemImage;
    private int value = 10;

    public PickableGem(int x, int y) {
        teleport(x, y);
        setInteractable();
        setDimension(16, 16);
        loadItemImage(10, 11);
    }

    public int getValue() {
        return value;
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(itemImage, x, y);
    }

    private void loadItemImage(int xPositionInSheet, int yPositionInSheet) {
        itemImage = GameResources.getInstance().getBufferedImage("items").getSubimage(xPositionInSheet * width, yPositionInSheet * height, width, height);
    }

}
