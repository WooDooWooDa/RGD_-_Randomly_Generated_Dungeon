package cegepst;

import cegepst.engine.RenderingEngine;
import cegepst.player.Player;

public class Camera {

    private static Camera instance;

    private int x;
    private int y;

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera(0,0);
        }
        return instance;
    }

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update(Player player) {
        x = player.getX() - RenderingEngine.SCREEN_WIDTH / 2;
        y = player.getY() - RenderingEngine.SCREEN_HEIGHT / 2;
    }
}
