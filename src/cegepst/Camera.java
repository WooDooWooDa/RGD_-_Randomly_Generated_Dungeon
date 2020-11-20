package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;
import cegepst.engine.entities.StaticEntity;
import cegepst.player.Player;

import java.awt.*;
import java.util.ArrayList;

public class Camera extends MovableEntity {

    private Player playerRef;
    private ArrayList<StaticEntity> allEntities;

    public Camera(Player player, ArrayList<StaticEntity> worldEntities, ArrayList<StaticEntity> worldEnemies) {
        allEntities = new ArrayList<>();
        playerRef = player;
        allEntities.addAll(worldEntities);
        allEntities.addAll(worldEnemies);
        teleport(0, 0);
        setDimension(RenderingEngine.SCREEN_WIDTH, RenderingEngine.SCREEN_HEIGHT);
        setSpeed(player.getSpeed());
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawText(getX() + " " + getY(), 20, 250, Color.red);
    }

    public void update() {
        if (playerRef.hasMoved()) {
            Direction inverse = playerRef.getInvertedDirection();
            int moveX = inverse.getVelocityX(playerRef.getSpeed());
            int moveY = inverse.getVelocityY(playerRef.getSpeed());
            for (StaticEntity entity : allEntities) {
                entity.teleport(entity.getX() + moveX, entity.getY() + moveY);
            }
            playerRef.move(inverse);
        }
    }
}
