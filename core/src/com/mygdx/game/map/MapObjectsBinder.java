package com.mygdx.game.map;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.DeathZone;
import com.mygdx.game.entities.Surface;
import com.mygdx.game.entities.enemies.BatEnemy;
import com.mygdx.game.entities.enemies.CarEnemy;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.enemies.MonsterEnemy;
import com.mygdx.game.entities.obstacles.EntryObstacle;
import com.mygdx.game.entities.obstacles.GateObstacle;
import com.mygdx.game.entities.obstacles.HammerObstacle;
import com.mygdx.game.entities.portals.FirstPortal;
import com.mygdx.game.entities.portals.Portal;
import com.mygdx.game.entities.portals.SecondPortal;
import com.mygdx.game.entities.portals.ThirdPortal;
import com.mygdx.game.levels.Level;
import com.mygdx.game.map.data.PlayerSpawnData;
import com.mygdx.game.physics.Collider;
import com.mygdx.game.physics.ColliderCreator;
import com.mygdx.game.utils.DelayedAction;
import com.mygdx.game.utils.RenderUtils;

import java.util.stream.Stream;

public class MapObjectsBinder {
    private final Level level;
    private final XMLLevelObjectsParser objectsParser;

    private final Array<GateObstacle> obstacles = new Array<>();
    private final Array<Enemy> enemies = new Array<>();
    private final Array<Portal> portals = new Array<>();

    public MapObjectsBinder(String tileMapName, Level level) {
        objectsParser = new XMLLevelObjectsParser(tileMapName);
        this.level = level;
    }

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

    public Stream<PlayerSpawnData> getPlayerSpawns() {
        return objectsParser.getPlayerSpawns();
    }

    public void createEntities() {
        createDeathZones();
        createObstacles();
        createEnemies();
        createPortals();
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
                case DEFAULT -> {
                    enemy = new MonsterEnemy(level, enemyData, 1.5f, 2f);
                }
                case MONSTER -> {
                    enemy = new MonsterEnemy(level, enemyData, 1.5f, 2f);
                }
                case BAT -> {
                    enemy = new BatEnemy(level, enemyData, 1.5f, 2f);
                }
                case CAR -> {
                    enemy = new CarEnemy(level, enemyData, 1.5f, 2f);
                }
                default -> throw new RuntimeException("Not supported enemy type!");
            }
            enemies.add(enemy);
            enemy.addOnDeathAction(() -> new DelayedAction(enemy.getDeathDelay(), () -> enemies.removeValue(enemy, true)));
        });
    }

    private void createPortals() {
        objectsParser.getPortalsData().forEach(portalData -> {
            Portal portal;
            switch (portalData.getType()) {
                case FIRST -> portal = new FirstPortal(level, portalData);
                case SECOND -> portal = new SecondPortal(level, portalData);
                case THIRD -> portal = new ThirdPortal(level, portalData);
                default -> throw new RuntimeException("Not supported portal type!");
            }
            portals.add(portal);
        });
    }

    public void renderObstacles(float delta) {
        RenderUtils.renderEntities(delta, obstacles);
    }

    public void renderEnemies(float delta) {
        RenderUtils.renderEntities(delta, enemies);
    }

    public void renderPortals(float delta) {
        RenderUtils.renderEntities(delta, portals);
    }

    public Array<Portal> getPortals() {
        return portals;
    }

    public Array<GateObstacle> getObstacles() {
        return obstacles;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}