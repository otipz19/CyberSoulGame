package com.mygdx.game.map;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.DeathZone;
import com.mygdx.game.entities.Surface;
import com.mygdx.game.entities.enemies.BatEnemy;
import com.mygdx.game.entities.enemies.CarEnemy;
import com.mygdx.game.entities.enemies.BossEnemy;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.enemies.MonsterEnemy;
import com.mygdx.game.entities.npc.Monk;
import com.mygdx.game.entities.npc.Npc;
import com.mygdx.game.entities.obstacles.EntryObstacle;
import com.mygdx.game.entities.obstacles.GateObstacle;
import com.mygdx.game.entities.obstacles.HammerObstacle;
import com.mygdx.game.entities.portals.*;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PlayerSpawnData;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.utils.DelayedAction;
import com.mygdx.game.utils.RenderUtils;

import java.util.stream.Stream;

/**
 * Binds objects parsed from a Tiled map file to corresponding game entities and colliders.
 */
public class MapObjectsBinder {

    private final Level level;
    private final XMLLevelObjectsParser objectsParser;

    private final Array<GateObstacle> obstacles = new Array<>();
    private final Array<Enemy> enemies = new Array<>();
    private final Array<Portal> portals = new Array<>();
    private final Array<Npc> npcList = new Array<>();

    /**
     * Constructs a MapObjectsBinder object.
     *
     * @param tileMapName The name of the Tiled map file (.tmx) to parse.
     * @param level       The Level object associated with the parsed objects.
     */
    public MapObjectsBinder(String tileMapName, Level level) {
        objectsParser = new XMLLevelObjectsParser(tileMapName);
        this.level = level;
    }

    /**
     * Creates colliders for shapes parsed from the Tiled map file.
     * Each collider is associated with a Surface entity in the level.
     */
    public void createColliders() {
        objectsParser.getColliders().forEach(shape -> {
            Collider collider;
            if (shape instanceof Rectangle)
                collider = ColliderCreator.create((Rectangle) shape, level.getCoordinatesProjector());
            else if (shape instanceof Polygon)
                collider = ColliderCreator.create((Polygon) shape, level.getCoordinatesProjector());
            else
                throw new RuntimeException("Shape is not supported");

            new Surface(level, collider);
        });
    }

    /**
     * Retrieves player spawn points parsed from the Tiled map file.
     *
     * @return A stream of PlayerSpawnData objects representing player spawn points.
     */
    public Stream<PlayerSpawnData> getPlayerSpawns() {
        return objectsParser.getPlayerSpawns();
    }

    /**
     * Creates death zones based on the bounds parsed from the Tiled map file.
     * Each death zone is associated with the level.
     */
    public void createEntities() {
        createDeathZones();
        createObstacles();
        createEnemies();
        createPortals();
        createNpc();
    }

    private void createDeathZones() {
        objectsParser.getDeathZones().forEach(bounds -> {
            new DeathZone(level, bounds);
        });
    }

    private void createObstacles() {
        objectsParser.getObstaclesData().forEach(obstacleData -> {
            var collider = ColliderCreator.create(obstacleData.getBounds(), level.getCoordinatesProjector());
            GateObstacle obstacle = switch (obstacleData.getType()) {
                case ENTRY -> new EntryObstacle(level, collider, obstacleData);
                case HAMMER -> new HammerObstacle(level, collider, obstacleData);
                default -> throw new RuntimeException("Not supported obstacle type!");
            };
            obstacles.add(obstacle);
        });
    }

    private void createEnemies() {
        objectsParser.getEnemiesData().forEach(enemyData -> {
            Enemy enemy;
            switch (enemyData.getType()) {
                case MONSTER -> {
                    enemy = new MonsterEnemy(level, enemyData, 1.5f, 2f);
                }
                case BAT -> {
                    enemy = new BatEnemy(level, enemyData, 1.25f, 1.75f);
                }
                case CAR -> {
                    enemy = new CarEnemy(level, enemyData, 3f, 2.5f);
                }
                case BOSS -> {
                    enemy = new BossEnemy(level, enemyData, 1.75f, 2.5f);
                }
                default -> throw new RuntimeException("Not supported enemy type!");
            }
            enemies.add(enemy);
            enemy.addOnDeathAction(() -> level.addDelayedAction(enemy.getDeathDelay(), () -> enemies.removeValue(enemy, true)));
        });
    }

    private void createPortals() {
        objectsParser.getPortalsData().forEach(portalData -> {
            Portal portal;
            switch (portalData.getType()) {
                case FIRST -> portal = new FirstPortal(level, portalData);
                case SECOND -> portal = new SecondPortal(level, portalData);
                case THIRD -> portal = new ThirdPortal(level, portalData);
                case LAST -> portal = new LastPortal(level, portalData);
                default -> throw new RuntimeException("Not supported portal type! " + portalData.getType());
            }
            portals.add(portal);
        });
    }

    private void createNpc() {
        objectsParser.getNpcData().forEach(npcData -> {
            Npc npc;
            switch (npcData.getType()) {
                case MONK -> npc = new Monk(level, npcData);
                default -> throw new RuntimeException("Unsupported NPC type! " + npcData.getType());
            }
            npcList.add(npc);
        });
    }

    /**
     * Renders all obstacles associated with the level.
     *
     * @param delta The time in seconds since the last frame.
     */
    public void renderObstacles(float delta) {
        RenderUtils.renderEntities(delta, obstacles);
    }

    /**
     * Renders all enemies associated with the level.
     *
     * @param delta The time in seconds since the last frame.
     */
    public void renderEnemies(float delta) {
        RenderUtils.renderEntities(delta, enemies);
    }

    /**
     * Renders all portals associated with the level.
     *
     * @param delta The time in seconds since the last frame.
     */
    public void renderPortals(float delta) {
        RenderUtils.renderEntities(delta, portals);
    }

    /**
     * Renders all NPCs associated with the level.
     *
     * @param delta The time in seconds since the last frame.
     */
    public void renderAllNpc(float delta) {
        RenderUtils.renderEntities(delta, npcList);
    }

    /**
     * Retrieves the array of portals associated with the level.
     *
     * @return An array of Portal objects.
     */
    public Array<Portal> getPortals() {
        return portals;
    }

    /**
     * Retrieves the array of obstacles associated with the level.
     *
     * @return An array of GateObstacle objects.
     */
    public Array<GateObstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Retrieves the array of enemies associated with the level.
     *
     * @return An array of Enemy objects.
     */
    public Array<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Retrieves the array of NPCs associated with the level.
     *
     * @return An array of Npc objects.
     */
    public Array<Npc> getNpcList() {
        return npcList;
    }
}
