package org.csproject.model.bean;

import org.csproject.model.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maike Keune-Staab on 04.10.2015 - 13.10.2015
 */
public class Field {

    private String groundImage;
    private String decoImage;

    private Tile[][] groundTiles;
    private Tile[][] decoTiles;

    private Collection<StartPoint> startPoints;
    private Collection<TeleportPoint> teleportPoints;

    public Field() {
        this.startPoints = new ArrayList<>();
        this.teleportPoints = new ArrayList<>();
    }

    public NavigationPoint getStart(String startName) {

        Map<String, NavigationPoint> startPointMap = new HashMap<>();
        NavigationPoint defaultStartPoint = null;

        for (StartPoint startPoint : startPoints) {
            {
                if(defaultStartPoint == null){
                    defaultStartPoint = startPoint;
                }
                startPointMap.put(startPoint.getName(), startPoint);
            }
        }

        if(startPointMap.get(startName) != null) {
            return startPointMap.get(startName);
        }

        return defaultStartPoint;
    }

    public void setGroundTiles(Tile[][] tiles) {
        this.groundTiles = tiles;
    }

    public Tile[][] getGroundTiles() {
        return groundTiles;
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

    public double getWidth() {
        if(groundTiles == null || groundTiles.length == 0 || groundTiles[0].length == 0) {
            return 0.0;
        }
        return groundTiles[0].length * Constants.TILE_SIZE;
    }

    public double getHeight() {
        if(groundTiles == null || groundTiles.length == 0) {
            return 0.0;
        }
        return groundTiles.length * Constants.TILE_SIZE;
    }

    public Collection<StartPoint> getStartPoints() {
        return startPoints;
    }

    public Collection<TeleportPoint> getTeleportPoints() {
        return teleportPoints;
    }
}
