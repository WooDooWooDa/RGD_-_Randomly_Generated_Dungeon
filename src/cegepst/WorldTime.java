package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.RenderingEngine;

import java.awt.*;

public class WorldTime {

    private final int MAX_TIME = 10000;
    private int time;
    private int darkness;
    private Color light;
    private boolean isNight = false;

    public WorldTime() {
        time = 0;
        darkness = 0;
        light = new Color(0, 0, 0, darkness);
    }

    public void update() {
        if (GameSettings.GAME_TIME) {
            if (isNight) {
                time--;
                if (time <= 0) {
                    isNight = false;
                }
            } else {
                time++;
                if (time >= MAX_TIME) {
                    isNight = true;
                }
            }
            darkness = (int)(100 * ((double)time / MAX_TIME)) * 2;
            light = new Color(0, 0, 0, darkness);
        }

    }

    public void draw(Buffer buffer) {
        if (GameSettings.GAME_TIME) {
            buffer.drawRectangle(0, 0, RenderingEngine.SCREEN_WIDTH, RenderingEngine.SCREEN_HEIGHT, light);
        }
    }
}
