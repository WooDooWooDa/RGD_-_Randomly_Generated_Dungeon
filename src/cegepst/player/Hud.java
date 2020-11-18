package cegepst.player;

import cegepst.GameResources;
import cegepst.engine.Buffer;

import java.awt.*;

public class Hud {

    private final Color hpBarColor = new Color(214, 15, 15);
    private final Color expColor = new Color(99, 228, 90);
    private final Color textColor = Color.black;

    private Image backGround;
    private int damageTextX = 110;
    private int armorTextX = 271;

    public Hud() {
        loadBackGround();
    }

    public void draw(Buffer buffer) {
        setXValueOfText();
        buffer.drawImage(backGround, 15 ,15);
        buffer.drawRectangle(97, 42, (int)(224 * ((double)PlayerStats.HEALTH / PlayerStats.MAX_HEALTH)), 16, hpBarColor);
        int expBarHeight = (int)(81 * ((double)PlayerStats.PLAYER_EXP / PlayerStats.NEXT_LVL_EXP));
        buffer.drawRectangle(18, 23 + (81 - expBarHeight), 5, expBarHeight, expColor);
        buffer.drawText(String.valueOf(PlayerStats.LVL), 47, 95, expColor);
        buffer.drawText(String.valueOf(PlayerStats.BASE_DAMAGE + PlayerStats.BONUS_DAMAGE), damageTextX, 90, textColor);
        buffer.drawText(String.valueOf(PlayerStats.BASE_ARMOR + PlayerStats.BONUS_ARMOR), armorTextX, 90, textColor);
    }

    private void setXValueOfText() {
        if ((PlayerStats.BASE_DAMAGE + PlayerStats.BONUS_DAMAGE) >= 100) {
            damageTextX = 87;
        } else if ((PlayerStats.BASE_DAMAGE + PlayerStats.BONUS_DAMAGE) >= 10) {
            damageTextX = 97;
        }
        if ((PlayerStats.BASE_ARMOR + PlayerStats.BONUS_ARMOR) >= 100) {
            armorTextX = 249;
        } else if ((PlayerStats.BASE_ARMOR + PlayerStats.BONUS_ARMOR) >= 10) {
            armorTextX = 260;
        }
    }

    private void loadBackGround() {
        backGround = GameResources.getInstance().getImage("playerHud");
    }
}
