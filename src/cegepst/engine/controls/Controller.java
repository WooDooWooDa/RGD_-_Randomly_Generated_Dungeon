package cegepst.engine.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public abstract class Controller implements KeyListener {

    private final HashMap<Integer, Boolean> pressedKey;

    public Controller() {
        pressedKey = new HashMap<>();
    }

    protected void bindKeys(int[] keys) {
        for (int keycode : keys) {
            pressedKey.put(keycode, false);
        }
    }

    protected void bindKey(int keycode) {
        pressedKey.put(keycode, false);
    }

    protected void clearKeys() {
        pressedKey.clear();
    }

    protected void removeKey(int keycode) {
        pressedKey.remove(keycode);
    }

    protected boolean isKeyPressed(int keycode) {
        return pressedKey.containsKey(keycode) && pressedKey.get(keycode);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (pressedKey.containsKey(keycode)) {
            pressedKey.put(keycode, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (pressedKey.containsKey(keycode)) {
            pressedKey.put(keycode, false);
        }
    }
}