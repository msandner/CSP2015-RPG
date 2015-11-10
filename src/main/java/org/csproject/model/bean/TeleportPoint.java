package org.csproject.model.bean;

/**
 * Created by Brett on 11/9/2015.
 *
 * The TeleportPoint object is supposed to be used as an object that
 * "teleports" the player from one map to another. Basically, it is a
 * transition between two maps.
 */
public class TeleportPoint {

    NavigationPoint fromMapPoint, toMapPoint;
    String fromMap;
    String toMap;

    public TeleportPoint(NavigationPoint fromMapPoint, NavigationPoint toMapPoint, String fromMap, String toMap) {
        this.fromMapPoint = fromMapPoint;
        this.toMapPoint = toMapPoint;
        this.fromMap = fromMap;
        this.toMap = toMap;
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
}
