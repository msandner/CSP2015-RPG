package org.csproject.service;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.Tile;
import org.csproject.model.bean.Town;
import java.util.HashMap;
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
        Image image = new Image("images/tiles/Outside.png");

        HashMap<String, Image> imageMap = new HashMap<>();
        imageMap.put("world", new Image("images/tiles/World.png"));
        imageMap.put("outside", new Image("images/tiles/Outside.png"));
        for(int rowIndex = 0; rowIndex < matrix.length ; rowIndex++) //vertikal durchs bild
        {
            Tile[] row = matrix[rowIndex];
            for(int colIndex = 0; colIndex < row.length; colIndex++) //horizontal durchs bild
            {
                Tile currentTile = row[colIndex];
                ImageView imageView = new ImageView(imageMap.get(currentTile.getTileImage()));
                Rectangle2D viewPort = new Rectangle2D(currentTile.getX()* Tile.TILE_SIZE, currentTile.getY()*Tile.TILE_SIZE,Tile.TILE_SIZE, Tile.TILE_SIZE);
                imageView.setViewport(viewPort);
                imageView.setTranslateX(colIndex * Tile.TILE_SIZE);
                imageView.setTranslateY(rowIndex*Tile.TILE_SIZE);
                root.getChildren().add(imageView);

            }
        }
        return root;

    }

    public Node buildNode(Town town) {
        Tile[][] matrix = town.getTiles();
        Tile[] row;
        Tile currentTile;
        Group root = new Group();

        HashMap<String, Image> imageMap = new HashMap<>();
        imageMap.put("outside", new Image("images/tiles/Outside.png"));
        imageMap.put("outside2", new Image("images/tiles/Outside2.png"));
        imageMap.put("inside", new Image("images/tiles/Inside.png"));
        imageMap.put("inside2", new Image("images/tiles/Inside2.png"));
        imageMap.put("house", new Image("images/tiles/HouseTiles.png"));

        for(int rowIndex = 0; rowIndex < matrix.length ; rowIndex++)
        {
            row = matrix[rowIndex];
            for(int colIndex = 0; colIndex < row.length; colIndex++)
            {
                currentTile = row[colIndex];
                ImageView imageView = new ImageView(imageMap.get(currentTile.getTileImage()));
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
