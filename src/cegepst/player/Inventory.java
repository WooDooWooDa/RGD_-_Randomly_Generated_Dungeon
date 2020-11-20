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

    public Inventory() {
        equippedBow = new Bow();
    }

    public Bow getBow() {
        return equippedBow;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        if (equippedArmor != null) {
            items.add(equippedArmor);
        }
        if (equippedSword != null) {
            items.add(equippedSword);
        }
        return items;
    }

    public void addItem(Item item) {
        if (item instanceof Sword) {
            if (((Sword) item).isBetterThan(equippedSword)) {
                equippedSword = (Sword)item;
                return;
            }
        }
        if (item instanceof Armor) {
            if (((Armor) item).isBetterThan(equippedArmor)) {
                equippedArmor = (Armor)item;
                updatePlayerStats();
                return;
            }
        }
        PlayerStats.GEM += item.getLevel() * 2;
    }

    private void updatePlayerStats() {
        PlayerStats.BONUS_DAMAGE = equippedSword.getDamage();
        PlayerStats.BONUS_ARMOR = equippedArmor.getArmorPoint();
        PlayerStats.BONUS_HEALTH = equippedArmor.getHealthPoint();
        PlayerStats.MAX_HEALTH = PlayerStats.BASE_MAX_HEALTH + PlayerStats.BONUS_HEALTH;
    }
}
