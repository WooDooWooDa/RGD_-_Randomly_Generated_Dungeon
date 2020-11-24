package cegepst.player;

import cegepst.Camera;
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
    private int gemTextX = 195;
    private Player playerRef;

    public Hud(Player player) {
        playerRef = player;
        loadBackGround();
    }

    public void draw(Buffer buffer) {
        int camX = Camera.getInstance().getX();
        int camY = Camera.getInstance().getY();
        setXValueOfText();
        buffer.drawImage(backGround, camX + 15 ,camY + 15);
        buffer.drawRectangle(camX + 97, camY + 42, (int)(224 * ((double)PlayerStats.HEALTH / PlayerStats.MAX_HEALTH)), 16, hpBarColor);
        buffer.setGameFontSmall();
        buffer.drawText((PlayerStats.HEALTH + " I " + PlayerStats.MAX_HEALTH), camX + 100, camY + 54, Color.WHITE);
        buffer.setGameFontBig();
        int expBarHeight = (int)(81 * ((double)PlayerStats.PLAYER_EXP / PlayerStats.NEXT_LVL_EXP));
        buffer.drawRectangle(camX + 18, camY + 23 + (81 - expBarHeight), 5, expBarHeight, expColor);
        buffer.drawText(String.valueOf(PlayerStats.LVL), camX + 47, camY + 95, expColor);
        buffer.drawText(String.valueOf(PlayerStats.BASE_DAMAGE + PlayerStats.BONUS_DAMAGE), camX + damageTextX, camY + 90, textColor);
        buffer.drawText(String.valueOf(PlayerStats.GEM), camX + gemTextX, camY + 90, textColor);
        buffer.drawText(String.valueOf(PlayerStats.BASE_ARMOR + PlayerStats.BONUS_ARMOR), camX + armorTextX, camY + 90, textColor);
        if (playerRef.getItems().size() > 0) {
            for (int i = 0; i < playerRef.getItems().size(); i++) {
                buffer.drawImage(playerRef.getItems().get(i).getImage(), camX + 97 + i * 32, camY + 95);
            }
        }
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
        if (PlayerStats.GEM >= 10) {
            gemTextX = 185;
        }
    }

    private void loadBackGround() {
        backGround = GameResources.getInstance().getImage("playerHud");
    }
}
