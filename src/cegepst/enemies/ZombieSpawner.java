package cegepst.enemies;

import cegepst.engine.Buffer;
import cegepst.engine.entities.CollidableRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class ZombieSpawner extends Enemy {

    public int maxHealth = 150;

    private final int SPAWN_RATE = 1000;

    private int spawn = SPAWN_RATE;
    private Random random = new Random();
    private Image image;

    public ZombieSpawner(int x, int y) {
        teleport(x, y);
        setDimension(32, 32);
        health = maxHealth;
        loadImage();
        CollidableRepository.getInstance().registerEntity(this);
    }

    public int dealDamage() {
        return 0;
    }

    public boolean canAttack() {
        return spawn == 0 && random.nextInt(100) <= 1;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Zombie spawn() {
        spawn = SPAWN_RATE;
        int newZombieX = (x - width) + (random.nextInt(width * 3));
        int newZombieY = (y - height) + (random.nextInt(height * 3));
        return new Zombie(newZombieX, newZombieY);
    }

    @Override
    public void update() {
        --spawn;
        if (spawn < 0) {
            spawn = 0;
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
        if (health < maxHealth) {
            buffer.drawRectangle(x , y - 10, (int)(32 * ((double)health / maxHealth)), 3, Color.RED);
        }
    }

    private void loadImage() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/spawner.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
