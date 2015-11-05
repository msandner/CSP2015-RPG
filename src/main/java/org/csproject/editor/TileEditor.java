/*
 * Copyright (c) 2015 conLeos GmbH. All Rights reserved.
 * <p/>
 * This software is the confidential intellectual property of conLeos GmbH; it is copyrighted and licensed.
 */
package org.csproject.editor;

import java.io.FileNotFoundException;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.Tile;
import org.csproject.service.ScreenFactory;
import org.csproject.util.Utilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.csproject.model.Constants.*;

public class TileEditor
        extends Application {
    // todo example tiles for deco and ground layer. add more!
    private static final String[] CS_GROUND_SELECTION = {CS_OUTSIDE_1, CS_INSIDE_1};
    private static final String[] CS_DECO_SELECTION = {CS_OUTSIDE_2};

    private static ApplicationContext context;

    private ScreenFactory screenFactory = context.getBean(ScreenFactory.class);

    private boolean showDecoLayer;

    private int rowNum;
    private int colNum;

    private String groundCS;
    private String decoCS;

    private Tile selectedTile;

    private ScrollPane scrollPane;
    private ScrollPane tileSelectionScroller;

    private Tile[][] groundTiles;
    private Tile[][] decoTiles;
    private Tile[][] tilesSelection;

    public static void main(String[] args) throws FileNotFoundException {
        // Start the spring application context
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        launch(args);
    }

    @Override
    public void start(Stage editorStage)
            throws Exception {
        this.rowNum = 50;
        this.colNum = 50;
        this.showDecoLayer = true;
        this.selectedTile = new Tile(0, 4, true); // todo

        editorStage.setTitle("Field editor");
        Stage toolStage = new Stage();
        toolStage.setTitle("Preferences");
        Stage tileSelectionStage = new Stage();
        tileSelectionStage.setTitle("Tiles");

        tileSelectionStage.setScene(getTileSelectionScene());
        editorStage.setScene(getEditorScene());
        toolStage.setScene(getToolScene());

        editorStage.show();
        toolStage.show();
        tileSelectionStage.show();

        setup();
    }

    private Scene getTileSelectionScene() {
        tileSelectionScroller = new ScrollPane();
        tileSelectionScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        tileSelectionScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        tileSelectionScroller.setPrefSize(200, 400);
        return new Scene(tileSelectionScroller);
    }

    private Scene getEditorScene() {
        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(500, 500);
        return new Scene(scrollPane);
    }

    private Scene getToolScene() {
        VBox vBox = new VBox();

        Label groundCSSelectionLabel = new Label("Ground: ");
        ChoiceBox<String> groundCSSelection = new ChoiceBox<>();
        groundCSSelection.getItems().addAll(CS_GROUND_SELECTION);
        groundCSSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String value, String newValue) {
                groundCS = newValue;
                setup();
            }
        });
        groundCSSelection.getSelectionModel().selectFirst();

        Label decoCSSelectionLabel = new Label("Deco: ");
        ChoiceBox<String> decoCSSelection = new ChoiceBox<>();
        decoCSSelection.getItems().addAll(CS_DECO_SELECTION);
        decoCSSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String value, String newValue) {
                decoCS = newValue;
                setup();
            }
        });
        decoCSSelection.getSelectionModel().selectFirst();

        CheckBox showDecoLayerCB = new CheckBox("Show deco layer");
        showDecoLayerCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean value, Boolean newValue) {
                showDecoLayer = newValue;
                setup();
            }
        });
        showDecoLayerCB.setSelected(showDecoLayer);

        Label fielNameLabel = new Label("Field name: ");
        final TextField fielNameField = new TextField("example");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Gson gson = context.getBean(Gson.class);
                String json = gson.toJson(getField());
                try {
                    Utilities.saveFile(fielNameField.getText(), json);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        vBox.getChildren().add(fielNameLabel);
        vBox.getChildren().add(fielNameField);
        vBox.getChildren().add(saveButton);

        vBox.getChildren().add(groundCSSelectionLabel);
        vBox.getChildren().add(groundCSSelection);

        vBox.getChildren().add(decoCSSelectionLabel);
        vBox.getChildren().add(decoCSSelection);

        vBox.getChildren().add(showDecoLayerCB);

        vBox.setPrefSize(200, 500);
        return new Scene(vBox);
    }

    private Field getField() {
        Field field = new Field();
        field.setGroundImage(groundCS);
        field.setDecoImage(decoCS);
        field.setGroundTiles(groundTiles);
        field.setDecoTiles(decoTiles);

        // todo other actors...

        return field;
    }

    private void setup() {
        if (groundCS == null || groundCS.isEmpty() || decoCS == null || decoCS.isEmpty()) {
            return;
        }

        setupEditorPanel();
        setupTileSelectionPanel();
    }

    private void setupEditorPanel() {
        String groundImageUrl = CS_DIR + groundCS + CS_POST_FIX;
        String decoImageUrl = CS_DIR + decoCS + CS_POST_FIX;

        if (groundTiles == null || groundTiles.length != rowNum || (groundTiles.length > 0 && groundTiles[0].length != colNum)) {
            groundTiles = newTileMatrix(0, 0, rowNum, colNum);
        }
        if (decoTiles == null || decoTiles.length != rowNum || (decoTiles.length > 0 && decoTiles[0].length != colNum)) {
            decoTiles = newTileMatrix(0, 1, rowNum, colNum);
        }

        ScreenFactory.TileClickCallback tileClickCallback = new ScreenFactory.TileClickCallback() {
            @Override
            public void onClick(Tile tile, ImageView tileImageView) {
                tile.setX(selectedTile.getX());
                tile.setY(selectedTile.getY());
                Rectangle2D rectangle2D = new Rectangle2D(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                tileImageView.setViewport(rectangle2D);
                // todo neighbours
            }
        };
        Group all = new Group();

        Group groundGroup = screenFactory.convert(groundTiles, groundImageUrl, tileClickCallback);
        Group decoGroup = screenFactory.convert(decoTiles, decoImageUrl, tileClickCallback);

        all.getChildren().add(groundGroup);
        if (showDecoLayer) {
            all.getChildren().add(decoGroup);
        }

        scrollPane.setContent(all);
    }

    private void setupTileSelectionPanel() {
        String imageUrl = showDecoLayer ? CS_DIR + decoCS + CS_POST_FIX : CS_DIR + groundCS + CS_POST_FIX;

        Image image = new Image(imageUrl);
        int cols = (int) (image.getWidth() / TILE_SIZE);
        int rows = (int) (image.getHeight() / TILE_SIZE);

        tilesSelection = newTileMatrix(-1, -1, rows, cols);

        final Group selection = screenFactory.convert(tilesSelection, imageUrl, new ScreenFactory.TileClickCallback() {
            @Override
            public void onClick(Tile tile, ImageView tileImageView) {
                selectedTile = tile; // select the clicked tile
                // todo 1: add a little icon or something here to show the selection (imageView.someFancyMethodThatWillMarkIt())
                // todo 2: add some way, to set tile walkable (tile.setWalkable(true/false))
                // todo 3: add some way to mark half tiles (auf deutsch: eine 32x32 tile zwischen zwei tiles/siehe tilesets!)
            }
        });
        tileSelectionScroller.setContent(selection);
    }

    private Tile[][] newTileMatrix(int defaultX, int defaultY, int rowNum, int colNum) {
        Tile[][] tiles = new Tile[rowNum][colNum];

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                int x = defaultX >= 0 ? defaultX : col;
                int y = defaultY >= 0 ? defaultY : row;
                tiles[row][col] = new Tile(x, y, true);
            }
        }
        return tiles;
    }
}
