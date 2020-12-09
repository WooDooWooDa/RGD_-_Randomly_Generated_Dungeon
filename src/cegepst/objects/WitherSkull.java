package cegepst.objects;

import cegepst.GameResources;
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
        image = GameResources.getInstance().getImage("witherSkull");
    }

}
