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


/*
    public void overritePartOfTiles(Tile[][] x, Tile[][] y) {
        int i, j;

        for(i = 0; i < ; i++) {
            for(j = 0; i< ; j++) {
                    x[i][] = y[i][0];

                }
            }
        }

    }*/

    public Tile[][] getTiles() {
        return tiles;
    }

    public NavigationPoint getTownTile() {
        for(int y = 0; y < tiles.length; y++) {
            for(int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x].isTownTile()) {
                    return new NavigationPoint(x, y);
                }
            }
        }
        return null;
    }
}
