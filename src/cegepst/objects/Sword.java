package cegepst.objects;

import java.awt.*;

public class Sword extends Item {

    private int attackDamage;
    private int attackRate;

    public Sword(int x, int y, int level) {
        super(level, getSwordName(), getImage());
        teleport(x, y);
        setDimension(16, 16);
        setAttackPoint(level);
    }

    public boolean isBetterThan(Sword other) {
        if (this.attackDamage > other.attackDamage) {
            return true;
        }
        return false;
    }

    public boolean canAttack() {
        return true;
    }

    private static Image getImage() {
        return null;
    }

    private static String getSwordName() {
        return "name";
    }

    private void setAttackPoint(int level) {
    }
}
