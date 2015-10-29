package org.csproject.model.bean;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Maike Keune-Staab on 13.10.2015.
 */
public class Tile {

    //x and y describe the position in the spritesheet
    private final int x;
    private final int y;
    public static final double TILE_SIZE = 32;
    private boolean walkable;

    Image image;
    ImageView iview = new ImageView();


    /**
     * Creates the tile object.
     *
     * @param x - x location of the tile desired on the image given
     * @param y - y location of the tile desired on the image given
     * @param walkable - can the character walk on the tile?
     * @param tileImage - source image of the tile - used in ScreenFactory
     */
    public Tile(int x, int y, boolean walkable, String tileImage) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.image = new Image(tileImage);
        this.iview.setImage(image);
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

    public Image getTileImage() {
        return image;
    }

    public ImageView getTileImageView() {
        return iview;
    }
}
