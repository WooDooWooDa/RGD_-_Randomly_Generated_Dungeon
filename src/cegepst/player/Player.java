package cegepst.player;

import cegepst.Animator;
import cegepst.MessageAnnouncer;
import cegepst.enemies.Enemy;
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
    private static final String ATTACK_PATH = "images/playerAttack.png";
    private static final int INTERACT_COOLDOWN = 25;
    private int interactCooldown = INTERACT_COOLDOWN;

    private WalkingAnimator animator;
    private Animator attackAnimator;
    private Inventory inventory;
    private StaticEntity interactRange;
    private SwordSlash swordSlash;
    private Hud hud;

    private boolean isAttackingBySword = false;
    private boolean isAttackingByBow = false;
    private int attack = 4;

    public Player(MovementController gamePad) {
        super(gamePad);
        PlayerStats.reset();
        setDimension(32,32);
        teleport(50 ,50);
        setSpeed(PlayerStats.BASE_SPEED);
        PlayerStats.HEALTH = PlayerStats.MAX_HEALTH;
        addComplements();
    }

    public boolean isAlive() {
        return PlayerStats.HEALTH > 0;
    }

    public boolean hasAllKeys() {
        return inventory.getNbTags() == 3;
    }

    public boolean canInteract() {
        return interactCooldown == INTERACT_COOLDOWN;
    }

    public boolean canAttack() {
        return inventory.swordCanAttack();
    }

    public boolean canShot() {
        return inventory.getBow().canShot();
    }
    
    public void receiveDamage(int damage) {
        SoundPlayer.play("sounds/damageReceive.wav");
        int damageReceived = damage - (PlayerStats.BASE_ARMOR + PlayerStats.BONUS_ARMOR);
        if (damageReceived <= 0) {
            damageReceived = 0;
        }
        PlayerStats.HEALTH -= damageReceived;
    }

    public MovableEntity shotArrow() {
        isAttackingByBow = true;
        inventory.getBow().reset();
        SoundPlayer.play("sounds/arrowshot.wav");
        return new Arrow(this, inventory.getBow().getDamage());
    }

    public void swordAttack(ArrayList<StaticEntity> worldEnemies) {
        SoundPlayer.play("sounds/swordSlash.wav");
        if (swordSlash != null) {
            inventory.getSword().reset();
            isAttackingBySword = true;
            swordSlash.update(this);
            for (StaticEntity entity : worldEnemies) {
                if (entity instanceof Enemy && entity.intersectWith(swordSlash)) {
                    ((Enemy) entity).receivedDamage(PlayerStats.BASE_DAMAGE + PlayerStats.BONUS_DAMAGE);
                }
            }
        }
    }

    public ArrayList<StaticEntity> interact(ArrayList<StaticEntity> worldEntities) {
        interactCooldown = 0;
        updateInteractRange();
        ArrayList<StaticEntity> newEntities = new ArrayList<>();
        ArrayList<StaticEntity> removedEntities = new ArrayList<>();
        for (StaticEntity entity : worldEntities) {
            if (entity.intersectWith(interactRange) && entity.isInteractable()) {
                if (entity instanceof Chest && !((Chest) entity).isOpened()) {
                    newEntities.addAll(((Chest) entity).openChest());
                    break;
                }
                if (entity instanceof Gem) {
                    SoundPlayer.play("sounds/coin.wav");
                    PlayerStats.GEM += ((Gem) entity).getValue();
                    removedEntities.add(entity);
                    break;
                }
                if (entity instanceof Item) {
                    inventory.addItem((Item)entity);
                    if (entity instanceof Sword) {
                        swordSlash = new SwordSlash(this, (Sword)entity);
                    }
                    removedEntities.add(entity);
                    break;
                }
                if (entity instanceof ExpOrb) {
                    SoundPlayer.play("sounds/expOrb.wav");
                    PlayerStats.PLAYER_EXP += ((ExpOrb) entity).getExpValue();
                    removedEntities.add(entity);
                    break;
                }
                if (entity instanceof Tag) {
                    SoundPlayer.play("sounds/tagAcquired.wav");
                    inventory.addTag();
                    MessageAnnouncer.setMessage("tag trouver  " + inventory.getNbTags() + " I 3", 200);
                    removedEntities.add(entity);
                    break;
                }
            }
        }
        worldEntities.removeAll(removedEntities);
        worldEntities.addAll(newEntities);
        return worldEntities;
    }

    @Override
    public void update() {
        super.update();
        if (PlayerStats.HEALTH <= 0) {
            PlayerStats.HEALTH = 0;
        }
        if (PlayerStats.PLAYER_EXP >= PlayerStats.NEXT_LVL_EXP) {
            PlayerStats.PLAYER_EXP = 0;
            SoundPlayer.play("sounds/expLvlUp.wav");
            PlayerStats.LVL++;
            MessageAnnouncer.setMessage("Monster difficulty increased!", 250);
            // TODO: 2020-11-20 update le next lvl exp
        }
        inventory.getBow().update();
        if (inventory.getSword() != null) {
            inventory.getSword().update();
        }
        interactCooldown++;
        if (interactCooldown > INTERACT_COOLDOWN) {
            interactCooldown = INTERACT_COOLDOWN;
        }
        if (attack <= 0) {
            isAttackingByBow = false;
            isAttackingBySword = false;
            attack = 4;
        }
        if (isAttackingBySword) {
            attack--;
            //attackAnimator.animateSword(attack); // TODO: 2020-11-18 attack animator (apres son animation, remet isAttacking a false)
            return;
        } else if (isAttackingByBow) {
            attack--;
            //attackAnimator.animateBow(attack);
            return;
        }
        moveAccordingToController();
        animator.update();
    }

    @Override
    public void draw(Buffer buffer) {
        interactRange.draw(buffer);
        if (swordSlash != null) {
            swordSlash.draw(buffer);
        }
        buffer.drawImage(animator.animate(getDirection()), x, y);
    }

    public void drawHud(Buffer buffer) {
        hud.draw(buffer);
    }

    protected ArrayList<Item> getItems() {
        return inventory.getItems();
    }

    private void updateInteractRange() {
        interactRange.teleport(x - width / 2, y - height / 2);
    }

    private void addComplements() {
        inventory = new Inventory();
        hud = new Hud(this);
        interactRange = new InteractRange(width * 2, height * 2);
        animator = new WalkingAnimator(this, SPRITE_PATH, 0, 128);
        //attackAnimator = new Animator(this, ATTACK_PATH, 4);
    }
}
