package org.csproject.model.field;

import java.util.List;
import java.util.Map;

/**
 * @author Maike Keune-Staab on 23.11.2015.
 */
public class DungeonDetails {
    private int height;
    private int width;
    private int floors;
    private int blockMinRows;
    private int blockMinCols;
    private int pathWidth;

    private String defaultGroundImage;
    private String defaultDecoImage;
    private String targetMap;
    private String targetPoint;
    private String sourceMap;
    private String sourcePoint;

    private Map<TileInfoFilter, List<Field>> tileMapping;

    public DungeonDetails(int height, int width, int floors, int blockMinRows, int blockMinCols, int pathWidth,
                          String defaultGroundImage, String defaultDecoImage, String targetMap, String targetPoint,
                          String sourceMap, String sourcePoint, Map<TileInfoFilter, List<Field>> tileMapping) {
        this.height = height;
        this.width = width;
        this.floors = floors;
        this.blockMinRows = blockMinRows;
        this.blockMinCols = blockMinCols;
        this.pathWidth = pathWidth;
        this.defaultGroundImage = defaultGroundImage;
        this.defaultDecoImage = defaultDecoImage;
        this.targetMap = targetMap;
        this.targetPoint = targetPoint;
        this.sourceMap = sourceMap;
        this.sourcePoint = sourcePoint;
        this.tileMapping = tileMapping;
    }

    public String getDefaultGroundImage() {
        return defaultGroundImage;
    }

    public String getDefaultDecoImage() {
        return defaultDecoImage;
    }

    public String getTargetMap() {
        return targetMap;
    }

    public String getTargetPoint() {
        return targetPoint;
    }

    public String getSourceMap() {
        return sourceMap;
    }

    public String getSourcePoint() {
        return sourcePoint;
    }

    public int getFloors() {
        return floors;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Map<TileInfoFilter, List<Field>> getTileMapping() {
        return tileMapping;
    }

    public int getBlockMinRows() {
        return blockMinRows;
    }

    public void setBlockMinRows(int blockMinRows) {
        this.blockMinRows = blockMinRows;
    }

    public int getBlockMinCols() {
        return blockMinCols;
    }

    public void setBlockMinCols(int blockMinCols) {
        this.blockMinCols = blockMinCols;
    }

    public int getPathWidth() {
        return pathWidth;
    }

    public void setPathWidth(int pathWidth) {
        this.pathWidth = pathWidth;
    }
}
