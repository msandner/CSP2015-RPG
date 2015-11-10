package org.csproject.service;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.csproject.model.Constants;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.Tile;
import org.csproject.model.bean.Town;
import org.springframework.stereotype.Component;


/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class ScreenFactory {
    public interface TileClickCallback{

      void onClick(Tile tile, ImageView tileImageView);
    }

    public Node buildNode(Field field) {
        Tile[][] groundMatrix = field.getGroundTiles();
        Tile[][] decoTiles = field.getDecoTiles();

        String groundImage = field.getGroundImage();
        String decoImage = field.getDecoImage();

        Group ground = convert(groundMatrix, Constants.CS_DIR + groundImage + Constants.CS_POST_FIX, null);
        Group deco = convert(decoTiles, Constants.CS_DIR + decoImage + Constants.CS_POST_FIX, null);

        ground.getChildren().add(deco);

        return ground;
    }

    public Group convert(Tile[][] matrix, String imageUrl, final TileClickCallback tileClickCallback) {
        Group root = new Group();
        if(matrix == null){
            return root;
        }
        Map<String, Image> images = new HashMap<>();
        images.put(imageUrl, new Image(imageUrl));
        String editorTileURL = null;
        if(tileClickCallback != null)
        {
          editorTileURL = Constants.CS_DIR + Constants.EDITOR_DECO_TILE + Constants.CS_POST_FIX;
          images.put(editorTileURL, new Image(getClass().getResourceAsStream(editorTileURL)));
        }

        HashMap<String, Image> imageMap = new HashMap<>();
        imageMap.put(imageUrl, new Image(imageUrl));
        for(int rowIndex = 0; rowIndex < matrix.length ; rowIndex++) //vertikal durchs bild
        {
            Tile[] row = matrix[rowIndex];
            for(int colIndex = 0; colIndex < row.length; colIndex++) //horizontal durchs bild
            {
                final Tile currentTile = row[colIndex];
                if (currentTile != null) {
                    Image currentImage;
                    if (currentTile.getTileImage() != null) {
                        currentImage = imageMap.get(currentTile.getTileImage());
                        if (currentImage == null) {
                            currentImage = new Image(Constants.CS_DIR + currentTile.getTileImage()
                                    + Constants.CS_POST_FIX);
                            images.put(currentTile.getTileImage(), currentImage);
                        }
                    } else {
                        currentImage = images.get(imageUrl);
                    }
                    final ImageView imageView = new ImageView(currentImage);
                    Rectangle2D viewPort = new Rectangle2D(currentTile.getX() * Constants.TILE_SIZE, currentTile.getY() * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
                    imageView.setViewport(viewPort);
                    imageView.setTranslateX(colIndex * Constants.TILE_SIZE);
                    imageView.setTranslateY(rowIndex * Constants.TILE_SIZE);
                    if(tileClickCallback != null) {
                        EventHandler<MouseEvent> onMouseClicked = new EventHandler<MouseEvent>()
                      {
                        @Override
                        public void handle(MouseEvent mouseEvent)
                        {
                          tileClickCallback.onClick(currentTile, imageView);
                        }
                      };
                      imageView.setOnMouseClicked(onMouseClicked);

                      ImageView editorView = new ImageView(images.get(editorTileURL));
                      editorView.setOnMouseClicked(onMouseClicked);
                      editorView.setTranslateX(colIndex * Constants.TILE_SIZE);
                      editorView.setTranslateY(rowIndex * Constants.TILE_SIZE);
                      root.getChildren().add(editorView);
                    }
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
