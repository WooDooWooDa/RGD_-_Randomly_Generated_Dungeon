package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entities.CollidableRepository;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.Random;

public class Rock extends StaticEntity {

    private Image image;

    public Rock(int x, int y) {
        teleport(x, y);
        setDimension(32, 32);
        loadImages();
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
    }

    private void loadImages() {
        image = GameResources.getInstance().getBufferedImage("rocks").getSubimage(new Random().nextInt(2) == 1 ? 0 : 32, 0, width, height);
    }
}
