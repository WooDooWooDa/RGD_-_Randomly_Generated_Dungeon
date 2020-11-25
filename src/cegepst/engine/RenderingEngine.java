package cegepst.engine;

import cegepst.Camera;
import cegepst.Menu;
import cegepst.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RenderingEngine {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    private static RenderingEngine instance;

    private Screen screen;
    private JPanel panel;
    private BufferedImage bufferedImage;


    public static RenderingEngine getInstance() {
        if (instance == null) {
            instance = new RenderingEngine();
        }
        return instance;
    }

    public Screen getScreen() {
        return screen;
    }

    public void start() {
        screen.start();
    }

    public void stop() {
        screen.end();
    }

    public Buffer getRenderingBuffer() {
        bufferedImage = new BufferedImage(World.WORLD_WIDTH, World.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHints(getRenderingHints());
        return new Buffer(graphics);
    }

    public void renderBufferOnScreen() {
        Graphics2D graphics2D = (Graphics2D) panel.getGraphics();
        graphics2D.translate(-Camera.getInstance().getX(), -Camera.getInstance().getY());
        graphics2D.drawImage(bufferedImage, 0,0, panel);
        graphics2D.translate(Camera.getInstance().getX(), Camera.getInstance().getY());
        Toolkit.getDefaultToolkit().sync();
        graphics2D.dispose();
    }

    public void addInputListener(KeyListener listener) {
        panel.addKeyListener(listener);
    }

    private RenderingEngine() {
        initializeFrame();
        initializePanel();
    }

    private void initializeFrame() {
        screen = new Screen();
        screen.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        screen.setTitle("Viking Game");
    }

    private void initializePanel() {
        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Menu.mouseX = e.getX();
                    Menu.mouseY = e.getY();
                }
            }
        });
        screen.setPanel(panel);
    }

    private RenderingHints getRenderingHints() {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return rh;
    }
}
