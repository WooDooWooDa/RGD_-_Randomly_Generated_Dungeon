package cegepst.objects;

import cegepst.engine.entities.MovableEntity;

public abstract class Projectile extends MovableEntity {

    protected int damage;
    protected int distanceTraveled = 0;

    private int maxTravelDistance = 0;

    public int dealDamage() {
        return damage;
    }

    public boolean hasReachMaxDistance() {
        return distanceTraveled > maxTravelDistance;
    }

    public void setMaxDistanceTraveled(int distance) {
        maxTravelDistance = distance;
    }

}
