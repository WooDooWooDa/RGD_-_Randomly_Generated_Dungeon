package cegepst;

import cegepst.enemies.Slime;
import cegepst.enemies.Zombie;
import cegepst.engine.*;
import cegepst.engine.entities.StaticEntity;
import cegepst.engine.entities.UpdatableEntity;
import cegepst.objects.Arrow;
import cegepst.objects.Chest;
import cegepst.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class RGDGame extends Game {

    private Menu menu;
    private World world;
    private GamePad gamePad;
    private Player player;
    private ArrayList<StaticEntity> worldEntities;
    private ArrayList<StaticEntity> gameEnemies;
    private ArrayList<StaticEntity> killedEntities;

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
        if (gamePad.isRangeAttackPressed() && player.canShot()) {
            gameEnemies.add(player.shotArrow());
        }
        for (StaticEntity entity : worldEntities) {
            if (entity instanceof UpdatableEntity) {
                ((UpdatableEntity) entity).update();
            }
        }
        for (StaticEntity entity: gameEnemies) {
            if (entity instanceof UpdatableEntity) {
                if (entity instanceof Zombie) {
                    ((Zombie) entity).update(player.getX(), player.getY());
                    if (((Zombie) entity).hitBoxIntersectWith(player) && ((Zombie) entity).canAttack()) {
                        player.receiveDamage(((Zombie) entity).dealDamage());
                    }
                } else if (entity instanceof Slime) {
                    ((Slime) entity).update(player.getX(), player.getY());
                } else if (entity instanceof Arrow) {
                    Arrow arrow = (Arrow)entity;
                    arrow.update();
                    for (StaticEntity other : gameEnemies) {
                        if (arrow.hitBoxIntersectWith(other)) {
                            if (other instanceof Zombie) {
                                ((Zombie) other).receivedDamage(arrow.dealDamage());
                            }
                            killedEntities.add(arrow);
                        }
                    }
                }
            }
        }
        updateKilledEntities();
        if (!menu.isOpen()) {
            player.update();
        }
        if (gamePad.isInteractPressed() && player.canInteract()) {
            worldEntities = player.interact(worldEntities);
        }
    }

    @Override
    public void draw(Buffer buffer) {
        world.draw(buffer);
        for (StaticEntity entity: worldEntities) {
            entity.draw(buffer);
        }
        for (StaticEntity entity: gameEnemies) {
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
        SoundPlayer.playLoop("musics/forestThemeBackgroundMusic.wav");
        //RenderingEngine.getInstance().getScreen().fullScreen();
    }

    @Override
    public void conclude() {

    }

    private void updateKilledEntities() {
        for (StaticEntity entity : gameEnemies) {
            //if (!entity.isAlive()) {
            // TODO: 2020-11-16 add isAlive method to all ennemies 
            //}
        }
        if (!killedEntities.isEmpty()) {
            for (StaticEntity killedElement : killedEntities) {
                gameEnemies.remove(killedElement);
                CollidableRepository.getInstance().unregisterEntity(killedElement);
            }
        }
        killedEntities.clear();
    }

    private void initAll() {
        gameEnemies = new ArrayList<>();
        worldEntities = new ArrayList<>();
        killedEntities = new ArrayList<>();
        menu = new Menu();
        world = new World();
        gamePad = new GamePad();
        player = new Player(gamePad);
        worldEntities.add(new Chest(100, 200));
        worldEntities.add(new Chest(200, 100));
        gameEnemies.add(new Slime(500, 500, new Random().nextInt(3) + 1));
        gameEnemies.add(new Zombie(300, 300, 2));
        gameEnemies.add(new Zombie(400, 300, 3));
    }
}
