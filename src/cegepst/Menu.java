package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.RenderingEngine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Menu {

    private static final String MENU_PATH = "images/menu.png";
    private Image image;
    private final int x;
    private final int y;
    private boolean opened = false;
    private int menuCooldown = 50;

    public Menu() {
        loadImage();
        x = 200;
        y = 150;
    }

    public boolean isOpen() {
        return opened;
    }

    public void toggleMenu() {
        menuCooldown = 0;
        opened = !opened;
        if (opened) {
            RenderingEngine.getInstance().getScreen().showCursor();
        } else {
            RenderingEngine.getInstance().getScreen().hideCursor();
        }
    }

    public boolean CanBeOpen() {
        return menuCooldown == 50;
    }

    public void update() {
        menuCooldown++;
        if (menuCooldown > 50) {
            menuCooldown = 50;
        }
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(image, x , y);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MENU_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
