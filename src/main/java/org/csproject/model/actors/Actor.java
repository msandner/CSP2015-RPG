package org.csproject.model.actors;

import org.csproject.model.general.NavigationPoint;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public abstract class Actor extends NavigationPoint{
    /**
     * creating the abstract actor
     */
    protected String type; //mage, knight or thief

    public Actor(String name, String type, int x, int y) {
        super(x, y,name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
