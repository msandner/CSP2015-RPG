package org.csproject.model.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maike Keune-Staab on 04.10.2015 - 13.10.2015
 */
public class Field {
    private NavigationPoint defaultStart;

    private Map<String, NavigationPoint> startPoints;
    private Tile[][] tiles;

//    private Tile[][] tiles; // TODO for maren: create class Tile which represents one tile on the field (with an image and a boolean like "walkable")

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


    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
