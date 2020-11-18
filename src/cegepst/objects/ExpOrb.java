package cegepst.objects;

import cegepst.GameResources;
import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.Random;

public class ExpOrb extends StaticEntity {

    private Image image;
    private int expValue;

    public ExpOrb(int x, int y) {
        teleport(x, y);
        setInteractable();
        setDimension(16, 16);
        loadItemImage();
        expValue = new Random().nextInt(10) + 1;
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
    }

    private void loadItemImage() {
        image = GameResources.getInstance().getBufferedImage("items").getSubimage(9 * width, 11 * height, width, height);
    }
}
