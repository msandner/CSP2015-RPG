package org.csproject.model.field;

import java.util.*;

import org.csproject.model.actors.Npc;
import org.csproject.model.general.NavigationPoint;

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
    private Collection<Npc> npcs;

    public Field() {
        this(null, null);
    }

    public Field(Tile[][] groundTiles, Tile[][] decoTiles) {
        this.startPoints = new ArrayList<>();
        this.teleportPoints = new ArrayList<>();
        this.npcs = new ArrayList<>();

        this.groundTiles = groundTiles;
        this.decoTiles = decoTiles;
    }

    /**
     * Maike Keune-Staab
     * returns the startPoint with the given nameor if no startPoint has the given name,
     * a default startPoint is returned
     *
     * @param startName
     * @return
     */
    public StartPoint getStart(String startName) {
        Map<String, StartPoint> startPointMap = new HashMap<>();
        StartPoint defaultStartPoint = null;

        for (StartPoint startPoint : startPoints) {
            {
                if (defaultStartPoint == null) {
                    defaultStartPoint = startPoint;
                }
                startPointMap.put(startPoint.getName(), startPoint);
            }
        }

        if (startPointMap.get(startName) != null) {
            return startPointMap.get(startName);
        }

        return defaultStartPoint;
    }

    /**
     * Maike Keune-Staab
     * returns a collection of all teleportPoints that have the given name or a singletonList with a default
     * teleporter is returned, if no teleporter matches the given name.
     *
     * @param teleportName
     * @return
     */
    public Collection<TeleportPoint> getTeleporter(String teleportName) {
        Map<String, Collection<TeleportPoint>> teleportPointMap = new HashMap<>();
        TeleportPoint defaultTeleportPoint = null;

        for (TeleportPoint teleportPoint : teleportPoints) {
            {
                if (defaultTeleportPoint == null) {
                    defaultTeleportPoint = teleportPoint;
                }
                Collection<TeleportPoint> teleportPoints = teleportPointMap.get(teleportPoint.getName());
                if (teleportPoints == null) {
                    teleportPoints = new ArrayList<>();
                    teleportPointMap.put(teleportPoint.getName(), teleportPoints);
                }
                teleportPoints.add(teleportPoint);
            }
        }

        if (teleportPointMap.get(teleportName) != null) {
            return teleportPointMap.get(teleportName);
        }

        return Collections.singletonList(defaultTeleportPoint);
    }

    public Npc getNpc (int x, int y){
        for (Npc npc : npcs) {
            if(npc.getX() == x && npc.getY()== y){
                return npc;
            }
        }
        return null;
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

    /**
     * Maike Keune-Staab
     * returns the number of tiles in one row
     *
     * @return
     */
    public double getWidth() {
        if (groundTiles != null && decoTiles != null) {
            return Math.max(groundTiles[0].length, decoTiles[0].length);
        } else if (groundTiles != null) {
            return groundTiles[0].length;
        } else if (decoTiles != null) {
            return decoTiles[0].length;
        }
        return 0;
    }

    /**
     * Maike Keune-Staab
     * returns the number of rows
     *
     * @return
     */
    public double getHeight() {
        if (groundTiles != null && decoTiles != null) {
            return Math.max(groundTiles.length, decoTiles.length);
        } else if (groundTiles != null) {
            return groundTiles.length;
        } else if (decoTiles != null) {
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

    public Collection<Npc> getNpcs() {
        return npcs;
    }
}
