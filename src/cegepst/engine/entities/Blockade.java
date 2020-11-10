package cegepst.engine.entities;

import cegepst.GameSettings;
import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;

import java.awt.*;

public class Blockade extends StaticEntity {

    public Blockade() {
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void draw(Buffer buffer) {
        if (GameSettings.SHOW_COLLISION) {
            buffer.drawRectangle(x, y, width, height, new Color(255,0,0,75));
        }
    }
}
