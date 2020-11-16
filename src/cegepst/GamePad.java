package cegepst;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_Q;
    private final int menuKey = KeyEvent.VK_ESCAPE;
    private final int interactKey = KeyEvent.VK_F;
    private final int meleeAttack = KeyEvent.VK_Z;
    private final int rangeAttack = KeyEvent.VK_X;

    public GamePad() {
        super.bindKey(quitKey);
        super.bindKey(menuKey);
        super.bindKey(interactKey);
        super.bindKey(meleeAttack);
        super.bindKey(rangeAttack);
        RenderingEngine.getInstance().addInputListener(this);
    }

    public boolean isInteractPressed() {
        return super.isKeyPressed(interactKey);
    }

    public boolean isQuitPressed() {
        return super.isKeyPressed(quitKey);
    }

    public boolean isMenuPressed() {
        return super.isKeyPressed(menuKey);
    }

    public boolean isMeleeAttackPressed() {
        return super.isKeyPressed(meleeAttack);
    }

    public boolean isRangeAttackPressed() {
        return super.isKeyPressed(rangeAttack);
    }

}
