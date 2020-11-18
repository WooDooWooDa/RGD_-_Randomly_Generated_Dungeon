package cegepst.player;

import cegepst.objects.Armor;
import cegepst.objects.Item;
import cegepst.objects.Sword;

import java.util.ArrayList;

public class Inventory {

    private Sword equippedSword;
    private Armor equippedArmor;

    public Inventory() {}

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
                PlayerStats.BONUS_DAMAGE = equippedSword.getDamage();
                return;
            }
        }
        if (item instanceof Armor) {
            if (((Armor) item).isBetterThan(equippedArmor)) {
                equippedArmor = (Armor)item;
                PlayerStats.BONUS_ARMOR = equippedArmor.getArmorPoint();
                return;
            }
        }
        PlayerStats.GEM += item.getLevel() * 2;
    }
}
