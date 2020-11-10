package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entities.Blockade;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Chest extends StaticEntity {

    private static final String CHEST_PATH = "images/chest.png";
    private Image image;
    private Blockade chestBase;

    public Chest(int x, int y) {
        teleport(x, y);
        loadImage();
        chestBase = new Blockade();
        chestBase.setDimension(32,10);
        chestBaseFromTop();
    }

    public void chestBaseFromTop() {
        chestBase.teleport(x, y + 22);
    }

    public void chestBaseFromBottom() {
        chestBase.teleport(x, y);
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
        chestBase.draw(buffer);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(CHEST_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
