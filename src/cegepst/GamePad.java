package cegepst;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_Q;
    private final int menuKey = KeyEvent.VK_ESCAPE;

    public GamePad() {
        super.bindKey(quitKey);
        super.bindKey(menuKey);
        RenderingEngine.getInstance().addInputListener(this);
    }

    public boolean isQuitPressed() {
        return super.isKeyPressed(quitKey);
    }

    public boolean isMenuPressed() {
        return super.isKeyPressed(menuKey);
    }

}
