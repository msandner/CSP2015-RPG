package org.csproject.service;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.csproject.model.Constants;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.Tile;
import org.csproject.model.bean.Town;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class ScreenFactory {
    public Node buildNode(Field field) {
        Tile[][] groundMatrix = field.getGroundTiles();
        Tile[][] decoTiles = field.getDecoTiles();

        String groundImage = field.getGroundImage();
        String decoImage = field.getDecoImage();

        Group ground = convert(groundMatrix, groundImage);
        Group deco = convert(decoTiles, decoImage);

        ground.getChildren().add(deco);


        return ground;
    }

    private Group convert(Tile[][] matrix, String imageUrl) {
        Group root = new Group();
        if(matrix == null){
            return root;
        }
        Map<String, Image> images = new HashMap<>();
        images.put(imageUrl, new Image(imageUrl));

        HashMap<String, Image> imageMap = new HashMap<>();
        imageMap.put(imageUrl, new Image(imageUrl));
        for(int rowIndex = 0; rowIndex < matrix.length ; rowIndex++) //vertikal durchs bild
        {
            Tile[] row = matrix[rowIndex];
            for(int colIndex = 0; colIndex < row.length; colIndex++) //horizontal durchs bild
            {
                Tile currentTile = row[colIndex];
                if (currentTile != null) {
                    Image currentImage;
                    if (currentTile.getTileImage() != null) {
                        currentImage = imageMap.get(currentTile.getTileImage());
                        if (currentImage == null) {
                            currentImage = new Image(currentTile.getTileImage());
                            images.put(currentTile.getTileImage(), currentImage);
                        }
                    } else {
                        currentImage = images.get(imageUrl);
                    }
                    ImageView imageView = new ImageView(currentImage);
                    Rectangle2D viewPort = new Rectangle2D(currentTile.getX() * Constants.TILE_SIZE, currentTile.getY() * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
                    imageView.setViewport(viewPort);
                    imageView.setTranslateX(colIndex * Constants.TILE_SIZE);
                    imageView.setTranslateY(rowIndex * Constants.TILE_SIZE);
                    root.getChildren().add(imageView);
                }
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
        imageMap.put("outside3", new Image("images/tiles/Outside3.png"));
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
                Rectangle2D viewPort = new Rectangle2D(currentTile.getX()* Constants.TILE_SIZE, currentTile.getY()* Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
                imageView.setViewport(viewPort);
                imageView.setTranslateX(colIndex * Constants.TILE_SIZE);
                imageView.setTranslateY(rowIndex* Constants.TILE_SIZE);
                root.getChildren().add(imageView);

            }
        }
        return root;
    }
}
