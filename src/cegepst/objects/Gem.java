package cegepst.objects;

import cegepst.GameResources;
import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.Random;

public class Gem extends StaticEntity {

    private Image image;
    private final int value;

    public Gem(int x, int y) {
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
        buffer.drawImage(image, x, y);
    }

    private void loadItemImage() {
        image = GameResources.getInstance().getBufferedImage("items").getSubimage(10 * width, 11 * height, width, height);
    }

}
