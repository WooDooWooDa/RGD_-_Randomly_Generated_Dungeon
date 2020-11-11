package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.RenderingEngine;
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
        createBorders();
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(backGround, 0, 0);
        for (Blockade worldBorder : worldBorders) {
            worldBorder.draw(buffer);
        }
    }

    private void createBorders() {
        createBorderTop();
        createBorderBottom();
        createBorderRight();
        createBorderLeft();
    }

    private void createBorderLeft() {
        Blockade border = new Blockade();
        border.setDimension(10, RenderingEngine.SCREEN_HEIGHT);
        border.teleport(-border.getWidth() / 2,0);
        worldBorders.add(border);
    }

    private void createBorderRight() {
        Blockade border = new Blockade();
        border.setDimension(10, RenderingEngine.SCREEN_HEIGHT);
        border.teleport(RenderingEngine.SCREEN_WIDTH - border.getWidth(), 0);
        worldBorders.add(border);
    }

    private void createBorderBottom() {
        Blockade border = new Blockade();
        border.setDimension(RenderingEngine.SCREEN_WIDTH, 10);
        border.teleport(0, 0);
        worldBorders.add(border);
    }

    private void createBorderTop() {
        Blockade border = new Blockade();
        border.setDimension(RenderingEngine.SCREEN_WIDTH, 10);
        border.teleport(0, RenderingEngine.SCREEN_HEIGHT - border.getHeight());
        worldBorders.add(border);
    }

    private void loadBackGround() {
        try {
            backGround = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
