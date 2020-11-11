package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class PickableMoney extends StaticEntity {

    private static final String MONEY_PATH = "images/money.png";
    private Image image;
    private int value = 10;

    public PickableMoney(int x, int y) {
        teleport(x, y);
        setDimension(10, 10);
        loadImage();
    }

    public int getValue() {
        return value;
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MONEY_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
