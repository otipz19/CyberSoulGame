package com.mygdx.game.map;

import com.mygdx.game.map.data.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.badlogic.gdx.math.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class XMLLevelObjectsParser {
    private final Document document;

    private final List<Rectangle> rectangleColliders = new ArrayList<>();
    private final List<Polygon> polygonColliders = new ArrayList<>();

    private final List<ObstacleData> obstaclesData = new ArrayList<>();
    private final List<EnemyData> enemiesData = new ArrayList<>();
    private final List<PortalData> portalsData = new ArrayList<>();
    private final List<PlayerSpawnData> playerSpawnsData = new ArrayList<>();
    private final List<NpcData> npcDataList = new ArrayList<>();

    private final List<Rectangle> deathZones = new ArrayList<>();

    public XMLLevelObjectsParser(String levelFileName) {
        document = loadDocument(levelFileName);
        parseGroups();
    }

    private Document loadDocument(String levelFileName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(Path.of(levelFileName).toFile());
            document.getDocumentElement().normalize();
            return document;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseGroups() {
        NodeList childNodes = document.getChildNodes().item(0).getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) childNode;
                if (element.getTagName().equals("group")) {
                    String groupName = element.getAttribute("name");
                    if (groupName.equals("default")) {
                        parseDefaultObjectGroups(element);
                    } else if (groupName.equals("enemies")) {
                        parseEnemiesObjectGroups(element);
                    }
                }
            }
        }
    }

    private void parseDefaultObjectGroups(Element parentGroup) {
        NodeList objectGroups = parentGroup.getElementsByTagName("objectgroup");
        for (int i = 0; i < objectGroups.getLength(); i++) {
            Element objectGroup = (Element) objectGroups.item(i);
            if (objectGroup.hasAttribute("name")) {
                String groupName = objectGroup.getAttribute("name");
                switch (groupName) {
                    case "colliders" -> parseColliders(objectGroup);
                    case "obstacles" -> parseObstacles(objectGroup);
                    case "portals" -> parsePortals(objectGroup);
                    case "playerSpawns" -> parsePlayerSpawns(objectGroup);
                    case "deathZones" -> parseDeathZones(objectGroup);
                    case "npc" -> parseNpc(objectGroup);
                }
            }
        }
    }

    private void parseColliders(Element objectGroup) {
        forEachObjectInGroup(objectGroup, (object) -> {
            if (!object.hasChildNodes()) {
                rectangleColliders.add(parseRectangle(object));
            } else {
                NodeList polygonElements = object.getElementsByTagName("polygon");
                if (polygonElements.getLength() == 1) {
                    Element polygonElement = (Element) polygonElements.item(0);
                    polygonColliders.add(parsePolygon(object, polygonElement));
                }
            }
        });
    }

    private Rectangle parseRectangle(Element object) {
        Rectangle rectangle = new Rectangle();
        rectangle.x = getFloatAttribute(object, "x");
        rectangle.y = getFloatAttribute(object, "y");
        rectangle.width = getFloatAttribute(object, "width");
        rectangle.height = getFloatAttribute(object, "height");
        return rectangle;
    }

    private Polygon parsePolygon(Element object, Element polygonElement) {
        float x = getFloatAttribute(object, "x");
        float y = getFloatAttribute(object, "y");
        float[] localVertices = parseLocalVertices(polygonElement.getAttribute("points"));
        Polygon polygon = new Polygon(localVertices);
        polygon.setPosition(x, y);
        return polygon;
    }

    private float[] parseLocalVertices(String points) {
        String[] xyPairs = points.split(" ");
        float[] result = new float[xyPairs.length * 2];
        for (int i = 0; i < xyPairs.length; i++) {
            String[] xy = xyPairs[i].split(",");
            result[i * 2] = Float.parseFloat(xy[0]);
            result[i * 2 + 1] = Float.parseFloat(xy[1]);
        }
        return result;
    }

    private void parseObstacles(Element objectGroup) {
        forEachObjectInGroup(objectGroup, (object) -> obstaclesData.add(parseObstacle(object)));
    }

    private ObstacleData parseObstacle(Element object) {
        Rectangle bounds = parseRectangle(object);
        return new ObstacleData(bounds, getProperty(object, "type"));
    }

    private void parsePortals(Element objectGroup) {
        forEachObjectInGroup(objectGroup, object -> portalsData.add(parsePortal(object)));
    }

    private PortalData parsePortal(Element object) {
        Rectangle bounds = parseRectangle(object);
        return new PortalData(bounds,
                getProperty(object, "type"),
                getProperty(object, "destination"),
                getProperty(object, "isEnabled"));
    }

    private void parsePlayerSpawns(Element objectGroup) {
        forEachObjectInGroup(objectGroup, object -> playerSpawnsData.add(parsePlayerSpawn(object)));
    }

    private PlayerSpawnData parsePlayerSpawn(Element object) {
        Rectangle bounds = parseRectangle(object);
        return new PlayerSpawnData(bounds, getProperty(object, "fromLevel"));
    }

    private void parseDeathZones(Element objectGroup) {
        forEachObjectInGroup(objectGroup, object -> {
            deathZones.add(parseRectangle(object));
        });
    }

    private void parseNpc(Element objectGroup) {
        forEachObjectInGroup(objectGroup, object -> {
            Rectangle bounds = parseRectangle(object);
            NpcData npcData = new NpcData(bounds, getProperty(object, "type"));
            npcDataList.add(npcData);
        });
    }

    private void parseEnemiesObjectGroups(Element group) {
        NodeList objectGroups = group.getElementsByTagName("objectgroup");
        for (int i = 0; i < objectGroups.getLength(); i++) {
            Element objectGroup = (Element) objectGroups.item(i);
            enemiesData.add(parseEnemy(objectGroup));
        }
    }

    private EnemyData parseEnemy(Element objectGroup) {
        String type = getProperty(objectGroup, "type");
        Rectangle spawnPoint = null;
        Rectangle travelArea = null;
        NodeList objects = objectGroup.getElementsByTagName("object");
        for (int i = 0; i < objects.getLength(); i++) {
            Element object = (Element) objects.item(i);
            String classProperty = getProperty(object, "class");
            if (classProperty.equals("travelArea")) {
                travelArea = parseRectangle(object);
            } else if (classProperty.equals("spawnPoint")) {
                spawnPoint = parseRectangle(object);
            }
        }
        if (spawnPoint == null || travelArea == null) {
            throw new RuntimeException("Invalid enemy object format!");
        }
        return new EnemyData(spawnPoint, travelArea, type);
    }

    private void forEachObjectInGroup(Element objectGroup, Consumer<Element> action) {
        NodeList objects = objectGroup.getElementsByTagName("object");
        for(int i = 0; i < objects.getLength(); i++) {
            Element object = (Element) objects.item(i);
            action.accept(object);
        }
    }

    private String getProperty(Element element, String propertyName) {
        NodeList properties = ((Element) element.getElementsByTagName("properties").item(0))
                .getElementsByTagName("property");
        for (int i = 0; i < properties.getLength(); i++) {
            Element property = (Element) properties.item(i);
            if (property.hasAttribute("name") && property.getAttribute("name").equals(propertyName)) {
                return property.getAttribute("value");
            }
        }
        throw new RuntimeException("Property " + propertyName + " not found!");
    }

    private float getFloatAttribute(Element element, String attribute) {
        return Float.parseFloat(element.getAttribute(attribute));
    }

    public Stream<Shape2D> getColliders() {
        return Stream.concat(getRectangleColliders(), getPolygonColliders());
    }

    public Stream<Rectangle> getRectangleColliders() {
        return rectangleColliders.stream();
    }

    public Stream<Polygon> getPolygonColliders() {
        return polygonColliders.stream();
    }

    public Stream<ObstacleData> getObstaclesData() {
        return obstaclesData.stream();
    }

    public Stream<EnemyData> getEnemiesData() {
        return enemiesData.stream();
    }

    public Stream<PortalData> getPortalsData() {
        return portalsData.stream();
    }

    public Stream<PlayerSpawnData> getPlayerSpawns() {
        return playerSpawnsData.stream();
    }

    public Stream<Rectangle> getDeathZones() {
        return deathZones.stream();
    }

    public Stream<NpcData> getNpcData() {
        return npcDataList.stream();
    }
}
