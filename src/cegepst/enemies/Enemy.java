package cegepst.enemies;

import cegepst.engine.entities.MovableEntity;
import cegepst.engine.entities.StaticEntity;
import cegepst.objects.ExpOrb;
import cegepst.objects.Gem;

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
        for (int i = 0; i < rand.nextInt(2) + level; i++) {
            if (rand.nextInt(2) == 1) {
                drop.add(new Gem(x + rand.nextInt(width), y + rand.nextInt(height)));
                continue;
            }
            drop.add(new ExpOrb(x + rand.nextInt(width), y + rand.nextInt(height)));
        }
        return drop;
    }

    public abstract int dealDamage();

    public abstract boolean canAttack();

    public abstract boolean isAlive();

}
