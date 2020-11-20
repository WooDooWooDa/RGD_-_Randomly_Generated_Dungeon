package cegepst.engine;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Buffer {

    private Graphics2D graphics;
    private Font gameFont;
    private Font gameFontSmall;

    public Buffer(Graphics2D graphics) {
        this.graphics = graphics;
        createFonts();
        graphics.setFont(gameFont);
    }

    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }

    public void drawRectangle(int x, int y, int width, int height, Paint color) {
        graphics.setPaint(color);
        graphics.fillRect(x, y, width, height);
    }

    public void drawRectangle(int x, int y, Rectangle rectangle, Paint color) {
        graphics.setPaint(color);
        graphics.fillRect(x, y, rectangle.width, rectangle.height);
    }

    public void drawCircle(int x, int y, int radius, Paint color) {
        graphics.setPaint(color);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }

    public void drawText(String text, int x, int y, Paint color) {
        graphics.setPaint(color);
        graphics.drawString(text, x, y);
    }

    public void setGameFontBig() {
        graphics.setFont(gameFont);
    }

    public void setGameFontSmall() {
        graphics.setFont(gameFontSmall);
    }

    private void createFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("minecraftFontAlt.ttf")).deriveFont(20f);
            gameFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File("minecraftFontAlt.ttf")).deriveFont(10f);
            ge.registerFont(gameFont);
            ge.registerFont(gameFontSmall);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
