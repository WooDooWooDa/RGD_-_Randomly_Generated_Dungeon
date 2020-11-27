package cegepst.objects;

import cegepst.engine.Buffer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class WitherSkull extends Projectile {

    private Image image;

    public WitherSkull(int x, int y, int dmg) {
        teleport(x, y);
        setDimension(21, 21);
        setMaxDistanceTraveled(500);
        setSpeed(2);
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
        buffer.drawImage(image, x, y);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/witherSkull.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
