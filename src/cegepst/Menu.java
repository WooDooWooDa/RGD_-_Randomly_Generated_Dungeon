package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.RenderingEngine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu {

    private static final String MENU_PATH = "images/menu.png";
    private static final String MENU2_PATH = "images/optionMenu.png";
    private BufferedImage[] menus = new BufferedImage[3];
    private final int x;
    private final int y;
    private static int currentMenu = 1;
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
        menuCooldown++;
        if (menuCooldown > 40) {
            menuCooldown = 40;
        }
        if (!isOpen()) {
            resetMouse();
            currentMenu = 1;
            return;
        }
        int camX = Camera.getInstance().getX();
        int camY = Camera.getInstance().getY();
        if (currentMenu == 1) {
            if (topBoxClicked(camX, camY)) {
                opened = !opened;
            }
            if (middleBoxClicked(camX, camY)) {
                currentMenu = 2;
            }
            if (bottomBoxClicked(camX, camY)) {
                quit = !quit;
            }
        } else if (currentMenu == 2) {
            if (topBoxClicked(camX, camY)) {
                opened = !opened;
            }
            if (middleBoxClicked(camX, camY)) {
                RenderingEngine.getInstance().getScreen().toggleFullScreen();
            }
            if (bottomBoxClicked(camX, camY)) {
                currentMenu = 1;
            }
        }
        resetMouse();

    }

    public void draw(Buffer buffer) {
        if (currentMenu == 1) {
            buffer.drawImage(menus[1], Camera.getInstance().getX() + x , Camera.getInstance().getY() + y);
        } else if (currentMenu == 2) {
            buffer.drawImage(menus[2],Camera.getInstance().getX() + x, Camera.getInstance().getY() + y);
        }
    }

    private void resetMouse() {
        Mouse.mouseX = 0;
        Mouse.mouseY = 0;
    }

    private boolean topBoxClicked(int camX, int camY) {
        return (camX + Mouse.mouseX >= camX + x + 95 && camX + Mouse.mouseX <= camX+  x + menus[1].getWidth() - 95) && (camY + Mouse.mouseY >= camY + y + 25 && camY + Mouse.mouseY <= camY + y + 75);
    }

    private boolean bottomBoxClicked(int camX, int camY) {
        return (camX + Mouse.mouseX >= camX + x + 95 && camX + Mouse.mouseX <= camX + x + menus[1].getWidth() - 95) && (camY + Mouse.mouseY >= camY + y + 165 && camY + Mouse.mouseY <= camY + y + 215);
    }

    private boolean middleBoxClicked(int camX, int camY) {
        return (camX + Mouse.mouseX >= camX + x + 95 && camX + Mouse.mouseX <= camX + x + menus[1].getWidth() - 95) && (camY + Mouse.mouseY >= camY + y + 100 && camY + Mouse.mouseY <= camY + y + 150);
    }

    private void loadImage() {
        try {
            menus[1] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MENU_PATH));
            menus[2] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MENU2_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
