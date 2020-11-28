package cegepst.player;

import cegepst.MessageAnnouncer;
import cegepst.engine.SoundPlayer;
import cegepst.objects.Armor;
import cegepst.objects.Bow;
import cegepst.objects.Item;
import cegepst.objects.Sword;

import java.util.ArrayList;

public class Inventory {

    private Sword equippedSword;
    private Armor equippedArmor;
    private final Bow equippedBow;
    private int nbTags;

    public Inventory() {
        equippedBow = new Bow();
    }

    protected void addTag() {
        nbTags++;
    }

    protected int getNbTags() {
        return nbTags;
    }

    protected void useAllTag() {
        nbTags = 0;
    }

    protected Bow getBow() {
        return equippedBow;
    }

    protected Sword getSword() {
        return equippedSword;
    }

    protected boolean swordCanAttack() {
        if (equippedSword == null) {
            MessageAnnouncer.setMessage("You have no sword...", 200);
            return false;
        }
        return equippedSword.canAttack();
    }

    protected ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        if (equippedArmor != null) {
            items.add(equippedArmor);
        }
        if (equippedSword != null) {
            items.add(equippedSword);
        }
        return items;
    }

    protected void addItem(Item item) {
        if (item instanceof Sword) {
            if (((Sword) item).isBetterThan(equippedSword)) {
                equippedSword = (Sword)item;
                SoundPlayer.play("sounds/armorEquip.wav");
                PlayerStats.BONUS_DAMAGE = equippedSword.getDamage();
                return;
            }
        }
        if (item instanceof Armor) {
            if (((Armor) item).isBetterThan(equippedArmor)) {
                equippedArmor = (Armor)item;
                SoundPlayer.play("sounds/armorEquip.wav");
                PlayerStats.BONUS_ARMOR = equippedArmor.getArmorPoint();
                PlayerStats.BONUS_HEALTH = equippedArmor.getHealthPoint();
                PlayerStats.MAX_HEALTH = PlayerStats.BASE_MAX_HEALTH + PlayerStats.BONUS_HEALTH;
                return;
            }
        }
        SoundPlayer.play("sounds/coin.wav");
        MessageAnnouncer.setMessage("Item converted in gem!", 200);
        PlayerStats.GEM += item.getLevel() * 2;
    }
}
