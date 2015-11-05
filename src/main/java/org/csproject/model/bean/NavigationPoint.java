package org.csproject.model.bean;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
public class NavigationPoint {
    private int x;
    private int y;

    public NavigationPoint(int x, int y) {
        this.x = x;
        this.y = y;
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
}
