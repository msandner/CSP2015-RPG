package org.csproject.editor;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.Constants;
import org.csproject.model.bean.StartPoint;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.bean.TeleportPoint;
import org.csproject.model.bean.Tile;
import org.csproject.service.ScreenFactory;
import org.csproject.util.Utilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public class TileEditor
        extends Application {
    // todo example tiles for deco and ground layer. add more!
    private static final String[] CS_GROUND_SELECTION = {CS_OUTSIDE_1, CS_INSIDE_1};
    private static final String[] CS_DECO_SELECTION = {CS_OUTSIDE_2, CS_WORLD, CS_HOUSE_TILES};

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

    private CheckBox blockingTileCB;
    private ChoiceBox<NavPointSelection> navPointSelection;

    private Collection<StartPoint> startPoints;
    private Collection<TeleportPoint> teleportPoints;

    private Map<String, Image> images;

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
        this.startPoints = new ArrayList<>();
        this.teleportPoints = new ArrayList<>();
        this.images = new HashMap<>();

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
        this.selectedTile = getEmptyTile();
    }

    private Tile getEmptyTile() {
        return new Tile(0, 0, !blockingTileCB.isSelected(), Constants.EDITOR_EMPTY_TILE);
    }

    private Scene getTileSelectionScene() {
        tileSelectionScroller = new ScrollPane();
        tileSelectionScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        tileSelectionScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        tileSelectionScroller.setPrefSize(200, 400);

        VBox vBox = new VBox();

        final VBox navPointSelectionPanel = new VBox();

        navPointSelection = new ChoiceBox<>();
        navPointSelection.getItems().add(new StartPointSelection());
        navPointSelection.getItems().add(new TeleporterSelection());
        navPointSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NavPointSelection>() {
            @Override
            public void changed(ObservableValue<? extends NavPointSelection> observable, NavPointSelection oldValue, NavPointSelection newValue) {
                if (navPointSelectionPanel.getChildren().size() > 1) {
                    navPointSelectionPanel.getChildren().remove(1); // remove old preferences panel
                }
                navPointSelectionPanel.getChildren().add(newValue.getPreferencesPanel());

                selectedTile = null;
            }
        });

        navPointSelectionPanel.getChildren().add(navPointSelection);

        Button deleteButtopn = new Button("Delte tiles");
        deleteButtopn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedTile = getEmptyTile();
            }
        });
        vBox.getChildren().add(deleteButtopn);

        vBox.getChildren().add(navPointSelectionPanel);
        vBox.getChildren().add(tileSelectionScroller);

        return new Scene(vBox);
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
                images.put(groundCS, new Image(CS_DIR + groundCS + CS_POST_FIX));
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
                images.put(decoCS, new Image(CS_DIR + decoCS + CS_POST_FIX));
                setup();
            }
        });
        decoCSSelection.getSelectionModel().selectFirst();

        CheckBox showDecoLayerCB = new CheckBox("Show deco layer");
        blockingTileCB = new CheckBox("Set as blocking");
        showDecoLayerCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean value, Boolean newValue) {
                showDecoLayer = newValue;
                blockingTileCB.setSelected(showDecoLayer);
                setup();
            }
        });
        showDecoLayerCB.setSelected(showDecoLayer);

        blockingTileCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean value, Boolean newValue) {
                selectedTile.setWalkable(!newValue);
            }
        });
        blockingTileCB.setSelected(showDecoLayer);

        Label fielNameLabel = new Label("Field name: ");
        final TextField fielNameField = new TextField("example");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Gson gson = context.getBean(Gson.class);
                String json = gson.toJson(createField());
                try {
                    Utilities.saveFile(fielNameField.getText(), json);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Button loadButton = new Button("Load");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Gson gson = context.getBean(Gson.class);
                String loaded = Utilities.getFile("C:" + File.separator + "fields" + File.separator
                        + fielNameField.getText() + Constants.JSON_POST_FIX);

                Field field = gson.fromJson(loaded, Field.class);
                groundCS = field.getGroundImage();
                decoCS = field.getDecoImage();
                groundTiles = field.getGroundTiles();
                decoTiles = field.getDecoTiles();
                startPoints = field.getStartPoints();
                teleportPoints = field.getTeleportPoints();

                images.put(groundCS, new Image(CS_DIR + groundCS + CS_POST_FIX));
                images.put(decoCS, new Image(CS_DIR + decoCS + CS_POST_FIX));

                setup();
            }
        });

        vBox.getChildren().add(fielNameLabel);
        vBox.getChildren().add(fielNameField);
        vBox.getChildren().add(saveButton);
        vBox.getChildren().add(loadButton);

        vBox.getChildren().add(groundCSSelectionLabel);
        vBox.getChildren().add(groundCSSelection);

        vBox.getChildren().add(decoCSSelectionLabel);
        vBox.getChildren().add(decoCSSelection);

        vBox.getChildren().add(showDecoLayerCB);
        vBox.getChildren().add(blockingTileCB);

        vBox.setPrefSize(200, 500);
        return new Scene(vBox);
    }

    private Field createField() {
        Field field = new Field();
        field.setGroundImage(groundCS);
        field.setDecoImage(decoCS);
        field.setGroundTiles(groundTiles);
        field.setDecoTiles(decoTiles);
        field.getStartPoints().addAll(startPoints);
        field.getTeleportPoints().addAll(teleportPoints);

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
        final String groundImageUrl = CS_DIR + groundCS + CS_POST_FIX;
        final String decoImageUrl = CS_DIR + decoCS + CS_POST_FIX;

        if (groundTiles == null || groundTiles.length != rowNum || (groundTiles.length > 0 && groundTiles[0].length != colNum)) {
            groundTiles = newTileMatrix(0, 0, rowNum, colNum, null);
        }
        if (decoTiles == null || decoTiles.length != rowNum || (decoTiles.length > 0 && decoTiles[0].length != colNum)) {
            decoTiles = newTileMatrix(0, 0, rowNum, colNum, EDITOR_EMPTY_TILE);
        }

        ScreenFactory.TileClickCallback tileClickCallback = new ScreenFactory.TileClickCallback() {
            @Override
            public void onClick(Tile tile, ImageView tileImageView) {
                if(selectedTile != null) {
                    tile.setX(selectedTile.getX());
                    tile.setY(selectedTile.getY());
                    tile.setWalkable(selectedTile.isWalkable());
                    tile.setTileImage(selectedTile.getTileImage());
                    Rectangle2D rectangle2D = new Rectangle2D(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    tileImageView.setViewport(rectangle2D);
                    if(selectedTile.getTileImage() == null){
                        tileImageView.setImage(showDecoLayer ? images.get(decoCS) : images.get(groundCS));
                    }
                    else{
                        Image image = images.get(selectedTile.getTileImage());
                        if (image == null) {
                            image = new Image(CS_DIR + selectedTile.getTileImage() + CS_POST_FIX);
                            images.put(selectedTile.getTileImage(), image);
                        }
                        tileImageView.setImage(image);
                    }

                    // todo neighbours
                }
                else
                {
                    NavigationPoint navigationPoint = navPointSelection.getSelectionModel().getSelectedItem()
                            .getNavigationPoint((int)(tileImageView.getTranslateX()/TILE_SIZE), (int)(tileImageView.getTranslateY()/TILE_SIZE));
                    if(navigationPoint instanceof StartPoint){
                        startPoints.add((StartPoint) navigationPoint);
                    }
                    else if (navigationPoint instanceof TeleportPoint) {
                        teleportPoints.add((TeleportPoint) navigationPoint);
                    }
                    // todo add some graphic for this nav point on editor
                }
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

        tilesSelection = newTileMatrix(-1, -1, rows, cols, null);

        final Group selection = screenFactory.convert(tilesSelection, imageUrl, new ScreenFactory.TileClickCallback() {
            @Override
            public void onClick(Tile tile, ImageView tileImageView) {
                selectedTile = tile; // select the clicked tile
                selectedTile.setWalkable(!blockingTileCB.isSelected());
                // todo 1: add a little icon or something here to show the selection (imageView.someFancyMethodThatWillMarkIt())
                // todo 3: add some way to mark half tiles (auf deutsch: eine 32x32 tile zwischen zwei tiles/siehe tilesets!)
            }
        });
        tileSelectionScroller.setContent(selection);
    }

    private Tile[][] newTileMatrix(int defaultX, int defaultY, int rowNum, int colNum, String defaultImage) {
        Tile[][] tiles = new Tile[rowNum][colNum];

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                int x = defaultX >= 0 ? defaultX : col;
                int y = defaultY >= 0 ? defaultY : row;
                Tile tile = new Tile(x, y, true);
                if (defaultImage != null) {
                    tile.setTileImage(defaultImage);
                }
                tiles[row][col] = tile;
            }
        }
        return tiles;
    }
}
