package org.csproject.model.field;

import org.csproject.model.general.NavigationPoint;
import org.csproject.service.DungeonHelper;

/**
 * Created by Brett on 11/9/2015.
 * @author Brett, Maike Keune-Staab
 *
 * The TeleportPoint object is supposed to be used as an object that
 * "teleports" the player from one map to another. Basically, it is a
 * transition between two maps.
 */
public class TeleportPoint extends NavigationPoint {

    private final boolean random;

    private Integer randomHeight;
    private Integer randomWidth;
    private Integer randomFloors;

    private String targetField;
    private String targetPoint;
    private String sourceField;
    private String sourcePoint;

    private DungeonHelper.Type randomType;

    /**
     * @param x - number of horizontal tiles on the field
     * @param y - number of vertical tiles on the field
     * @param targetField - name of the field where point will teleports you
     * @param targetPoint - name of the  startPoint inside the target field
     */
    public TeleportPoint(boolean random, int x, int y, String name, String targetField, String targetPoint,
                         String sourceField, String sourcePoint, Integer randomHeight,
                         Integer randomWidth, Integer randomFloors, DungeonHelper.Type randomType) {
        super(x, y, name);

        this.random = random;
        this.targetField = targetField;
        this.targetPoint = targetPoint;
        this.sourceField = sourceField;
        this.sourcePoint = sourcePoint;
        this.randomHeight = randomHeight;
        this.randomWidth = randomWidth;
        this.randomFloors = randomFloors;
        this.randomType = randomType;
    }

    public boolean isRandom()
    {
        return random;
    }

    public String getTargetField() {
        return targetField;
    }

    public String getTargetPoint() {
        return targetPoint;
    }

    public String getSourceField()
    {
      return sourceField;
    }

    public String getSourcePoint()
    {
        return sourcePoint;
    }

    public Integer getRandomHeight()
    {
        return randomHeight;
    }

    public Integer getRandomWidth()
    {
        return randomWidth;
    }

    public Integer getRandomFloors()
    {
        return randomFloors;
    }

    public DungeonHelper.Type getRandomType()
    {
        return randomType;
    }

    public void setRandomHeight(Integer randomHeight) {
        this.randomHeight = randomHeight;
    }

    public void setRandomWidth(Integer randomWidth) {
        this.randomWidth = randomWidth;
    }

    public void setRandomFloors(Integer randomFloors) {
        this.randomFloors = randomFloors;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public void setTargetPoint(String targetPoint) {
        this.targetPoint = targetPoint;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public void setSourcePoint(String sourcePoint) {
        this.sourcePoint = sourcePoint;
    }

    public void setRandomType(DungeonHelper.Type randomType) {
        this.randomType = randomType;
    }
}
