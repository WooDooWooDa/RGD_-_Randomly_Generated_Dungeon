package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.SoundPlayer;
import cegepst.engine.entities.Blockade;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Chest extends StaticEntity {

    private static final String CLOSED_CHEST_PATH = "images/chest.png";
    private static final String OPENED_CHEST_PATH = "images/open_chest.png";
    private Image closedChest;
    private Image openedChest;
    private Blockade chestBase;
    private boolean opened = false;

    public Chest(int x, int y) {
        teleport(x, y);
        setDimension(32,32);
        setInteractable();
        loadImage();
        chestBase = new Blockade();
        chestBase.setDimension(32,10);
        chestBaseFromTop();
    }

    public boolean isOpened() {
        return opened;
    }

    public void chestBaseFromTop() {
        chestBase.teleport(x, y + 22);
    }

    public void chestBaseFromBottom() {
        chestBase.teleport(x, y);
    }

    public ArrayList<StaticEntity> openChest() {
        SoundPlayer.play("sounds/chestOpen.wav");
        opened = true;
        Random rand = new Random();
        ArrayList<StaticEntity> items = new ArrayList<>();
        for (int i = 0; i <= rand.nextInt(3); i++) {
            items.add(new PickableGem(rand.nextInt(width * 2) + (x - width), rand.nextInt(20) + (y + height / 2)));
        }
        if (rand.nextInt() <= 5) {
            items.add(Item.Factory.create(width * 2 + (x - width), rand.nextInt(20) + (y + height / 2)));
        }
        return items;
    }

    @Override
    public void draw(Buffer buffer) {
        if (opened) {
            buffer.drawImage(openedChest, x, y);
        } else {
            buffer.drawImage(closedChest, x, y);
        }
        chestBase.draw(buffer);
    }

    private void loadImage() {
        try {
            closedChest = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(CLOSED_CHEST_PATH));
            openedChest = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(OPENED_CHEST_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
