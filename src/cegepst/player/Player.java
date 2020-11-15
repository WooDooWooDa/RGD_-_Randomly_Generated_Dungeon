package cegepst.player;

import cegepst.objects.Chest;
import cegepst.objects.PickableGem;
import cegepst.WalkingAnimator;
import cegepst.engine.Buffer;
import cegepst.engine.SoundPlayer;
import cegepst.engine.controls.MovementController;
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
    private Hud hud;

    public Player(MovementController gamePad) {
        super(gamePad);
        setDimension(32,32);
        teleport(100 ,100);
        setSpeed(PlayerStats.BASE_SPEED);
        inventory = new Inventory();
        hud = new Hud();
        PlayerStats.HEALTH = PlayerStats.MAX_HEALTH;
        interactRange = new InteractRange(width * 2, height * 2);
        animator = new WalkingAnimator(this, SPRITE_PATH, 0, 128);
        animator.setAnimationSpeed(8);
    }

    public boolean canInteract() {
        return interactCooldown == INTERACT_COOLDOWN;
    }

    public void receiveDamage(int damage) {
        SoundPlayer.play("sounds/damageReceive.wav");
        int damageReceived = damage - (PlayerStats.BASE_ARMOR + PlayerStats.BONUS_ARMOR);
        if (damageReceived <= 0) {
            damageReceived = 0;
        }
        PlayerStats.HEALTH -= damageReceived;
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
                        break;
                    }
                }
                if (entity instanceof PickableGem) {
                    SoundPlayer.play("sounds/coin.wav");
                    inventory.addMoney(((PickableGem) entity).getValue());
                    removedEntities.add(entity);
                    break;
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
        buffer.drawImage(animator.animate(getDirection()), x, y);
        hud.draw(buffer);
    }

    private void drawHub(Buffer buffer) {

    }

    private void updateInteractRange() {
        interactCooldown = 0;
        interactRange.teleport(x - width / 2, y - height / 2);
    }
}
