package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.Game;
import cegepst.engine.RenderingEngine;

public class RGDGame extends Game {

    private Menu menu;
    private World world;
    private GamePad gamePad;
    private Player player;

    public RGDGame() {
        initAll();
    }

    @Override
    public void update() {
        menu.update();
        if (gamePad.isQuitPressed()) {
            super.stop();
        }
        if (gamePad.isMenuPressed() && menu.CanBeOpen()) {
            menu.toggleMenu();
        }
        if (!menu.isOpen()) {
            player.update();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        world.draw(buffer);
        player.draw(buffer);
        if (menu.isOpen()) {
            menu.draw(buffer);
        }
    }

    @Override
    public void initialize() {
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    @Override
    public void conclude() {

    }

    private void initAll() {
        menu = new Menu();
        world = new World();
        gamePad = new GamePad();
        player = new Player(gamePad);

    }
}
