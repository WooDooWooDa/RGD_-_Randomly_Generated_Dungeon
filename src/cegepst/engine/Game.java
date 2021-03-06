package cegepst.engine;

import java.awt.event.KeyListener;

public abstract class Game {

    private boolean playing = true;

    private RenderingEngine renderingEngine;
    private GameTime gameTime;

    public Game() {
        renderingEngine = RenderingEngine.getInstance();
    }

    public void start() {
        initialize();
        run();
        conclude();
    }

    public void stop() {
        playing = false;
    }

    public abstract void initialize();
    public abstract void conclude();
    public abstract void update();
    public abstract void draw(Buffer buffer);

    public void addKeyListener(KeyListener listener) {
        renderingEngine.addInputListener(listener);
    }

    private void run() {
        renderingEngine.start();
        gameTime = new GameTime();
        while (playing) {
            update();
            draw(renderingEngine.getRenderingBuffer());
            renderingEngine.renderBufferOnScreen();
            gameTime.synchronize();
        }
        renderingEngine.stop();
    }

}
