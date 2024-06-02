package com.mygdx.game.utils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LevelObjectsParser {
    private final Path filePath;
    private final String layerName;
    private boolean isParsed;
    private Array<Rectangle> rectangles;
    private Array<Polygon> polygons;
    private String objectGroup;

    public LevelObjectsParser(String filePath, String layerName) {
        this.filePath = Path.of(filePath);
        this.layerName = layerName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getLayerName() {
        return layerName;
    }

    public Array<Shape2D> getShapes(){
        if (!isParsed)
            parse();

        Array<Shape2D> shapes = new Array<>(rectangles.size+polygons.size);
        shapes.addAll(rectangles);
        shapes.addAll(polygons);
        return shapes;
    }

    public Array<Rectangle> getRectangles() {
        if (!isParsed)
            parse();

        return rectangles;
    }

    public Array<Polygon> getPolygons() {
        if (!isParsed)
            parse();

        return polygons;
    }

    private void parse(){
        objectGroup = getObjectsGroup();
        parseRectangles();
        parsePolygons();
        isParsed = true;
    }

    private String getObjectsGroup()  {
        String fileContent = getFileContent();
        Pattern objectGroupPattern = Pattern.compile("<objectgroup.*name=\"" + layerName + "\".*?>(.*)</objectgroup>");
        Matcher matcher = objectGroupPattern.matcher(fileContent);
        if (matcher.find()){
            return matcher.group(1);
        }
        return "";
    }

    private String getFileContent() {
        try (Stream<String> fileLines = Files.lines(Path.of("C:\\Users\\Win10\\Documents\\GitHub\\CyberSoulGame\\assets\\zones\\greenzone\\levels\\test-level.tmx"))) {
            StringBuilder fileContent = new StringBuilder();
            fileLines.forEach(fileContent::append);
            return fileContent.toString();
        }
        catch (IOException exception){
            throw new RuntimeException("Can't read a file " + filePath.getFileName());
        }
    }

    private void parseRectangles(){
        rectangles = new Array<>();
        Pattern rectanglePattern = Pattern.compile("<object.*?x=\"(?<x>[0-9.]+)\" y=\"(?<y>[0-9.]+)\" width=\"(?<width>[0-9.]+)\" height=\"(?<height>[0-9.]+)\"/>");
        Matcher matcher = rectanglePattern.matcher(objectGroup);
        while (matcher.find()) {
            rectangles.add(createRectangle(matcher));
        }
    }

    private Rectangle createRectangle(Matcher matcher){
        float x = Float.parseFloat(matcher.group("x"));
        float y = Float.parseFloat(matcher.group("y"));
        float width = Float.parseFloat(matcher.group("width"));
        float height = Float.parseFloat(matcher.group("height"));
        return new Rectangle(x, y, width, height);
    }

    private void parsePolygons(){
        polygons = new Array<>();
        Pattern polygonPattern = Pattern.compile("<object.*?x=\"(?<x>[0-9.]+)\" y=\"(?<y>[0-9.]+)\">.*?points=\"(?<points>[0-9-., ]+)\".*?</object>");
        Matcher matcher = polygonPattern.matcher(objectGroup);
        while (matcher.find()) {
            polygons.add(createPolygon(matcher));
        }
    }

    private Polygon createPolygon(Matcher matcher){
        float x = Float.parseFloat(matcher.group("x"));
        float y = Float.parseFloat(matcher.group("y"));
        float[] localVertices = parseLocalVertices(matcher.group("points"));
        Polygon polygon = new Polygon(localVertices);
        polygon.setPosition(x, y);
        return polygon;
    }

    private float[] parseLocalVertices(String points) {
        String[] xyPairs = points.split(" ");
        float[] result = new float[xyPairs.length*2];
        for (int i = 0; i < xyPairs.length; i++){
            String[] xy = xyPairs[i].split(",");
            result[i*2] = Float.parseFloat(xy[0]);
            result[i*2+1] = Float.parseFloat(xy[1]);
        }
        return result;
    }
}
