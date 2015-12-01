package org.csproject.model.bean;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
public class NavigationPoint {
    private int x;
    private int y;

    private String name;

    public NavigationPoint(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
