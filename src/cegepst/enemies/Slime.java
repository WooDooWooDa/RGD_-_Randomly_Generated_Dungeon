package cegepst.enemies;

import cegepst.Animator;
import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.entities.MovableEntity;

public class Slime extends MovableEntity {

    private WalkingPath path;
    private Animator animator;

    public Slime() {
        teleport(400, 500);
        setDimension(32, 32);
        setSpeed(1);
        animator = new Animator(this, "images/slime.png", 4);
        animator.setAnimationSpeed(12);
        path = new WalkingPath(WalkingPath.SQUARE);
        setWalkingPathLength();
        CollidableRepository.getInstance().registerEntity(this);
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
        path.setHorizontalWalk(100);
        path.setVerticalWalk(100);
    }
}
