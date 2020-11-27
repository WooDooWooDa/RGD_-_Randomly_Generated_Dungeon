package cegepst.player;

public class PlayerStats {

    public static int LVL;
    protected static int NEXT_LVL_EXP;
    protected static int PLAYER_EXP;
    protected static int GEM;

    protected static int BASE_MAX_HEALTH;
    protected static int MAX_HEALTH;
    protected static int HEALTH;

    protected static int BASE_DAMAGE;
    protected static int BASE_ARMOR;
    protected static int BASE_SPEED;

    protected static int BONUS_HEALTH;
    protected static int BONUS_DAMAGE;
    protected static int BONUS_ARMOR;

    public static void reset() {
        LVL = 1;
        NEXT_LVL_EXP = 150;
        PLAYER_EXP = 0;
        GEM = 0;
        BASE_MAX_HEALTH = 100;
        MAX_HEALTH = 100;
        BASE_DAMAGE = 15;
        BASE_ARMOR = 0;
        BASE_SPEED = 2;
        BONUS_HEALTH = 0;
        BONUS_DAMAGE = 0;
        BONUS_ARMOR = 0;
    }
}
