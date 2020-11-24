package cegepst.player;

import cegepst.objects.Armor;
import cegepst.objects.Bow;
import cegepst.objects.Item;
import cegepst.objects.Sword;

import java.util.ArrayList;

public class Inventory {

    private Sword equippedSword;
    private Armor equippedArmor;
    private final Bow equippedBow;
    private int nbKeys;

    public Inventory() {
        equippedBow = new Bow();
    }

    protected void addKey() {
        nbKeys++;
    }

    protected int getNbKeys() {
        return nbKeys;
    }

    protected void useAllKeys() {
        nbKeys = 0;
    }

    protected Bow getBow() {
        return equippedBow;
    }

    protected Sword getSword() {
        return equippedSword;
    }

    protected boolean swordCanAttack() {
        if (equippedSword == null) {
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
                PlayerStats.BONUS_DAMAGE = equippedSword.getDamage();
                return;
            }
        }
        if (item instanceof Armor) {
            if (((Armor) item).isBetterThan(equippedArmor)) {
                equippedArmor = (Armor)item;
                PlayerStats.BONUS_ARMOR = equippedArmor.getArmorPoint();
                PlayerStats.BONUS_HEALTH = equippedArmor.getHealthPoint();
                PlayerStats.MAX_HEALTH = PlayerStats.BASE_MAX_HEALTH + PlayerStats.BONUS_HEALTH;
                return;
            }
        }
        PlayerStats.GEM += item.getLevel() * 2;
    }
}
