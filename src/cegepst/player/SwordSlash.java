package cegepst.player;

import cegepst.GameSettings;
import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.StaticEntity;
import cegepst.objects.Sword;

import java.awt.*;

public class SwordSlash extends StaticEntity {

    public SwordSlash(Player player, Sword sword) {
        setDimensionForDirection(player.getDirection(), sword.getAttackRange());
        placeAccordingToPlayer(player);
    }

    public void update(Player player) {
        placeAccordingToPlayer(player);
    }

    @Override
    public void draw(Buffer buffer) {
        if (GameSettings.SHOW_COLLISION) {
            buffer.drawRectangle(x, y, width, height, new Color(255,0,0,75));
        }
    }

    private void placeAccordingToPlayer(Player player) {
        switch (player.getDirection()) {
            case DOWN -> teleport(player.getX() - 4, player.getY() + 16);
            case UP -> teleport(player.getX() - 4, player.getY() - 16);
            case RIGHT -> teleport(player.getX() + 16, player.getY() - 4);
            case LEFT -> teleport(player.getX() - 16, player.getY() - 4);
        }
    }

    private void setDimensionForDirection(Direction direction, int range) {
        if (direction == Direction.DOWN || direction == Direction.UP) {
            setDimension(40, range + 16);
            return;
        }
        setDimension(range + 16, 40);
    }
}
