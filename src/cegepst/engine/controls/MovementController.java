package cegepst.engine.controls;

import java.awt.event.KeyEvent;

public class MovementController extends Controller {

    private int upKey = KeyEvent.VK_UP;
    private int downKey = KeyEvent.VK_DOWN;
    private int leftKey = KeyEvent.VK_LEFT;
    private int rightKey = KeyEvent.VK_RIGHT;

    public MovementController() {
        int[] keys = {upKey, downKey, leftKey, rightKey};
        bindKeys(keys);
    }

    public boolean isLeftPressed() {
        return super.isKeyPressed(leftKey);
    }
    public boolean isRightPressed() {
        return super.isKeyPressed(rightKey);
    }
    public boolean isUpPressed() {
        return super.isKeyPressed(upKey);
    }
    public boolean isDownPressed() {
        return super.isKeyPressed(downKey);
    }

    public boolean isMoving() {
        return isLeftPressed() || isRightPressed() || isDownPressed() || isUpPressed();
    }

    public void setUpKey(int upKey) {
        updateKey(upKey);
        this.upKey = upKey;
    }

    public void setDownKey(int downKey) {
        updateKey(downKey);
        this.downKey = downKey;
    }

    public void setLeftKey(int leftKey) {
        updateKey(leftKey);
        this.leftKey = leftKey;
    }

    public void setRightKey(int rightKey) {
        updateKey(rightKey);
        this.rightKey = rightKey;
    }

    private void updateKey(int keycode) {
        super.removeKey(keycode);
        super.bindKey(keycode);
    }
}
