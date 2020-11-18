package cegepst.objects;

public class Bow {

    private int damage;
    private static final int SHOT_RATE = 50;
    private int shotRateCooldown = SHOT_RATE;

    public Bow() {
        damage = 15;
    }

    public int getDamage() {
        return damage;
    }

    public void reset() {
        shotRateCooldown = 0;
    }

    public boolean canShot() {
        return shotRateCooldown == SHOT_RATE;
    }

    public void update() {
        shotRateCooldown++;
        if (shotRateCooldown > SHOT_RATE) {
            shotRateCooldown = SHOT_RATE;
        }
    }
}
