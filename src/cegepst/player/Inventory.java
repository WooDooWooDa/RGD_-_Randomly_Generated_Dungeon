package cegepst.player;

import cegepst.objects.Item;

import java.util.ArrayList;

public class Inventory {

    private int money = 0;
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addMoney(int moneyToAdd) {
        money += moneyToAdd;
    }

    public int getMoneyCount() {
        return money;
    }
}
