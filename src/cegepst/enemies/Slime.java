package cegepst.enemies;

import cegepst.Animator;
import cegepst.engine.Buffer;
import cegepst.engine.SoundPlayer;
import cegepst.engine.entities.MovableEntity;

import java.util.Random;

public class Slime extends MovableEntity {

    private WalkingPath path;
    private Animator animator;

    public Slime(int x, int y, int walkingPathMode) {
        teleport(x, y);
        setDimension(32, 32);
        setSpeed(1);
        animator = new Animator(this, "images/slime.png", 4);
        animator.setAnimationSpeed(16);
        path = new WalkingPath(walkingPathMode);
        setWalkingPathLength();
    }
    
    public void update(int playerX, int playerY) {
        animator.update();
        move(path.follow());
        playSoundIfClose(playerX, playerY);
    }

    private void playSoundIfClose(int playerX, int playerY) {
        if ((playerX >= (x - width * 2) && playerX <= (x + width * 3)) && (playerY >= (y - height * 2) && playerY <= (y + height * 3))) {
            Random rand = new Random();
            int chance = rand.nextInt(200) + 1;
            if (chance <= 1) {
                SoundPlayer.play("sounds/slimeWalking.wav");
            }
        }
    }

    @Override
    public void draw(Buffer buffer) {
        drawHitBox(buffer);
        buffer.drawImage(animator.animate(), x, y);
    }

    private void setWalkingPathLength() {
        Random rand = new Random();
        path.setHorizontalWalk(rand.nextInt(100) + 100);
        path.setVerticalWalk(rand.nextInt(100) + 100);
    }
}
