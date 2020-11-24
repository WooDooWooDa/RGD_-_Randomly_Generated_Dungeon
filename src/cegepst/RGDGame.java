package cegepst;

import cegepst.enemies.Enemy;
import cegepst.enemies.Slime;
import cegepst.enemies.Zombie;
import cegepst.enemies.ZombieSpawner;
import cegepst.engine.*;
import cegepst.engine.entities.CollidableRepository;
import cegepst.engine.entities.StaticEntity;
import cegepst.engine.entities.UpdatableEntity;
import cegepst.objects.Arrow;
import cegepst.player.Player;

import java.awt.*;
import java.util.ArrayList;

public class RGDGame extends Game {

    private Menu menu;
    private World world;
    private GamePad gamePad;
    private Player player;
    private WorldTime worldTime;
    private MessageAnnouncer messageAnnouncer;
    private ArrayList<StaticEntity> worldEntities;
    private ArrayList<StaticEntity> worldEnemies;
    private ArrayList<StaticEntity> killedEntities;
    private ArrayList<StaticEntity> newEntities;

    private int currentWorldBiomes = 1;
    private int changingWorld = 0;

    public RGDGame() {
        initAll();
    }

    @Override
    public void update() {
        menu.update();
        worldTime.update();
        messageAnnouncer.update();
        checkForKeyPressed();
        if (!menu.isOpen() && player.isAlive()) {
            player.update();
            for (StaticEntity entity : worldEntities) {
                if (entity instanceof UpdatableEntity) {
                    ((UpdatableEntity) entity).update();
                }
            }
            for (StaticEntity entity: worldEnemies) {
                if (entity instanceof UpdatableEntity) {
                    if (entity instanceof Zombie) {
                        ((Zombie) entity).update(player.getX(), player.getY());
                        if (entity.intersectWith(player) && ((Zombie) entity).canAttack()) {
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
                        for (StaticEntity other : worldEnemies) {
                            if (arrow.hitBoxIntersectWith(other) && other instanceof Enemy) {
                                ((Enemy)other).receivedDamage(arrow.dealDamage());
                                killedEntities.add(arrow);
                            }
                            if (!arrow.hasMoved()) {
                                killedEntities.add(arrow);
                            }
                        }
                    }
                }
            }
        }
        Camera.getInstance().update(player);
        updateKilledEntities();
        worldEnemies.addAll(newEntities);
        newEntities.clear();
        if (!player.isAlive()) {
            endGame();
        }
        if (worldEnemies.isEmpty() && player.hasAllKeys()) {
            goToNextBiome();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        if (changingWorld > 0) {
            changingWorld--;
            buffer.drawText("Changing World...", 500, 300, Color.white);
        } else {
            world.draw(buffer);
            if (player.isAlive()) {
                for (StaticEntity entity: worldEnemies) {
                    entity.draw(buffer);
                }
                for (StaticEntity entity: worldEntities) {
                    entity.draw(buffer);
                }
                player.draw(buffer);
                worldTime.draw(buffer);
            } else {
                buffer.drawText("PLAYER IS DEAD!!!!!", 300, 300, Color.red);
            }
        }
        messageAnnouncer.showMessage(buffer);
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
            worldEnemies.add(player.shotArrow());
        }
        if (gamePad.isMeleeAttackPressed() && player.canAttack()) {
            player.swordAttack(worldEnemies);
        }
        if (gamePad.isInteractPressed() && player.canInteract()) {
            worldEntities = player.interact(worldEntities);
        }
    }

    private void updateKilledEntities() {
        for (StaticEntity entity : worldEnemies) {
            if (entity instanceof Enemy) {
                if (!((Enemy)entity).isAlive()) {
                    worldEntities.addAll(((Enemy)entity).dies());
                    killedEntities.add(entity);
                }
            }
        }
        if (!killedEntities.isEmpty()) {
            for (StaticEntity killedElement : killedEntities) {
                worldEnemies.remove(killedElement);
                CollidableRepository.getInstance().unregisterEntity(killedElement);
            }
        }
        killedEntities.clear();
    }

    private void goToNextBiome() {
        changingWorld = 500;
        currentWorldBiomes++;
        if (currentWorldBiomes > 5) {
            currentWorldBiomes = 1;
        }
        worldEntities.clear();
        worldEnemies.clear();
        world = new World();
        worldEnemies.addAll(world.createMobs(currentWorldBiomes));
        worldEntities.addAll(world.createMisc());
        world.changeBiome(currentWorldBiomes);
    }

    private void endGame() {
        worldEntities.clear();
        worldEnemies.clear();
    }

    private void initAll() {
        worldEnemies = new ArrayList<>();
        worldEntities = new ArrayList<>();
        killedEntities = new ArrayList<>();
        newEntities = new ArrayList<>();
        menu = new Menu();
        world = new World();
        worldEnemies.addAll(world.createMobs(currentWorldBiomes));
        worldEntities.addAll(world.createMisc());
        world.changeBiome(currentWorldBiomes);
        worldTime = new WorldTime();
        messageAnnouncer = new MessageAnnouncer();
        gamePad = new GamePad();
        player = new Player(gamePad);
    }
}
