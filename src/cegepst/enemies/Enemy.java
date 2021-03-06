package cegepst.enemies;

import cegepst.engine.entities.MovableEntity;
import cegepst.engine.entities.StaticEntity;
import cegepst.objects.ExpOrb;
import cegepst.objects.Gem;
import cegepst.objects.Item;
import cegepst.player.PlayerStats;

import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy extends MovableEntity {

    protected int level = 1;
    protected int health;
    protected int damage;

    public void receivedDamage(int damage) {
        health -= damage;
    }

    public ArrayList<StaticEntity> dies() {
        Random rand = new Random();
        ArrayList<StaticEntity> drop = new ArrayList<>();
        if (this instanceof WitherBoss) {
            drop.add(Item.Factory.createItemOfLvl(x + rand.nextInt(width), y + rand.nextInt(height), PlayerStats.LVL + 1));
            drop.add(Item.Factory.createItemOfLvl(x + rand.nextInt(width), y + rand.nextInt(height), PlayerStats.LVL + 1));
            for (int i = 0; i < 6; i++) {
                drop.add(new ExpOrb(x + rand.nextInt(width), y + rand.nextInt(height)));
            }
        }
        for (int i = 1; i <= rand.nextInt(2) + level; i++) {
            int dropChance = rand.nextInt(200);
            if (dropChance <= 1) {
                drop.add(Item.Factory.create(x + rand.nextInt(width), y + rand.nextInt(height)));
            } else if (dropChance <= 100) {
                drop.add(new ExpOrb(x + rand.nextInt(width), y + rand.nextInt(height)));
            }
            if (this instanceof Slime) {
                return drop;
            }
            drop.add(new Gem(x + rand.nextInt(width), y + rand.nextInt(height)));
        }
        return drop;
    }

    public abstract void upgrade();

    public abstract int dealDamage();

    public abstract boolean canAttack();

    public abstract boolean isAlive();

}
