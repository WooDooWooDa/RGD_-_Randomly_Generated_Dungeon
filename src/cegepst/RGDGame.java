package cegepst;

import cegepst.enemies.Enemy;
import cegepst.enemies.Slime;
import cegepst.enemies.Zombie;
import cegepst.enemies.ZombieSpawner;
import cegepst.engine.*;
import cegepst.engine.entities.Blockade;
import cegepst.engine.entities.CollidableRepository;
import cegepst.engine.entities.StaticEntity;
import cegepst.engine.entities.UpdatableEntity;
import cegepst.objects.Arrow;
import cegepst.objects.Chest;
import cegepst.objects.Item;
import cegepst.player.Player;

import java.util.ArrayList;

public class RGDGame extends Game {

    private Menu menu;
    private World world;
    private GamePad gamePad;
    private Player player;
    private ArrayList<StaticEntity> worldEntities;
    private ArrayList<StaticEntity> gameEnemies;
    private ArrayList<StaticEntity> killedEntities;
    private ArrayList<StaticEntity> newEntities;

    public RGDGame() {
        initAll();
    }

    @Override
    public void update() {
        menu.update();
        checkForKeyPressed();
        //updateWorldEntities();
        for (StaticEntity entity : worldEntities) {
            if (entity instanceof UpdatableEntity) {
                ((UpdatableEntity) entity).update();
            }
        }
        if (!menu.isOpen()) {
            player.update();
            for (StaticEntity entity: gameEnemies) {
                if (entity instanceof UpdatableEntity) {
                    if (entity instanceof Zombie) {
                        ((Zombie) entity).update(player.getX(), player.getY());
                        if (((Zombie) entity).hitBoxIntersectWith(player) && ((Zombie) entity).canAttack()) {
                            player.receiveDamage(((Zombie) entity).dealDamage());
                        }
                    } else if (entity instanceof Slime) {
                        ((Slime) entity).update(player.getX(), player.getY());
                        if (entity.intersectWith(player) && ((Slime) entity).canAttack()) {
                            player.receiveDamage(((Slime) entity).dealDamage());
                        }
                    } else if (entity instanceof ZombieSpawner) {
                        ((ZombieSpawner) entity).update();
                        if (((ZombieSpawner) entity).canAttack()) {
                            newEntities.add(((ZombieSpawner) entity).spawn());
                        }
                    } else if (entity instanceof Arrow) {
                        Arrow arrow = (Arrow)entity;
                        arrow.update();
                        for (StaticEntity other : gameEnemies) {
                            if (arrow.hitBoxIntersectWith(other) && other instanceof Enemy) {
                                ((Enemy)other).receivedDamage(arrow.dealDamage());
                                killedEntities.add(arrow);
                            }
                        }
                        for (StaticEntity worldEntity : worldEntities) {
                            if (arrow.hitBoxIntersectWith(worldEntity) && worldEntity instanceof Blockade) {
                                killedEntities.add(arrow);
                            }
                        }
                    }
                }
            }
        }
        updateKilledEntities();
        gameEnemies.addAll(newEntities);
        newEntities.clear();
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
        GameResources.getInstance();
        SoundPlayer.playLoop("musics/forestThemeBackgroundMusic.wav");
        //RenderingEngine.getInstance().getScreen().fullScreen();
    }

    @Override
    public void conclude() {

    }

    private void checkForKeyPressed() {
        if (gamePad.isQuitPressed()) {
            super.stop();
        }
        if (gamePad.isMenuPressed() && menu.CanBeOpen()) {
            menu.toggleMenu();
        }
        if (gamePad.isRangeAttackPressed() && player.canShot()) {
            gameEnemies.add(player.shotArrow());
        }
        if (gamePad.isInteractPressed() && player.canInteract()) {
            worldEntities = player.interact(worldEntities);
        }
    }

    private void updateKilledEntities() {
        for (StaticEntity entity : gameEnemies) {
            if (entity instanceof Enemy) {
                if (!((Enemy)entity).isAlive()) {
                    worldEntities.addAll(((Enemy)entity).dies());
                    killedEntities.add(entity);
                }
            }
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
        newEntities = new ArrayList<>();
        menu = new Menu();
        world = new World();
        gamePad = new GamePad();
        player = new Player(gamePad);
        //camera = new Camera(player, worldEntities);
        worldEntities.add(new Chest(100, 200));
        worldEntities.add(new Chest(200, 100));
        worldEntities.add(Item.Factory.create(200, 200));
        worldEntities.addAll(world.getWorldBorder());
        gameEnemies.add(new Slime(500, 500));
        gameEnemies.add(new Zombie(300, 300, 2));
        gameEnemies.add(new Zombie(400, 300, 3));
        gameEnemies.add(new ZombieSpawner(600, 100));
    }
}
