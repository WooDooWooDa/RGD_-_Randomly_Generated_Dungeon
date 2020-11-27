package cegepst;

import cegepst.enemies.*;
import cegepst.engine.*;
import cegepst.engine.entities.CollidableRepository;
import cegepst.engine.entities.StaticEntity;
import cegepst.engine.entities.UpdatableEntity;
import cegepst.objects.Arrow;
import cegepst.objects.Projectile;
import cegepst.objects.WitherSkull;
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

    private int currentWorldBiomes = 4;
    private int changingWorld = 0;

    public RGDGame() {
        initAll();
    }

    @Override
    public void update() {
        if (!player.isAlive()) {
            endGame();
            if (Mouse.mouseX != 0) {
                resetGame();
            }
            return;
        }
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
                    if (entity instanceof WitherBoss) {
                        ((WitherBoss) entity).update(player.getX(), player.getY());
                        if (entity.intersectWith(player) && ((WitherBoss) entity).canPhysicalAttack()) {
                            player.receiveDamage(((WitherBoss) entity).dealDamage());
                        }
                        if (((WitherBoss) entity).canAttack()) {
                            newEntities.addAll(((WitherBoss) entity).spawnWitherSkulls());
                        }
                    } else if (entity instanceof Zombie) {
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
                        }
                    } else if (entity instanceof WitherSkull) {
                        WitherSkull skull = (WitherSkull)entity;
                        skull.update();
                        if (skull.intersectWith(player)) {
                            player.receiveDamage(skull.dealDamage());
                            killedEntities.add(skull);
                        }
                    }
                }
            }
        }
        Camera.getInstance().update(player);
        updateKilledEntities();
        worldEnemies.addAll(newEntities);
        newEntities.clear();
        if (worldEnemies.isEmpty() && player.hasAllKeys()) {
            goToNextBiome();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        int camX = Camera.getInstance().getX();
        int camY = Camera.getInstance().getY();
        if (changingWorld > 0) {
            changingWorld--;
            buffer.drawText("Changing World...", camX + 400, camY + 300, Color.white);
        } else {
            world.draw(buffer);
            if (player.isAlive()) {
                player.draw(buffer);
                for (StaticEntity entity: worldEntities) {
                    entity.draw(buffer);
                }
                for (StaticEntity entity: worldEnemies) {
                    entity.draw(buffer);
                }
                player.drawHud(buffer);
                worldTime.draw(buffer);
            } else {
                MessageAnnouncer.setMessage("PLAYER IS DEAD.....", 1000);
                buffer.drawText("CLick anywhere to restart!", camX + 200, camY + 180, Color.BLACK);
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
        if (menu.isQuit()) {
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
            if (entity instanceof Enemy && !((Enemy)entity).isAlive()) {
                worldEntities.addAll(((Enemy)entity).dies());
                killedEntities.add(entity);
            }
            if (entity instanceof Projectile && ( !((Projectile)entity).hasMoved() || ((Projectile) entity).hasReachMaxDistance())) {
                killedEntities.add(entity);
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
        if (currentWorldBiomes > 4) {
            currentWorldBiomes = 1;
        }
        worldTime.resetTime();
        worldEntities.clear();
        worldEnemies.clear();
        worldEnemies.addAll(world.createMobs(currentWorldBiomes));
        worldEntities.addAll(world.createMisc());
        world.changeBiome(currentWorldBiomes);
        player.teleport(50, 50);
    }

    private void resetGame() {
        messageAnnouncer.clearMessage();
        currentWorldBiomes = 1;
        worldEnemies.addAll(world.createMobs(currentWorldBiomes));
        worldEntities.addAll(world.createMisc());
        world.changeBiome(currentWorldBiomes);
        player = new Player(gamePad);
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    private void endGame() {
        RenderingEngine.getInstance().getScreen().showCursor();
        worldTime.resetTime();
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
