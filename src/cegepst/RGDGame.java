package cegepst;

import cegepst.engine.Buffer;
import cegepst.engine.Game;
import cegepst.engine.RenderingEngine;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.ArrayList;

public class RGDGame extends Game {

    private Menu menu;
    private World world;
    private GamePad gamePad;
    private Player player;
    private Chest chest;
    private ArrayList<StaticEntity> gameEntities;

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
        if (gamePad.isInteractPressed() && player.canInteract()) {
            getEntityInRange();
            //player.interact();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        world.draw(buffer);
        player.draw(buffer);
        chest.draw(buffer);
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

    private ArrayList<StaticEntity> getEntityInRange() {
        ArrayList<StaticEntity> entities = new ArrayList<>();
        Rectangle range = new Rectangle(player.getX() , player.getY(), player.getWidth() * 2, player.getHeight() * 2 );
        System.out.println(range);
        return entities;
    }

    private void initAll() {
        gameEntities = new ArrayList<>();
        menu = new Menu();
        world = new World();
        gamePad = new GamePad();
        player = new Player(gamePad);
        chest = new Chest(100, 200);
        gameEntities.add(chest);
    }
}
