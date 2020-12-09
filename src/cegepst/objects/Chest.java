package cegepst.objects;

import cegepst.GameResources;
import cegepst.World;
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

    private Image closedChest;
    private Image openedChest;
    private final Blockade chestBase;
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
            items.add(new Gem(rand.nextInt(width * 2) + (x - width), rand.nextInt(20) + (y + height / 2)));
        }
        if (rand.nextInt() <= 5) {
            items.add(Item.Factory.create(width * 2 + (x - width), rand.nextInt(20) + (y + height / 2)));
        }
        if (y >= World.WORLD_HEIGHT - height * 2) {
            for (StaticEntity item : items) {
                item.teleport(item.getX(), item.getY() - 15);
            }
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
        closedChest = GameResources.getInstance().getImage("closeChest");
        openedChest = GameResources.getInstance().getImage("openChest");
    }
}
