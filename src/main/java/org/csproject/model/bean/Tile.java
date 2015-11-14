package org.csproject.model.bean;

import java.util.Random;

/**
 * @author Maike Keune-Staab on 13.10.2015.
 */
public class Tile {

    private boolean townTile;

    //x and y describe the position in the spritesheet
    private int x;
    private int y;
    private boolean walkable;
    private String tileImage;

    /**
     * Creates the tile object.
     *
     * @param x         x location of the tile desired on the image given
     * @param y         y location of the tile desired on the image given
     * @param walkable  can the character walk on the tile?
     * @param tileImage special image loaded just for this tile
     */
    public Tile(int x, int y, boolean walkable, String tileImage) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.tileImage = tileImage;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Tile(int x, int y, boolean walkable) {
         this(x, y, walkable, null);

    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public String getTileImage() {
        return this.tileImage;
    }

    public void setTownTile() {
        this.townTile = true;
    }

    public void setX(int x)
    {
      this.x = x;
    }

    public void setY(int y)
    {
      this.y = y;
    }

  public boolean isTownTile() {
        if (this.townTile)
            return true;
        else
            return false;
    }

    public void setTileImage(String tileImage) {
        this.tileImage = tileImage;
    }

}
