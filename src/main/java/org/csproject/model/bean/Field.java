package org.csproject.model.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
public class Field {
    private NavigationPoint defaultStart;

    private Map<String, NavigationPoint> startPoints;

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
}
