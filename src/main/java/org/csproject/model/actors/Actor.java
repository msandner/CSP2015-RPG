package org.csproject.model.actors;

import org.csproject.model.bean.NavigationPoint;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public abstract class Actor {

    protected NavigationPoint position;
    protected String name;
    protected String type;

    public Actor(String name, String type) {
        this.name = name;
        this.type = type;
        this.position = new NavigationPoint(0,0);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public NavigationPoint getPosition() {
        return position;
    }
}
