package org.csproject.model.bean;

import javax.swing.text.html.ImageView;

/**
 * @author Maike Keune-Staab on 13.10.2015.
 */
public class Tile {

    private final int x;
    private final int y;
    public static final double TILE_SIZE = 32;


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }




}
