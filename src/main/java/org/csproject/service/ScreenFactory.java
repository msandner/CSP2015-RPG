package org.csproject.service;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.Tile;
import org.springframework.stereotype.Component;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class ScreenFactory {
    // todo read field and build Jafa FX node according to the field
    public Node buildNode(Field field) {
        Tile[][] matrix = field.getTiles();
        Group root = new Group();
        Image image = new Image("images/Outside.png");
        for(int rowIndex = 0; rowIndex < matrix.length ; rowIndex++) //vertikal durchs bild
        {
            Tile[] row = matrix[rowIndex];
            for(int colIndex = 0; colIndex < row.length; colIndex++) //horizontal durchs bild
            {
                Tile currentTile = row[colIndex];
                ImageView imageView = new ImageView(image);
                Rectangle2D viewPort = new Rectangle2D(currentTile.getX()* Tile.TILE_SIZE, currentTile.getY()*Tile.TILE_SIZE,Tile.TILE_SIZE, Tile.TILE_SIZE);
                imageView.setViewport(viewPort);
                imageView.setTranslateX(colIndex * Tile.TILE_SIZE);
                imageView.setTranslateY(rowIndex*Tile.TILE_SIZE);
                root.getChildren().add(imageView);

            }
        }
        return root;

    }
}
