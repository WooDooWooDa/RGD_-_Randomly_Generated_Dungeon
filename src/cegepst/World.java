package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.entities.Blockade;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class World {

    private static final String MAP_PATH = "images/map.png";
    private Image backGround;
    private ArrayList<Blockade> worldBorders;

    public World() {
        loadBackGround();
        worldBorders = new ArrayList<>();
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(backGround, 0, 0);
        for (Blockade worldBorder : worldBorders) {
            worldBorder.draw(buffer);
        }
    }

    private void loadBackGround() {
        try {
            backGround = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
