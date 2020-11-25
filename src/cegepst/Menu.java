package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.RenderingEngine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu {

    public static int mouseX;
    public static int mouseY;

    private static final String MENU_PATH = "images/menu.png";
    private BufferedImage image;
    private final int x;
    private final int y;
    private boolean opened = false;
    private int menuCooldown = 40;

    private boolean quit = false;

    public Menu() {
        loadImage();
        x = 200;
        y = 150;
    }

    public boolean isQuit() {
        return quit;
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
        return menuCooldown == 40;
    }

    public void update() {
        int camX = Camera.getInstance().getX();
        int camY = Camera.getInstance().getY();
        menuCooldown++;
        if (menuCooldown > 40) {
            menuCooldown = 40;
        }
        if (camX + mouseX >= x + 20 && camX + mouseX <= x + image.getWidth() - 20) { // TODO: 2020-11-24 ajust to fit menu button
            opened = !opened;
        }
        if (!isOpen()) {
            mouseX = 0;
            mouseY = 0;
        }
    }

    public void draw(Buffer buffer) {
        buffer.drawText(mouseX + ", " + mouseY, x, y, Color.WHITE);
        buffer.drawImage(image, Camera.getInstance().getX() + x , Camera.getInstance().getY() + y);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MENU_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
