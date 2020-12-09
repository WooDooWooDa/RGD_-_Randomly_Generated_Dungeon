package cegepst.enemies;

import cegepst.GameResources;
import cegepst.engine.Buffer;
import cegepst.engine.entities.CollidableRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class ZombieSpawner extends Enemy {

    private int maxHealth = 150;

    private int spawnRate = 1000;

    private int spawn = spawnRate;
    private Random random = new Random();
    private Image image;

    public ZombieSpawner(int x, int y) {
        teleport(x, y);
        setDimension(32, 32);
        health = maxHealth;
        loadImage();
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void upgrade() {
        maxHealth += 100;
        health = maxHealth;
        spawnRate -= 75;
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
        spawn = spawnRate;
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
        image = GameResources.getInstance().getImage("ZombieSpawner");
    }
}
