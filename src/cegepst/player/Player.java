package cegepst.player;

import cegepst.engine.entities.MovableEntity;
import cegepst.objects.*;
import cegepst.WalkingAnimator;
import cegepst.engine.Buffer;
import cegepst.engine.SoundPlayer;
import cegepst.engine.controls.MovementController;
import cegepst.engine.entities.ControllableEntity;
import cegepst.engine.entities.StaticEntity;

import java.util.ArrayList;


public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/player.png";
    private static final int INTERACT_COOLDOWN = 25;
    private static final int SHOT_RATE = 50;
    private int shotRateCooldown = SHOT_RATE;
    private int interactCooldown = INTERACT_COOLDOWN;

    private final WalkingAnimator animator;
    private Inventory inventory;
    private StaticEntity interactRange;
    private final Hud hud;

    public Player(MovementController gamePad) {
        super(gamePad);
        setDimension(32,32);
        teleport(100 ,100);
        setSpeed(PlayerStats.BASE_SPEED);
        inventory = new Inventory();
        hud = new Hud(this);
        PlayerStats.HEALTH = PlayerStats.MAX_HEALTH;
        interactRange = new InteractRange(width * 2, height * 2);
        animator = new WalkingAnimator(this, SPRITE_PATH, 0, 128);
        animator.setAnimationSpeed(8);
    }

    public boolean canInteract() {
        return interactCooldown == INTERACT_COOLDOWN;
    }

    public boolean canShot() {
        return shotRateCooldown == SHOT_RATE; // TODO: 2020-11-16 attack rate from bow in item inventory
    }
    
    public void receiveDamage(int damage) {
        SoundPlayer.play("sounds/damageReceive.wav");
        int damageReceived = damage - (PlayerStats.BASE_ARMOR + PlayerStats.BONUS_ARMOR);
        if (damageReceived <= 0) {
            damageReceived = 0;
        }
        PlayerStats.HEALTH -= damageReceived;
    }

    public ArrayList<StaticEntity> interact(ArrayList<StaticEntity> worldEntities) {
        interactCooldown = 0;
        updateInteractRange();
        ArrayList<StaticEntity> newEntities = new ArrayList<>();
        ArrayList<StaticEntity> removedEntities = new ArrayList<>();
        for (StaticEntity entity : worldEntities) {
            if (entity.intersectWith(interactRange)) {
                if (entity.isInteractable()) {
                    if (entity instanceof Chest) {
                        if (!((Chest) entity).isOpened()) {
                            newEntities.addAll(((Chest) entity).openChest());
                            break;
                        }
                    }
                    if (entity instanceof Gem) {
                        SoundPlayer.play("sounds/coin.wav");
                        PlayerStats.GEM += ((Gem) entity).getValue();
                        removedEntities.add(entity);
                        break;
                    }
                    if (entity instanceof Item) {
                        inventory.addItem((Item)entity);
                        removedEntities.add(entity);
                        break;
                    }
                    if (entity instanceof ExpOrb) {
                        //play exp sound
                        PlayerStats.PLAYER_EXP += ((ExpOrb) entity).getExpValue();
                        removedEntities.add(entity);
                        break;
                    }
                }
            }
        }
        worldEntities.removeAll(removedEntities);
        worldEntities.addAll(newEntities);
        return worldEntities;
    }
    
    public MovableEntity shotArrow() {
        shotRateCooldown = 0;
        SoundPlayer.play("sounds/arrowshot.wav");
        return new Arrow(this);
    }

    @Override
    public void update() {
        super.update();
        interactCooldown++;
        shotRateCooldown++;
        if (shotRateCooldown > SHOT_RATE) {
            shotRateCooldown = SHOT_RATE;
        }
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

    protected ArrayList<Item> getItems() {
        return inventory.getItems();
    }

    private void updateInteractRange() {
        interactRange.teleport(x - width / 2, y - height / 2);
    }
}
