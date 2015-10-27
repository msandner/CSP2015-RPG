package org.csproject.model.bean;


/**
 * @author Maike Keune-Staab on 13.10.2015.
 */
public class Tile {

    //x and y describe the position in the spritesheet
    private final int x;
    private final int y;
    public static final double TILE_SIZE = 32;
    private boolean walkable;



    public Tile(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }
}
