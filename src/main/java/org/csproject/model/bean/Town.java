package org.csproject.model.bean;

import org.csproject.model.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brett on 10/19/2015.
 */
public class Town {
    private Map<String, NavigationPoint> startPoints;
    private Tile[][] tiles;

    public Town() {
        startPoints = new HashMap<>();
        startPoints.put("townEntrance", new NavigationPoint(18 * Constants.TILE_SIZE, 18 * Constants.TILE_SIZE));
    }

    public NavigationPoint getTownEntrance() {
        return startPoints.get("townEntrance");
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
