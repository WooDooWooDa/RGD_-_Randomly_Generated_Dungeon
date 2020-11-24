package cegepst.objects;

import cegepst.GameResources;
import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;

public class Tag extends StaticEntity {

    private Image image;

    public Tag(int x, int y) {
        teleport(x, y);
        setInteractable();
        setDimension(16, 16);
        loadItemImage();
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
    }

    private void loadItemImage() {
        image = GameResources.getInstance().getBufferedImage("items").getSubimage(3 * width, 10 * height, width, height);
    }
}
