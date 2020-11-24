package cegepst;

import cegepst.engine.Buffer;

import java.awt.*;

public class MessageAnnouncer {

    private static String message;

    private static int messageTimer;

    public MessageAnnouncer() {
        messageTimer = 0;
    }

    public static void showMessage(String msg, int time) {
        messageTimer = time;
        message = msg;
    }

    public void update() {
        if (messageTimer > 0) {
            messageTimer--;
        }
    }

    public void showMessage(Buffer buffer) {
        if (messageTimer > 0) {
            buffer.drawText(message, Camera.getInstance().getX() + 250, Camera.getInstance().getY() + 150, Color.BLACK);
        }
    }

}
