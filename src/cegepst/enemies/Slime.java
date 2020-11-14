package cegepst.enemies;

import cegepst.Animator;
import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
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

    @Override
    public void update() {
        animator.update();
        move(path.follow());
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(animator.animate(), x, y);
    }

    private void setWalkingPathLength() {
        Random rand = new Random();
        path.setHorizontalWalk(rand.nextInt(100) + 100);
        path.setVerticalWalk(rand.nextInt(100) + 100);
    }
}
