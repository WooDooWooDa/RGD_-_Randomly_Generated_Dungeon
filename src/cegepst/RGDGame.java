package cegepst;

import cegepst.enemies.Slime;
import cegepst.engine.Buffer;
import cegepst.engine.Game;
import cegepst.engine.RenderingEngine;
import cegepst.engine.entities.StaticEntity;
import cegepst.engine.entities.UpdatableEntity;
import cegepst.player.Player;

import java.util.ArrayList;

public class RGDGame extends Game {

    private Menu menu;
    private World world;
    private GamePad gamePad;
    private Player player;
    private ArrayList<Chest> chests;
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
        for (StaticEntity entity: gameEntities) {
            if (entity instanceof UpdatableEntity) {
                ((UpdatableEntity) entity).update();
            }
        }
        if (!menu.isOpen()) {
            player.update();
        }
        if (gamePad.isInteractPressed() && player.canInteract()) {
            gameEntities = player.interact(gameEntities);
        }
    }

    @Override
    public void draw(Buffer buffer) {
        world.draw(buffer);
        for (StaticEntity entity: gameEntities) {
            entity.draw(buffer);
        }
        player.draw(buffer);
        if (menu.isOpen()) {
            menu.draw(buffer);
        }
    }

    @Override
    public void initialize() {
        RenderingEngine.getInstance().getScreen().hideCursor();
        //RenderingEngine.getInstance().getScreen().fullScreen();
    }

    @Override
    public void conclude() {

    }

    private void initAll() {
        gameEntities = new ArrayList<>();
        chests = new ArrayList<>();
        menu = new Menu();
        world = new World();
        gamePad = new GamePad();
        player = new Player(gamePad);
        chests.add(new Chest(100, 200));
        chests.add(new Chest(200, 100));
        gameEntities.addAll(chests);
        gameEntities.add(new Slime());
    }
}
