package cegepst;

import cegepst.engine.RenderingEngine;
import cegepst.player.Player;

public class Camera {

    private static Camera instance;

    private int x;
    private int y;

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera(400,400);
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
        if (x <= 0) {
            x = 0;
        }
        if (y <= 0 ) {
            y = 0;
        }
        if (x + RenderingEngine.SCREEN_HEIGHT >= World.WORLD_HEIGHT) {
            x = x + RenderingEngine.SCREEN_WIDTH;
        }
    }
}
