package cegepst.player;

import cegepst.GameSettings;
import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;


public class InteractRange extends StaticEntity {
    @Override
    public void draw(Buffer buffer) {
        if (GameSettings.SHOW_COLLISION) {
            buffer.drawRectangle(x, y, width, height, new Color(255, 0, 0, 75));
        }
    }
}
