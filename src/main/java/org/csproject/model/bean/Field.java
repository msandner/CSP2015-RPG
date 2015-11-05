package org.csproject.model.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maike Keune-Staab on 04.10.2015 - 13.10.2015
 */
public class Field {

    private String groundImage;
    private String decoImage;

    private NavigationPoint defaultStart;

    private Tile[][] groundTiles;
    private Tile[][] decoTiles;

    private Map<String, NavigationPoint> startPoints;


    public Field() {
        this.startPoints = new HashMap<>();
    }

    public NavigationPoint getStart(String startName) {
        NavigationPoint startPoint = startPoints.get(startName);
        if (startPoint == null) {
            startPoint = defaultStart;
        }
        return startPoint;
    }

    public void setStartPoint(String name, NavigationPoint startPoint) {
        if(defaultStart == null)
        {
            defaultStart = startPoint;
        }
        startPoints.put(name, startPoint);
    }

    public void setStartPoint(NavigationPoint startPoint) {
        defaultStart = startPoint;
    }

    public void setGroundTiles(Tile[][] tiles) {
        this.groundTiles = tiles;
    }

    public Tile[][] getGroundTiles() {
        return groundTiles;
    }

    public NavigationPoint getTownTile() {
        for(int y = 0; y < groundTiles.length; y++) {
            for(int x = 0; x < groundTiles[y].length; x++) {
                if (groundTiles[y][x].isTownTile()) {
                    return new NavigationPoint(x, y);
                }
            }
        }
        return null;
    }

    public String getGroundImage() {
        return groundImage;
    }

    public void setGroundImage(String groundImage) {
        this.groundImage = groundImage;
    }

    public String getDecoImage() {
        return decoImage;
    }

    public void setDecoImage(String decoImage) {
        this.decoImage = decoImage;
    }

    public void setDecoTiles(Tile[][] decoTiles) {
        this.decoTiles = decoTiles;
    }

    public Tile[][] getDecoTiles() {
        return decoTiles;
    }
}
