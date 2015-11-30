package org.csproject.model.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.csproject.model.bean.NavigationPoint;

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
        this(null, null);
    }

    public Field(Tile[][] groundTiles, Tile[][] decoTiles) {
        this.startPoints = new ArrayList<>();
        this.teleportPoints = new ArrayList<>();

        this.groundTiles = groundTiles;
        this.decoTiles = decoTiles;
    }

    public StartPoint getStart(String startName) {
        Map<String, StartPoint> startPointMap = new HashMap<>();
        StartPoint defaultStartPoint = null;

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

    public TeleportPoint getTeleporter(String teleportName) {
        Map<String, TeleportPoint> startPointMap = new HashMap<>();
        TeleportPoint defaultTeleportPoint = null;

        for (TeleportPoint teleportPoint : teleportPoints) {
            {
                if(defaultTeleportPoint == null){
                    defaultTeleportPoint = teleportPoint;
                }
                startPointMap.put(teleportPoint.getName(), teleportPoint);
            }
        }

        if(startPointMap.get(teleportName) != null) {
            return startPointMap.get(teleportName);
        }

        return defaultTeleportPoint;
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
        if(groundTiles != null && decoTiles != null) {
            return Math.max(groundTiles[0].length, decoTiles[0].length);
        }
        else if(groundTiles != null) {
            return groundTiles[0].length;
        }
        else if(decoTiles != null) {
            return decoTiles[0].length;
        }
        return 0;
    }

    public double getHeight() {
        if(groundTiles != null && decoTiles != null) {
            return Math.max(groundTiles.length, decoTiles.length);
        }
        else if(groundTiles != null) {
            return groundTiles.length;
        }
        else if(decoTiles != null) {
            return decoTiles.length;
        }
        return 0;
    }

    public Collection<StartPoint> getStartPoints() {
        return startPoints;
    }

    public Collection<TeleportPoint> getTeleportPoints() {
        return teleportPoints;
    }
}
