package org.csproject.model.field;

/**
 * @author Maike Keune-Staab on 13.10.2015.
 */
public class Tile {

    private boolean walkable;
    private boolean complex;

    //x and y describe the position in the spritesheet
    private int x;
    private int y;

    private String tileImage;

    public Tile(int x, int y, boolean walkable) {
        this(x, y, walkable, null);
    }

    public Tile(int x, int y, boolean walkable, String tileImage) {
        this(x, y, walkable, false, tileImage);
    }

    /**
     * Creates the tile object.
     *
     * @param x         x location of the tile desired on the image given
     * @param y         y location of the tile desired on the image given
     * @param walkable  can the character walk on the tile?
     * @param complex   complex tiles will be rendered depending on their neighbour tiles
     * @param tileImage special image loaded just for this tile
     */
    public Tile(int x, int y, boolean walkable, boolean complex, String tileImage) {
        this.x = x;
        this.y = y;
        this.complex = complex;
        this.walkable = walkable;
        this.tileImage = tileImage;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public boolean isComplex() {
        return complex;
    }

    public void setComplex(boolean complex) {
        this.complex = complex;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public String getTileImage() {
        return this.tileImage;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTileImage(String tileImage) {
        this.tileImage = tileImage;
    }
}
