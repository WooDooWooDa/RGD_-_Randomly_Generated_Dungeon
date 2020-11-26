package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Arrow extends Projectile {

    private Image[] arrowsImages = new Image[4];
    private Image arrow;

    public Arrow(Player player, int dmg) {
        loadImage();
        damage = dmg;
        teleport(player.getX(), player.getY());
        setSpeed(2);
        setDirection(player.getDirection());
        setDimensionForDirection();
        updatePositionOrientation();
    }

    @Override
    public void update() {
        super.update();
        distanceTraveled++;
        move(getDirection());
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(arrow, x, y);
    }

    private void updatePositionOrientation() {
        switch (getDirection()) {
            case UP:
                teleport(x + 9, y);
                arrow = arrowsImages[2];
                break;
            case DOWN:
                teleport(x + 9, y + 32);
                arrow = arrowsImages[3];
                break;
            case LEFT:
                teleport(x, y + 9);
                arrow = arrowsImages[0];
                break;
            case RIGHT:
                teleport(x + 32, y + 9);
                arrow = arrowsImages[1];
                break;
        }
    }

    private void setDimensionForDirection() {
        if (getDirection() == Direction.UP || getDirection() == Direction.DOWN) {
            setDimension(9,15);
            return;
        }
        setDimension(15, 9);
    }

    private void loadImage() {
        try {
            arrowsImages[0] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowleft.png"));
            arrowsImages[1] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowright.png"));
            arrowsImages[2] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowup.png"));
            arrowsImages[3] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowdown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
