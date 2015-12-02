package org.csproject.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.Constants;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.field.Field;
import org.csproject.model.field.StartPoint;
import org.csproject.model.field.TeleportPoint;
import org.csproject.model.field.Tile;
import org.csproject.service.ScreenFactory;
import org.csproject.util.Utilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public class TileEditor
        extends Application {
    // todo example tiles for deco and ground layer. add more!
    private static final String[] CS_COMPLEX_SELECTION = {CS_OUTSIDE_1, CS_INSIDE_1, CS_DUNGEON_1, CS_WORLD_2,
            CS_WORLD_3};
    private static final String[] CS_NORMAL_SELECTION = {CS_CHEST, CS_Door_1, CS_Door_2, CS_Door_3, CS_FLAME,
            CS_GATE_1, CS_HOUSE_TILES, CS_INSIDE_2, CS_OTHER_2, CS_OUTSIDE_2, CS_OUTSIDE_3, CS_SWITCH_1, CS_WORLD_1};

    private static ApplicationContext context;

    private ScreenFactory screenFactory = context.getBean(ScreenFactory.class);

    private boolean showDecoLayer;
    private boolean showPointsOfInterest;
    private boolean hasChunkSelected = false;

    private int rowNum;
    private int colNum;

    private String complexCS;
    private String normalCS;

    private Tile selectedTile;

    private ScrollPane scrollPane;
    private ScrollPane complexSelectionScroller;
    private ScrollPane normalSelectionScroller;

    private Tile[][] groundTiles;
    private Tile[][] decoTiles;

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
        this.rowNum = 20;
        this.colNum = 20;
        this.showDecoLayer = true;
        this.showPointsOfInterest = true;
        this.startPoints = new ArrayList<>();
        this.teleportPoints = new ArrayList<>();
        this.images = new HashMap<>();

        editorStage.setTitle("Field editor");
        Stage tileSelectionStage = new Stage();
        tileSelectionStage.setTitle("Tiles");

        editorStage.setScene(getEditorScene());
        tileSelectionStage.setScene(getTileSelectionScene());

        editorStage.show();
        tileSelectionStage.show();

        this.selectedTile = getEmptyTile();

        setup();
    }

    private Tile getEmptyTile() {
        return new Tile(0, 0, true, Constants.EDITOR_EMPTY_TILE);
    }

    private Scene getTileSelectionScene() {
        complexSelectionScroller = new ScrollPane();
        complexSelectionScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        complexSelectionScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        complexSelectionScroller.setPrefSize(400, 300);

        normalSelectionScroller = new ScrollPane();
        normalSelectionScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        normalSelectionScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        normalSelectionScroller.setPrefSize(400, 300);

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

        Button deleteButton = new Button("Delete tiles");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedTile = getEmptyTile();
            }
        });

        Label complexCSSelectionLabel = new Label("Complex: ");
        ChoiceBox<String> complexCSSelection = new ChoiceBox<>();
        complexCSSelection.getItems().addAll(CS_COMPLEX_SELECTION);
        complexCSSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String value, String newValue) {
                complexCS = newValue;
                setupTileSelectionPanel(complexCS, complexSelectionScroller, true);
            }
        });
        complexCSSelection.getSelectionModel().selectFirst();

        Label normalCSSelectionLabel = new Label("Normal: ");
        ChoiceBox<String> normalCSSelection = new ChoiceBox<>();
        normalCSSelection.getItems().addAll(CS_NORMAL_SELECTION);
        normalCSSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String value, String newValue) {
                normalCS = newValue;
                setupTileSelectionPanel(normalCS, normalSelectionScroller, false);
            }
        });
        normalCSSelection.getSelectionModel().selectFirst();

        Label chunkCSSelectionLabel = new Label("Chunks: ");
        Button trees = new Button("4x4 Trees");
        Button hole = new Button("2x2 Hole");
        Button waterHole = new Button("2x2 Water Hole");

        trees.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSelectedTile(new Tile(0, 14, false, "Outside3"));
                hasChunkSelected = true;
            }
        });
        hole.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSelectedTile(new Tile(10, 7, false, "Outside"));
                hasChunkSelected = true;
            }
        });
        waterHole.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSelectedTile(new Tile(14, 9, false, "Outside"));
                hasChunkSelected = true;
            }
        });

        vBox.getChildren().add(deleteButton);
        vBox.getChildren().add(navPointSelectionPanel);

        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(complexCSSelectionLabel);
        vBox.getChildren().add(complexCSSelection);
        vBox.getChildren().add(complexSelectionScroller);

        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(normalCSSelectionLabel);
        vBox.getChildren().add(normalCSSelection);
        vBox.getChildren().add(normalSelectionScroller);

        vBox.getChildren().add(chunkCSSelectionLabel);
        vBox.getChildren().add(trees);
        vBox.getChildren().add(hole);
        vBox.getChildren().add(waterHole);

        return new Scene(vBox);
    }

    private Scene getEditorScene() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getToolScene());

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(800, 600);

        borderPane.setCenter(scrollPane);
        return new Scene(borderPane);
    }

    private Pane getToolScene() {
        HBox hBox = new HBox();

        CheckBox showDecoLayerCB = new CheckBox("Show deco layer");
        showDecoLayerCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean value, Boolean newValue) {
                showDecoLayer = newValue;
                if(blockingTileCB != null)
                {
                    blockingTileCB.setSelected(showDecoLayer);
                }
                setup();
            }
        });
        showDecoLayerCB.setSelected(showDecoLayer);

        blockingTileCB = new CheckBox("Set as blocking");
        blockingTileCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean value, Boolean newValue) {
                if(selectedTile != null) {
                  selectedTile.setWalkable(!newValue);
                }
            }
        });
        blockingTileCB.setSelected(showDecoLayer);

        CheckBox showPointsOfInterestCB = new CheckBox("Show points of interest");
        showPointsOfInterestCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean value, Boolean newValue) {
                showPointsOfInterest = newValue;
                setup();
            }
        });
        showPointsOfInterestCB.setSelected(showPointsOfInterest);

        Label fieldNameLabel = new Label("Field name: ");
        final TextField fileNameField = new TextField("example");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Gson gson = context.getBean(Gson.class);
                String json = gson.toJson(createField());
                try {
                    Utilities.saveFile(fileNameField.getText(), json);
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
                        + fileNameField.getText() + Constants.JSON_POST_FIX);

                Field field = gson.fromJson(loaded, Field.class);
                complexCS = field.getGroundImage();
                normalCS = field.getDecoImage();
                groundTiles = field.getGroundTiles();
                decoTiles = field.getDecoTiles();
                startPoints = field.getStartPoints();
                teleportPoints = field.getTeleportPoints();
                colNum = (int) field.getWidth();
                rowNum = (int) field.getHeight();

                if (complexCS != null && !complexCS.isEmpty()) {
                    images.put(complexCS, new Image(CS_DIR + complexCS + CS_POST_FIX));
                }
                if (normalCS != null && !normalCS.isEmpty()) {
                    images.put(normalCS, new Image(CS_DIR + normalCS + CS_POST_FIX));
                }

                setup();
            }
        });

        Button newButton = new Button("New");
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage newDialog = new Stage();
                VBox vBox = new VBox();
                vBox.getChildren().add(new Label("Columns:"));
                final TextField colsTextField = new NumberTextField();
                vBox.getChildren().add(colsTextField);
                vBox.getChildren().add(new Label("Rows:"));
                final TextField rowsTextField = new NumberTextField();
                vBox.getChildren().add(rowsTextField);
                Button okButton = new Button("Ok");
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        rowNum = Integer.parseInt(rowsTextField.getText());
                        colNum = Integer.parseInt(colsTextField.getText());
                        setup();
                        newDialog.hide();
                    }
                });
                vBox.getChildren().add(okButton);

                newDialog.setScene(new Scene(vBox));
                newDialog.show();
            }
        });
        hBox.getChildren().add(newButton);

        hBox.getChildren().add(fieldNameLabel);
        hBox.getChildren().add(fileNameField);
        hBox.getChildren().add(saveButton);
        hBox.getChildren().add(loadButton);

        hBox.getChildren().add(showDecoLayerCB);
        hBox.getChildren().add(blockingTileCB);
        hBox.getChildren().add(showPointsOfInterestCB);

        return hBox;
    }

    private Field createField() {
        Field field = new Field();
        field.setGroundImage(complexCS);
        field.setDecoImage(normalCS);
        field.setGroundTiles(groundTiles);
        field.setDecoTiles(decoTiles);
        field.getStartPoints().addAll(startPoints);
        field.getTeleportPoints().addAll(teleportPoints);

        // todo other actors...

        return field;
    }

    private void setup() {
        if (complexCS == null || complexCS.isEmpty() || normalCS == null || normalCS.isEmpty()) {
            return;
        }

        setupEditorPanel();
    }

    private void setupEditorPanel() {
        final String groundImageUrl = CS_DIR + complexCS + CS_POST_FIX;
        final String decoImageUrl = CS_DIR + normalCS + CS_POST_FIX;

        if (groundTiles == null || groundTiles.length != rowNum || (groundTiles.length > 0 && groundTiles[0].length != colNum)) {
            groundTiles = newTileMatrix(0, 0, rowNum, colNum, complexCS, false);
        }
        if (decoTiles == null || decoTiles.length != rowNum || (decoTiles.length > 0 && decoTiles[0].length != colNum)) {
            decoTiles = newTileMatrix(0, 0, rowNum, colNum, EDITOR_EMPTY_TILE, false);
        }

        ScreenFactory.TileClickCallback tileClickCallback = new ScreenFactory.TileClickCallback() {
            @Override
            public void onClick(Tile tile, boolean controlDown, boolean shiftDown, int col, int row) {
                if(controlDown) {
                    setSelectedTile(tile);
                    hasChunkSelected = false;
                } else if (shiftDown) {
                    Tile[][] allTiles = showDecoLayer ? decoTiles : groundTiles;
                    for (int currentRow = 0; currentRow < rowNum; currentRow++) {
                        for (int currentCol = 0; currentCol < colNum; currentCol++) {
                            if (selectedTile != null) {
                                allTiles[currentRow][currentCol].setX(selectedTile.getX());
                                allTiles[currentRow][currentCol].setY(selectedTile.getY());
                                allTiles[currentRow][currentCol].setWalkable(selectedTile.isWalkable());
                                allTiles[currentRow][currentCol].setTileImage(selectedTile.getTileImage());
                                allTiles[currentRow][currentCol].setComplex(selectedTile.isComplex());
                            } else {
                                allTiles[currentRow][currentCol] = null;
                            }
                        }
                        setup();
                    }
                } else {
                    if(selectedTile != null && !hasChunkSelected)
                    {
                      tile.setX(selectedTile.getX());
                      tile.setY(selectedTile.getY());
                      tile.setWalkable(selectedTile.isWalkable());
                      tile.setTileImage(selectedTile.getTileImage());
                      tile.setComplex(selectedTile.isComplex());
                    }
                    else if(selectedTile != null && hasChunkSelected)
                    {
                        Tile[][] allTiles = showDecoLayer ? decoTiles : groundTiles;
                        int x = row;
                        int y = col;
                        if(selectedTile.getTileImage() == "Outside3"){
                            allTiles[x][y].setX(0);
                            allTiles[x][y].setY(14);
                            allTiles[x][y].setWalkable(false);
                            allTiles[x][y].setTileImage("Outside3");
                            allTiles[x][y].setComplex(false);

                            allTiles[x][y + 1].setX(1);
                            allTiles[x][y + 1].setY(14);
                            allTiles[x][y + 1].setWalkable(false);
                            allTiles[x][y + 1].setTileImage("Outside3");
                            allTiles[x][y + 1].setComplex(false);

                            allTiles[x][y + 2].setX(0);
                            allTiles[x][y + 2].setY(14);
                            allTiles[x][y + 2].setWalkable(false);
                            allTiles[x][y + 2].setTileImage("Outside3");
                            allTiles[x][y + 2].setComplex(false);

                            allTiles[x][y + 3].setX(1);
                            allTiles[x][y + 3].setY(14);
                            allTiles[x][y + 3].setWalkable(false);
                            allTiles[x][y + 3].setTileImage("Outside3");
                            allTiles[x][y + 3].setComplex(false);

                            allTiles[x + 1][y].setX(0);
                            allTiles[x + 1][y].setY(15);
                            allTiles[x + 1][y].setWalkable(false);
                            allTiles[x + 1][y].setTileImage("Outside3");
                            allTiles[x + 1][y].setComplex(false);

                            allTiles[x + 1][y + 1].setX(2);
                            allTiles[x + 1][y + 1].setY(15);
                            allTiles[x + 1][y + 1].setWalkable(false);
                            allTiles[x + 1][y + 1].setTileImage("Outside3");
                            allTiles[x + 1][y + 1].setComplex(false);

                            allTiles[x + 1][y + 2].setX(3);
                            allTiles[x + 1][y + 2].setY(15);
                            allTiles[x + 1][y + 2].setWalkable(false);
                            allTiles[x + 1][y + 2].setTileImage("Outside3");
                            allTiles[x + 1][y + 2].setComplex(false);

                            allTiles[x + 1][y + 3].setX(1);
                            allTiles[x + 1][y + 3].setY(15);
                            allTiles[x + 1][y + 3].setWalkable(false);
                            allTiles[x + 1][y + 3].setTileImage("Outside3");
                            allTiles[x + 1][y + 3].setComplex(false);

                            allTiles[x + 2][y].setX(0);
                            allTiles[x + 2][y].setY(14);
                            allTiles[x + 2][y].setWalkable(false);
                            allTiles[x + 2][y].setTileImage("Outside3");
                            allTiles[x + 2][y].setComplex(false);

                            allTiles[x + 2][y + 1].setX(2);
                            allTiles[x + 2][y + 1].setY(14);
                            allTiles[x + 2][y + 1].setWalkable(false);
                            allTiles[x + 2][y + 1].setTileImage("Outside3");
                            allTiles[x + 2][y + 1].setComplex(false);

                            allTiles[x + 2][y + 2].setX(3);
                            allTiles[x + 2][y + 2].setY(14);
                            allTiles[x + 2][y + 2].setWalkable(false);
                            allTiles[x + 2][y + 2].setTileImage("Outside3");
                            allTiles[x + 2][y + 2].setComplex(false);

                            allTiles[x + 2][y + 3].setX(1);
                            allTiles[x + 2][y + 3].setY(14);
                            allTiles[x + 2][y + 3].setWalkable(false);
                            allTiles[x + 2][y + 3].setTileImage("Outside3");
                            allTiles[x + 2][y + 3].setComplex(false);

                            allTiles[x + 3][y].setX(0);
                            allTiles[x + 3][y].setY(15);
                            allTiles[x + 3][y].setWalkable(false);
                            allTiles[x + 3][y].setTileImage("Outside3");
                            allTiles[x + 3][y].setComplex(false);

                            allTiles[x + 3][y].setX(0);
                            allTiles[x + 3][y].setY(15);
                            allTiles[x + 3][y].setWalkable(false);
                            allTiles[x + 3][y].setTileImage("Outside3");
                            allTiles[x + 3][y].setComplex(false);

                            allTiles[x + 3][y + 1].setX(1);
                            allTiles[x + 3][y + 1].setY(15);
                            allTiles[x + 3][y + 1].setWalkable(false);
                            allTiles[x + 3][y + 1].setTileImage("Outside3");
                            allTiles[x + 3][y + 1].setComplex(false);

                            allTiles[x + 3][y + 2].setX(0);
                            allTiles[x + 3][y + 2].setY(15);
                            allTiles[x + 3][y + 2].setWalkable(false);
                            allTiles[x + 3][y + 2].setTileImage("Outside3");
                            allTiles[x + 3][y + 2].setComplex(false);

                            allTiles[x + 3][y + 3].setX(1);
                            allTiles[x + 3][y + 3].setY(15);
                            allTiles[x + 3][y + 3].setWalkable(false);
                            allTiles[x + 3][y + 3].setTileImage("Outside3");
                            allTiles[x + 3][y + 3].setComplex(false);

                            setup();
                        } else if(selectedTile.getTileImage() == "Outside"){
                            if(selectedTile.getX() == 10 && selectedTile.getY() == 7){
                                allTiles[x][y].setX(10);
                                allTiles[x][y].setY(7);
                                allTiles[x][y].setWalkable(false);
                                allTiles[x][y].setTileImage("Outside");
                                allTiles[x][y].setComplex(false);

                                allTiles[x][y + 1].setX(11);
                                allTiles[x][y + 1].setY(7);
                                allTiles[x][y + 1].setWalkable(false);
                                allTiles[x][y + 1].setTileImage("Outside");
                                allTiles[x][y + 1].setComplex(false);

                                allTiles[x + 1][y].setX(10);
                                allTiles[x + 1][y].setY(8);
                                allTiles[x + 1][y].setWalkable(false);
                                allTiles[x + 1][y].setTileImage("Outside");
                                allTiles[x + 1][y].setComplex(false);

                                allTiles[x + 1][y + 1].setX(11);
                                allTiles[x + 1][y + 1].setY(8);
                                allTiles[x + 1][y + 1].setWalkable(false);
                                allTiles[x + 1][y + 1].setTileImage("Outside");
                                allTiles[x + 1][y + 1].setComplex(false);

                                setup();
                            } else if(selectedTile.getX() == 14 && selectedTile.getY() == 9){
                                allTiles[x][y].setX(14);
                                allTiles[x][y].setY(10);
                                allTiles[x][y].setWalkable(false);
                                allTiles[x][y].setTileImage("Outside");
                                allTiles[x][y].setComplex(false);

                                allTiles[x][y + 1].setX(15);
                                allTiles[x][y + 1].setY(10);
                                allTiles[x][y + 1].setWalkable(false);
                                allTiles[x][y + 1].setTileImage("Outside");
                                allTiles[x][y + 1].setComplex(false);

                                allTiles[x + 1][y].setX(14);
                                allTiles[x + 1][y].setY(11);
                                allTiles[x + 1][y].setWalkable(false);
                                allTiles[x + 1][y].setTileImage("Outside");
                                allTiles[x + 1][y].setComplex(false);

                                allTiles[x + 1][y + 1].setX(15);
                                allTiles[x + 1][y + 1].setY(11);
                                allTiles[x + 1][y + 1].setWalkable(false);
                                allTiles[x + 1][y + 1].setTileImage("Outside");
                                allTiles[x + 1][y + 1].setComplex(false);

                                setup();
                            } else {

                            }
                        } else {

                        }
                    }
                    else
                    {
                        NavigationPoint navigationPoint = navPointSelection.getSelectionModel().getSelectedItem().getNavigationPoint(col, row);
                        if(navigationPoint instanceof StartPoint)
                        {
                          startPoints.add((StartPoint) navigationPoint);
                        }
                        else if(navigationPoint instanceof TeleportPoint)
                        {
                          teleportPoints.add((TeleportPoint) navigationPoint);
                        }
                        setup();
                    }
                }
            }
        };
        Group all = new Group();

        Pane groundGroup = screenFactory.convert(groundTiles, groundImageUrl, Color.BLACK, tileClickCallback);
        Pane decoGroup = screenFactory.convert(decoTiles, decoImageUrl, null, showDecoLayer ? tileClickCallback : null);

        all.getChildren().add(groundGroup);
        if (showDecoLayer) {
            all.getChildren().add(decoGroup);
        }
        if(showPointsOfInterest) {
            if(images.get(EDITOR_START_TILE) == null) {
                images.put(EDITOR_START_TILE, new Image(CS_DIR + EDITOR_START_TILE + CS_POST_FIX));
            }
            if(images.get(EDITOR_TELEPORT_TILE) == null) {
              images.put(EDITOR_TELEPORT_TILE, new Image(CS_DIR + EDITOR_TELEPORT_TILE + CS_POST_FIX));
            }
            Group pointsOfInterestIcons = new Group();
            for(final StartPoint startPoint : startPoints)
            //noinspection Duplicates
            {
                ImageView startIconView = new ImageView(images.get(EDITOR_START_TILE));
                startIconView.setTranslateX(startPoint.getX() * TILE_SIZE);
                startIconView.setTranslateY(startPoint.getY() * TILE_SIZE);
                pointsOfInterestIcons.getChildren().add(startIconView);

                pointsOfInterestIcons.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                  @Override
                  public void handle(MouseEvent mouseEvent)
                  {
                    Prompt.getPrompt("Delete Start Point", "Are you sure, you want to delete this start point?", new Prompt.Callback()
                    {
                      @Override
                      public void onYes()
                      {
                        startPoints.remove(startPoint);
                        setup();
                      }
                    });
                  }
                });
            }
            for(final TeleportPoint teleportPoint : teleportPoints)
            //noinspection Duplicates
            {
                final ImageView teleportIconView = new ImageView(images.get(EDITOR_TELEPORT_TILE));
                teleportIconView.setTranslateX(teleportPoint.getX() * TILE_SIZE);
                teleportIconView.setTranslateY(teleportPoint.getY() * TILE_SIZE);
                pointsOfInterestIcons.getChildren().add(teleportIconView);

              pointsOfInterestIcons.setOnMouseClicked(new EventHandler<MouseEvent>()
              {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                  Prompt.getPrompt("Delete Teleporter", "Are you sure, you want to delete this teleporter?", new Prompt.Callback()
                  {
                    @Override
                    public void onYes()
                    {
                      teleportPoints.remove(teleportPoint);
                      setup();
                    }
                  });
                }
              });
            }
            all.getChildren().add(pointsOfInterestIcons);
        }

        scrollPane.setContent(all);
    }

    private void setupTileSelectionPanel(String csImage, ScrollPane selectionScroller, boolean complex) {
        String imageUrl = CS_DIR + csImage + CS_POST_FIX;

        Image image = images.get(csImage);
        if (image == null) {
            image = new Image(imageUrl);
            images.put(csImage, image);
        }
        int cols = (int) (image.getWidth() / TILE_SIZE);
        int rows = (int) (image.getHeight() / TILE_SIZE);

        Tile[][] tilesSelection = newTileMatrix(-1, -1, rows, cols, csImage, complex);

        final Pane selection = screenFactory.convert(tilesSelection, imageUrl, Color.BLACK, new ScreenFactory.TileClickCallback() {
            @Override
            public void onClick(Tile tile, boolean controlDown, boolean shiftDown, int col, int row) {
              setSelectedTile(tile);
                // todo 1: add a little icon or something here to show the selection (imageView.someFancyMethodThatWillMarkIt())
                hasChunkSelected = false;
            }
        });
        selectionScroller.setContent(selection);
    }

  private void setSelectedTile(Tile tile)
  {
    selectedTile = new Tile(tile.getX(), tile.getY(), !blockingTileCB.isSelected(), tile.getTileImage()); // select the clicked tile
    selectedTile.setComplex(tile.isComplex());
  }

  private Tile[][] newTileMatrix(int defaultX, int defaultY, int rowNum, int colNum, String tileImage,
                                   boolean complex) {
        Tile[][] tiles = new Tile
                [complex ? (int) (rowNum / TILE_BLOCK_HEIGHT) : rowNum]
                [complex ? (int) (colNum / TILE_BLOCK_WIDTH) : colNum];

        for (int row = 0; row < (complex ? rowNum / TILE_BLOCK_HEIGHT : rowNum); row++) {
            for (int col = 0; col < (complex ? colNum / TILE_BLOCK_WIDTH : colNum) ; col++) {
                int x = defaultX >= 0 ? defaultX : col;
                int y = defaultY >= 0 ? defaultY : row;
                Tile tile = new Tile(x, y, true);
                tile.setComplex(complex);
                if (tileImage != null) {
                    tile.setTileImage(tileImage);
                }
                tiles[row][col] = tile;
            }
        }

        return tiles;
    }

}
