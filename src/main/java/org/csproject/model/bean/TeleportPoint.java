package org.csproject.model.bean;

/**
 * Created by Brett on 11/9/2015.
 * @author Brett, Maike Keune-Staab
 *
 * The TeleportPoint object is supposed to be used as an object that
 * "teleports" the player from one map to another. Basically, it is a
 * transition between two maps.
 */
public class TeleportPoint extends NavigationPoint{

    private String targetField;
    private String targetPoint;
    private NavigationPoint fromMapPoint, toMapPoint;
    private String fromMap;
    private String toMap;

    public TeleportPoint(NavigationPoint fromMapPoint, NavigationPoint toMapPoint, String fromMap, String toMap) {
        super(0, 0);
        this.fromMapPoint = fromMapPoint;
        this.toMapPoint = toMapPoint;
        this.fromMap = fromMap;
        this.toMap = toMap;
    }

    /**
     * @param x - number of horizontal tiles on the field
     * @param y - number of vertical tiles on the field
     * @param targetField - name of the field where point will teleports you
     * @param targetPoint - name of the  startPoint inside the target field
     */
    public TeleportPoint(int x, int y, String targetField, String targetPoint) {
        super(x, y);

        this.targetField = targetField;
        this.targetPoint = targetPoint;
    }

    public void setFromMapPoint(NavigationPoint point) {
        fromMapPoint.setX(point.getX());
        fromMapPoint.setY(point.getY());
    }

    public void setToMapPoint(NavigationPoint point) {
        toMapPoint.setX(point.getX());
        toMapPoint.setY(point.getY());
    }

    public void setFromMap(String fromMap) {
        this.fromMap = fromMap;
    }

    public void setToMap(String toMap) {
        this.toMap = toMap;
    }

    public String getTargetField() {
        return targetField;
    }

    public String getTargetPoint() {
        return targetPoint;
    }
}
