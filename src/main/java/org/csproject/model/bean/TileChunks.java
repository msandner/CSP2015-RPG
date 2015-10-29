package org.csproject.model.bean;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Created by Maren on 24.10.2015.
 */
public class TileChunks {

    public Tile[][] Trees4x4(Field field) {

        String strimage = "images/tiles/Outside3.png";
        Tile[][] treetiles = new Tile[2][2];

        treetiles[1][0] = new Tile(3, 15, false, strimage);
        treetiles[1][1] = new Tile(4, 15, false, strimage);
        treetiles[0][0] = new Tile(3, 16, false, strimage);
        treetiles[0][1] = new Tile(4, 16, false, strimage);

        return treetiles;
        }


    }
