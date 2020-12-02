package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.entities.CollidableRepository;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.Random;

public class Merchant extends StaticEntity {

    private boolean traded = false;

    public Merchant(int x, int y) {
        setDimension(20, 20);
        teleport(x, y);
        setInteractable();
        CollidableRepository.getInstance().registerEntity(this);
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
        buffer.drawRectangle(x, y, width, height, Color.white);
    }
}
