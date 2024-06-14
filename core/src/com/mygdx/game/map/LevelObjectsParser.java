package com.mygdx.game.map;

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
/**
 * Parses object data from a Tiled map file (.tmx) to retrieve shapes such as rectangles and polygons.
 */
public class LevelObjectsParser {
    private final Path filePath;
    private final String layerName;
    private boolean isParsed;
    private Array<Rectangle> rectangles;
    private Array<Polygon> polygons;
    private String objectGroup;

    //Should be changed for special type to actually save properties
    private Array<Rectangle> rectanglesWithProperties;
    /**
     * Constructs a LevelObjectsParser object with the specified file path and layer name.
     *
     * @param filePath  The path to the Tiled map file (.tmx).
     * @param layerName The name of the layer containing objects to parse.
     */
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

    public Array<Rectangle> getRectanglesWithProperties(){
        if(!isParsed)
            parse();

        return rectanglesWithProperties;
    }
    /**
     * Parses the Tiled map file to retrieve object data based on the layer name.
     * This method initializes internal arrays for rectangles, polygons, and rectangles with properties.
     * It sets the {@code isParsed} flag to true once parsing is completed.
     */
    private void parse(){
        objectGroup = getObjectsGroup();
        parseRectangles();
        parsePolygons();
        parseRectanglesWithProperties();
        isParsed = true;
    }
    /**
     * Extracts the object group content from the Tiled map file based on the layer name.
     *
     * @return The content of the object group as a string.
     */
    private String getObjectsGroup()  {
        String fileContent = getFileContent();
        Pattern objectGroupPattern = Pattern.compile("<objectgroup.*name=\"" + layerName + "\".*?>(.*)</objectgroup>");
        Matcher matcher = objectGroupPattern.matcher(fileContent);
        if (matcher.find()){
            return matcher.group(1);
        }
        return "";
    }
    /**
     * Reads the content of the Tiled map file specified by {@code filePath}.
     *
     * @return The content of the Tiled map file as a string.
     * @throws RuntimeException If an IOException occurs while reading the file.
     */
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
    /**
     * Parses rectangles from the object group content extracted from the Tiled map file.
     * Populates the {@code rectangles} array with parsed Rectangle objects.
     */
    private void parseRectangles(){
        rectangles = new Array<>();
        Pattern rectanglePattern = Pattern.compile("<object.*?x=\"(?<x>[0-9.]+)\" y=\"(?<y>[0-9.]+)\" width=\"(?<width>[0-9.]+)\" height=\"(?<height>[0-9.]+)\"/>");
        Matcher matcher = rectanglePattern.matcher(objectGroup);
        while (matcher.find()) {
            rectangles.add(createRectangle(matcher));
        }
    }
    /**
     * Parses rectangles with properties from the object group content extracted from the Tiled map file.
     * Populates the {@code rectanglesWithProperties} array with parsed Rectangle objects.
     */
    private void parseRectanglesWithProperties() {
        rectanglesWithProperties = new Array<>();
        Pattern rectanglePattern = Pattern.compile("<object.*?x=\"(?<x>[0-9.]+)\" y=\"(?<y>[0-9.]+)\" width=\"(?<width>[0-9.]+)\" height=\"(?<height>[0-9.]+)\">");
        Matcher matcher = rectanglePattern.matcher(objectGroup);
        while (matcher.find()) {
            rectanglesWithProperties.add(createRectangle(matcher));
        }
    }
    /**
     * Creates a Rectangle object based on the matched data from the Tiled map file.
     *
     * @param matcher The Matcher object containing the matched data.
     * @return A Rectangle object initialized with the parsed x, y, width, and height values.
     */
    private Rectangle createRectangle(Matcher matcher){
        float x = Float.parseFloat(matcher.group("x"));
        float y = Float.parseFloat(matcher.group("y"));
        float width = Float.parseFloat(matcher.group("width"));
        float height = Float.parseFloat(matcher.group("height"));
        return new Rectangle(x, y, width, height);
    }
    /**
     * Parses polygons from the object group content extracted from the Tiled map file.
     * Populates the {@code polygons} array with parsed Polygon objects.
     */
    private void parsePolygons(){
        polygons = new Array<>();
        Pattern polygonPattern = Pattern.compile("<object.*?x=\"(?<x>[0-9.]+)\" y=\"(?<y>[0-9.]+)\">.*?points=\"(?<points>[0-9-., ]+)\".*?</object>");
        Matcher matcher = polygonPattern.matcher(objectGroup);
        while (matcher.find()) {
            polygons.add(createPolygon(matcher));
        }
    }
    /**
     * Creates a Polygon object based on the matched data from the Tiled map file.
     *
     * @param matcher The Matcher object containing the matched data.
     * @return A Polygon object initialized with the parsed x, y position and vertices.
     */
    private Polygon createPolygon(Matcher matcher){
        float x = Float.parseFloat(matcher.group("x"));
        float y = Float.parseFloat(matcher.group("y"));
        float[] localVertices = parseLocalVertices(matcher.group("points"));
        Polygon polygon = new Polygon(localVertices);
        polygon.setPosition(x, y);
        return polygon;
    }
    /**
     * Parses local vertices data into an array of floats for creating Polygon objects.
     *
     * @param points The string containing vertices data in "x,y x,y ..." format.
     * @return An array of floats representing local vertices coordinates.
     */
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
