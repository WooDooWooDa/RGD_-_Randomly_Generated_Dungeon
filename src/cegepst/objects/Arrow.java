package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Arrow extends MovableEntity {

    private final int damage;
    private Image[] arrows = new Image[4];
    private Image arrow;

    public Arrow(Direction direction, int x, int y, int damage) {
        this.damage = damage;
        loadImage();
        teleport(x, y);
        setSpeed(2);
        setDirection(direction);
        setDimensionForDirection();
        updatePositionOrientation();
    }

    public int dealDamage() {
        return damage;
    }

    @Override
    public void update() {
        super.update();
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
                arrow = arrows[2];
                break;
            case DOWN:
                teleport(x + 9, y + 32);
                arrow = arrows[3];
                break;
            case LEFT:
                teleport(x, y + 9);
                arrow = arrows[0];
                break;
            case RIGHT:
                teleport(x + 32, y + 9);
                arrow = arrows[1];
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
            arrows[0] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowleft.png"));
            arrows[1] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowright.png"));
            arrows[2] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowup.png"));
            arrows[3] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/arrowdown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
