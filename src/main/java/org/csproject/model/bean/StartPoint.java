package org.csproject.model.bean;

import org.csproject.model.bean.NavigationPoint;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public class StartPoint extends NavigationPoint {
    private final String name;


    public StartPoint(int a, int b, String name) {
        super(a, b);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
