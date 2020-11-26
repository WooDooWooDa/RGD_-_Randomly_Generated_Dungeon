package cegepst.objects;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class WitherSkull extends Projectile {

    private Image image;

    public WitherSkull(int x, int y, int dmg) {
        teleport(x, y);
        setDimension(36, 36);
        setMaxDistanceTraveled(500);
        setDirection(randomDirection());
        setSpeed(5);
        damage = dmg;
        loadImage();
    }

    @Override
    public void update() {
        super.update();
        distanceTraveled++;
        move(getDirection());
    }

    @Override
    public void draw(Buffer buffer) {

    }

    private Direction randomDirection() {
        return switch (new Random().nextInt(4)) {
            case 1 -> Direction.UP;
            case 2 -> Direction.RIGHT;
            case 3 -> Direction.LEFT;
            default -> Direction.DOWN;
        };
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/witherSkull.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
