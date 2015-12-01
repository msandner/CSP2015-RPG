package org.csproject.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.csproject.model.Constants;
import org.csproject.model.field.Field;
import org.csproject.model.field.Tile;
import org.springframework.stereotype.Component;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class ScreenFactory {
    public interface TileClickCallback {
        void onClick(Tile tile, boolean controlDown, boolean shiftDown, int col, int row);
    }

    public Node buildNode(Field field) {
        Tile[][] groundMatrix = field.getGroundTiles();
        Tile[][] decoTiles = field.getDecoTiles();

        String groundImage = field.getGroundImage();
        String decoImage = field.getDecoImage();

        Pane ground = convert(groundMatrix, CS_DIR + groundImage + CS_POST_FIX, Color.BLACK, null);
        Pane deco = convert(decoTiles, CS_DIR + decoImage + CS_POST_FIX, null, null);

        ground.getChildren().add(deco);

        return ground;
    }

    public Pane convert(final Tile[][] matrix, final String defaultImage, Color backGroundColor,
                        final TileClickCallback tileClickCallback) {
        Pane root = new Pane();

        if (matrix == null) {
            return root;
        }

        root.setPrefSize(matrix[0].length * TILE_SIZE, matrix.length * TILE_SIZE);
        if (backGroundColor != null) {
            root.backgroundProperty().set(new Background(new BackgroundFill(backGroundColor,
                    CornerRadii.EMPTY, Insets.EMPTY)));
        }

        final Map<String, Image> images = new HashMap<>();
        String editorTileURL;
        String editorBlockingURL;
        if (tileClickCallback != null) {
            editorTileURL = CS_DIR + EDITOR_DECO_TILE + CS_POST_FIX;
            images.put(EDITOR_DECO_TILE, new Image(getClass().getResourceAsStream(editorTileURL)));
            editorBlockingURL = CS_DIR + Constants.EDITOR_BLOCKIG_TILE + CS_POST_FIX;
            images.put(EDITOR_BLOCKIG_TILE, new Image(getClass().getResourceAsStream(editorBlockingURL)));
        }

        final Map<Tile, Group> tileGroupMap = new HashMap<>();

        for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) { //vertikal durchs bild
            Tile[] row = matrix[rowIndex];
            for (int colIndex = 0; colIndex < row.length; colIndex++) { //horizontal durchs bild
                final Tile currentTile = row[colIndex];
                if (currentTile != null) {
                    final Group tileGroup = new Group();
                    tileGroupMap.put(currentTile, tileGroup);
                    tileGroup.setTranslateX(colIndex * TILE_SIZE);
                    tileGroup.setTranslateY(rowIndex * TILE_SIZE);

                    final Tile[][] neighbours = getNeighbours(matrix, (int) (tileGroup.getTranslateX() / TILE_SIZE),
                            (int) (tileGroup.getTranslateY() / TILE_SIZE));

                    render(currentTile, neighbours, tileGroupMap, images, defaultImage);

                    if (tileClickCallback != null) {
                        tileGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                tileClickCallback.onClick(currentTile, mouseEvent.isControlDown(),
                                        mouseEvent.isShiftDown(), (int) (tileGroup.getTranslateX() / TILE_SIZE),
                                        (int) (tileGroup.getTranslateY() / TILE_SIZE));

                                final Tile[][] neighbours = getNeighbours(matrix,
                                        (int) (tileGroup.getTranslateX() / TILE_SIZE),
                                        (int) (tileGroup.getTranslateY() / TILE_SIZE));
                                render(currentTile, neighbours, tileGroupMap, images, defaultImage);
                            }
                        });
                    }
                    root.getChildren().add(tileGroup);
                }
            }
        }
        return root;
    }

    private void render(Tile currentTile, Tile[][] neighbours, Map<Tile, Group> tileGroupMap, Map<String, Image> images, String defaultImage) {

        Image currentImage;
        if (currentTile.getTileImage() != null) {
            currentImage = images.get(currentTile.getTileImage());
            if (currentImage == null) {
                currentImage = new Image(CS_DIR + currentTile.getTileImage()
                        + CS_POST_FIX);
                images.put(currentTile.getTileImage(), currentImage);
            }
        } else {
            currentImage = images.get(defaultImage);
        }

        Group group = tileGroupMap.get(currentTile);
        if (group != null) {
            group.getChildren().clear();
            if (images.get(EDITOR_DECO_TILE) != null) {
                group.getChildren().add(new ImageView(images.get(EDITOR_DECO_TILE)));
            }

            if (currentTile.isComplex()) {
                Tile upLeft = neighbours[0][0];
                Tile up = neighbours[0][1];
                Tile upRight = neighbours[0][2];

                Tile left = neighbours[1][0];
                Tile right = neighbours[1][2];

                Tile downLeft = neighbours[2][0];
                Tile down = neighbours[2][1];
                Tile downRight = neighbours[2][2];

                group.getChildren().add(getUpLeftPart(currentImage, currentTile, upLeft, up, left));
                group.getChildren().add(getUpRightPart(currentImage, currentTile, upRight, up, right));
                group.getChildren().add(getDownLeftPart(currentImage, currentTile, downLeft, down, left));
                group.getChildren().add(getDownRightPart(currentImage, currentTile, downRight, down, right));


            } else {
                final ImageView imageView = new ImageView(currentImage);
                group.getChildren().add(imageView);
                Rectangle2D viewPort = new Rectangle2D(currentTile.getX() * TILE_SIZE,
                        currentTile.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                imageView.setViewport(viewPort);
            }

            if(images.get(EDITOR_BLOCKIG_TILE) != null && !currentTile.isWalkable()) {
                group.getChildren().add(new ImageView(images.get(EDITOR_BLOCKIG_TILE)));
            }
        }
    }

    private Node getDownRightPart(Image currentImage, Tile currentTile, Tile downRight, Tile down, Tile right) {
        ImageView imageView = new ImageView(currentImage);

        int x = 0;
        int y = 0;

        if ((equals(currentTile, right))
                && (equals(currentTile, down))) {

            if (equals(currentTile, downRight)) {
                x = (int) (0.5 * TILE_SIZE);
                y = (int) (1.5 * TILE_SIZE);
            } else {
                x = (int) (1.5 * TILE_SIZE);
                y = (int) (0.5 * TILE_SIZE);
            }
        } else if ((equals(currentTile, right))
                && (!equals(currentTile, down))) {
            x = (int) TILE_SIZE / 2;
            y = (int) (2.5 * TILE_SIZE);
        } else if ((!equals(currentTile, right))
                && (equals(currentTile, down))) {
            x = (int) (1.5 * TILE_SIZE);
            y = (int) (1.5 * TILE_SIZE);
        } else if ((!equals(currentTile, right))
                && (!equals(currentTile, down))) {
            x = (int) (1.5 * TILE_SIZE);
            y = (int) (2.5 * TILE_SIZE);
        }

        Rectangle2D viewPort = new Rectangle2D(currentTile.getX() * TILE_SIZE * TILE_BLOCK_WIDTH + x,
                currentTile.getY() * TILE_SIZE * TILE_BLOCK_HEIGHT + y, TILE_SIZE / 2, TILE_SIZE / 2);
        imageView.setViewport(viewPort);
        imageView.setTranslateX(TILE_SIZE / 2);
        imageView.setTranslateY(TILE_SIZE / 2);

        return imageView;
    }

    private Node getDownLeftPart(Image currentImage, Tile currentTile, Tile downLeft, Tile down, Tile left) {
        ImageView imageView = new ImageView(currentImage);

        int x = 0;
        int y = 0;

        if ((equals(currentTile, left))
                && (equals(currentTile, down))) {
            if (equals(currentTile, downLeft)) {
                x = (int) TILE_SIZE;
                y = (int) (1.5 * TILE_SIZE);
            } else {
                x = (int) TILE_SIZE;
                y = (int) (0.5 * TILE_SIZE);
            }
        } else if ((equals(currentTile, left))
                && (!equals(currentTile, down))) {
            x = (int) TILE_SIZE;
            y = (int) (2.5 * TILE_SIZE);
        } else if ((!equals(currentTile, left))
                && (equals(currentTile, down))) {
            x = 0;
            y = (int) (1.5 * TILE_SIZE);
        } else if ((!equals(currentTile, left))
                && (!equals(currentTile, down))) {
            x = 0;
            y = (int) (2.5 * TILE_SIZE);
        }

        Rectangle2D viewPort = new Rectangle2D(currentTile.getX() * TILE_SIZE * TILE_BLOCK_WIDTH + x,
                currentTile.getY() * TILE_SIZE * TILE_BLOCK_HEIGHT + y, TILE_SIZE / 2, TILE_SIZE / 2);
        imageView.setViewport(viewPort);
        imageView.setTranslateY(TILE_SIZE / 2);

        return imageView;
    }

    private Node getUpRightPart(Image currentImage, Tile currentTile, Tile upRight, Tile up, Tile right) {
        ImageView imageView = new ImageView(currentImage);

        int x = 0;
        int y = 0;

        if ((equals(currentTile, right))
                && (equals(currentTile, up))) {
            if (equals(currentTile, upRight)) {
                x = (int) TILE_SIZE / 2;
                y = (int) (2 * TILE_SIZE);
            } else {
                x = (int) (1.5 * TILE_SIZE);
                y = 0;
            }
        } else if ((equals(currentTile, right))
                && (!equals(currentTile, up))) {
            x = (int) TILE_SIZE / 2;
            y = (int) TILE_SIZE;
        } else if ((!equals(currentTile, right))
                && (equals(currentTile, up))) {
            x = (int) (1.5 * TILE_SIZE);
            y = (int) (2 * TILE_SIZE);
        } else if ((!equals(currentTile, right))
                && (!equals(currentTile, up))) {
            x = (int) (1.5 * TILE_SIZE);
            y = (int) TILE_SIZE;
        }

        Rectangle2D viewPort = new Rectangle2D(currentTile.getX() * TILE_SIZE * TILE_BLOCK_WIDTH + x,
                currentTile.getY() * TILE_SIZE * TILE_BLOCK_HEIGHT + y, TILE_SIZE / 2, TILE_SIZE / 2);
        imageView.setViewport(viewPort);
        imageView.setTranslateX(TILE_SIZE / 2);

        return imageView;
    }

    private ImageView getUpLeftPart(Image currentImage, Tile currentTile, Tile upLeft, Tile up, Tile left) {
        ImageView imageView = new ImageView(currentImage);

        int x = 0;
        int y = 0;

        if ((equals(currentTile, left))
                && (equals(currentTile, up))) {
            if (equals(currentTile, upLeft)) {
                x = (int) TILE_SIZE;
                y = (int) (2 * TILE_SIZE);
            } else {
                x = (int) TILE_SIZE;
                y = 0;
            }
        } else if ((equals(currentTile, left))
                && (!equals(currentTile, up))) {
            x = (int) TILE_SIZE;
            y = (int) TILE_SIZE;
        } else if ((!equals(currentTile, left))
                && (equals(currentTile, up))) {
            x = 0;
            y = (int) (2 * TILE_SIZE);
        } else if ((!equals(currentTile, left))
                && (!equals(currentTile, up))) {
            x = 0;
            y = 0;
        }

        Rectangle2D viewPort = new Rectangle2D(currentTile.getX() * TILE_SIZE * TILE_BLOCK_WIDTH + x,
                currentTile.getY() * TILE_SIZE * TILE_BLOCK_HEIGHT + y, TILE_SIZE / 2, TILE_SIZE / 2);
        imageView.setViewport(viewPort);

        return imageView;
    }

    private boolean equals(Tile tile1, Tile tile2) {
        if (tile1 == null && tile2 == null) {
            return true;
        }
        if (tile1 != null && tile2 == null) {
            return false;
        }
        if (tile1 == null) {
            return false;
        }
        if (tile1.getX() != tile2.getX() ||
                tile1.getY() != tile2.getY() ||
                (!Objects.equals(tile1.getTileImage(), tile2.getTileImage()) ||
                        tile1.isComplex() != tile2.isComplex())) {
            return false;
        }
        return true;

    }

    private Tile[][] getNeighbours(Tile[][] matrix, int x, int y) {
        Tile[][] neighbours = new Tile[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                try {
                    neighbours[row][col] = matrix[y - 1 + row][x - 1 + col];
                } catch (ArrayIndexOutOfBoundsException edgeException) {
                    try {
                        neighbours[row][col] = matrix[y][x];
                    } catch (ArrayIndexOutOfBoundsException unkownException) {
                        neighbours[row][col] = null;
                    }
                }
            }
        }

        return neighbours;
    }
}
