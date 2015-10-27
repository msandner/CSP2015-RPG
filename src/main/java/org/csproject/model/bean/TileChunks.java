package org.csproject.model.bean;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Created by Maren on 24.10.2015.
 */
public class TileChunks {

    public List<ImageView> Trees4x4(Field field) {
        List<ImageView> listview = null;
        Tile[][] matrix = field.getTiles();
        Image image = new Image("images/tiles/Outside3.png");
        for(int rowIndex = 0; rowIndex < matrix.length ; rowIndex++) {
            Tile[] row = matrix[rowIndex];
            for (int colIndex = 0; colIndex < row.length; colIndex++) {
                Tile tile = row[colIndex];

            }
        }


        return listview;
    }
}
