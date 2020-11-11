package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.controls.MovementController;
import cegepst.engine.controls.WalkingAnimator;
import cegepst.engine.entities.ControllableEntity;
import cegepst.engine.entities.StaticEntity;

import java.util.ArrayList;


public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/player.png";
    private static final int INTERACT_COOLDOWN = 50;

    private WalkingAnimator animator;
    private int interactCooldown = INTERACT_COOLDOWN;

    public Player(MovementController gamePad) {
        super(gamePad);
        setDimension(32,32);
        teleport(100 ,100);
        setSpeed(2);
        animator = new WalkingAnimator(this, SPRITE_PATH, 0, 128);
        animator.setAnimationSpeed(8);
    }

    public boolean canInteract() {
        return interactCooldown == INTERACT_COOLDOWN;
    }

    public void interact(ArrayList<StaticEntity> EntitiesInRange) {
        interactCooldown = 0;

    }

    @Override
    public void update() {
        super.update();
        interactCooldown++;
        if (interactCooldown > INTERACT_COOLDOWN) {
            interactCooldown = INTERACT_COOLDOWN;
        }
        moveAccordingToController();
        animator.update();
    }

    @Override
    public void draw(Buffer buffer) {
        if (getDirection() == Direction.UP) {
            buffer.drawImage(animator.animateUp(), x, y);
        } else if (getDirection() == Direction.DOWN) {
            buffer.drawImage(animator.animateDown() ,x ,y);
        } else if (getDirection() == Direction.LEFT) {
            buffer.drawImage(animator.animateLeft(), x, y);
        } else if (getDirection() == Direction.RIGHT) {
            buffer.drawImage(animator.animateRight(), x, y);
        }

    }
}
