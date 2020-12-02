package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.entities.CollidableRepository;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Merchant extends StaticEntity {

    private boolean traded = false;
    private Image image;

    public Merchant(int x, int y) {
        setDimension(27, 29);
        teleport(x, y);
        setInteractable();
        CollidableRepository.getInstance().registerEntity(this);
        loadImage();
    }

    public boolean hasTraded() {
        return traded;
    }

    public Item trade() {
        traded = true;
        return Item.Factory.create(width * 2 + (x - width), new Random().nextInt(20) + (y + height / 2));
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/merchant.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
