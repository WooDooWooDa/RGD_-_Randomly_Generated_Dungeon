package cegepst.player;

import cegepst.Chest;
import cegepst.PickableMoney;
import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.controls.MovementController;
import cegepst.engine.controls.WalkingAnimator;
import cegepst.engine.entities.ControllableEntity;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.ArrayList;


public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/player.png";
    private static final int INTERACT_COOLDOWN = 25;
    private int interactCooldown = INTERACT_COOLDOWN;

    private WalkingAnimator animator;
    private Inventory inventory;
    private StaticEntity interactRange;

    private int health = 100;

    public Player(MovementController gamePad) {
        super(gamePad);
        setDimension(32,32);
        teleport(100 ,100);
        setSpeed(2);
        inventory = new Inventory();
        interactRange = new InteractRange();
        animator = new WalkingAnimator(this, SPRITE_PATH, 0, 128);
        animator.setAnimationSpeed(8);
    }

    public boolean canInteract() {
        return interactCooldown == INTERACT_COOLDOWN;
    }

    public ArrayList<StaticEntity> interact(ArrayList<StaticEntity> gameEntities) {
        updateInteractRange();
        ArrayList<StaticEntity> newEntities = new ArrayList<>();
        ArrayList<StaticEntity> removedEntities = new ArrayList<>();
        for (StaticEntity entity : gameEntities) {
            if (entity.intersectWith(interactRange)) {
                if (entity instanceof Chest) {
                    if (!((Chest) entity).isOpened()) {
                        newEntities.addAll(((Chest) entity).openChest());
                    }
                }
                if (entity instanceof PickableMoney) {
                    inventory.addMoney(((PickableMoney) entity).getValue());
                    removedEntities.add(entity);
                }
            }
        }
        gameEntities.removeAll(removedEntities);
        gameEntities.addAll(newEntities);
        return gameEntities;
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
        interactRange.draw(buffer);
        if (getDirection() == Direction.UP) {
            buffer.drawImage(animator.animateUp(), x, y);
        } else if (getDirection() == Direction.DOWN) {
            buffer.drawImage(animator.animateDown() ,x ,y);
        } else if (getDirection() == Direction.LEFT) {
            buffer.drawImage(animator.animateLeft(), x, y);
        } else if (getDirection() == Direction.RIGHT) {
            buffer.drawImage(animator.animateRight(), x, y);
        }
        buffer.drawText(String.valueOf(inventory.getMoneyCount()), 10, 20, Color.white);
    }

    private void updateInteractRange() {
        interactCooldown = 0;
        interactRange.teleport(x - width / 2, y - height / 2);
        interactRange.setDimension(width * 2, height * 2);
    }
}
