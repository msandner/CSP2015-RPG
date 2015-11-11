package org.csproject.service;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.view.ControlledScreen;
import org.csproject.view.FieldScreen;
import org.csproject.view.MasterController;
import org.csproject.view.TownScreen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Brett on 9/21/2015.
 *
 * Holds the screens to be displayed
 */
@Component
public class ScreensController{

    private final static Logger LOG = Logger.getLogger(ScreensController.class);

    @Autowired
    private WorldService worldService;

    @Autowired
    private FieldScreen fieldScreen;

    @Autowired
    private TownService townService;

    @Autowired
    private TownScreen townScreen;

    private HashMap<String, Node> screens;

    private StackPane root;

    private String currentScreen;

    public ScreensController() {
        super();
        screens = new HashMap<>();
        root = new StackPane();
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public boolean loadScreen(String name, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
            Parent loadScreen = (Parent) loader.load();
            ControlledScreen controlledScreen = loader.getController();
            controlledScreen.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {

        if(screens.get(name) != null) { //screen loaded
            final DoubleProperty opacity = root.opacityProperty();

            //Is there is more than one screen
            if(!root.getChildren().isEmpty()){
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity,1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                //remove displayed screen
                                root.getChildren().remove(0);
                                //add new screen
                                fadeScreenIn(name, opacity);
                            }}, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                //no one else been displayed, then just show
                root.setOpacity(0.0);
                fadeScreenIn(name, opacity);
            }
            currentScreen = name;
            return true;
        } else {
            LOG.error("The screen hasn't been loaded!");
            return false;
        }
    }

    private void fadeScreenIn(String name, DoubleProperty opacity) {
        Node element = screens.get(name);
        root.getChildren().add(0, element);
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
        fadeIn.play();
    }

    public boolean unloadScreen (String name) {
        if (screens.remove(name) == null) {
            LOG.warn("Screen did not exist!");
            return false;
        } else {
            return true;
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setUpNewGame() {
        /*starts the field*/
        //fieldScreen.setScene(worldService.generateField("images/tiles/Outside.png", "images/tiles/Outside3.png"));
        /*starts a dungeon*/
        //fieldScreen.setScene(worldService.generateDungeon("images/tiles/Dungeon.png", "images/tiles/Outside3.png"));
        /*starts the static map*/
        fieldScreen.setScene(worldService.getField(Constants.WORLD_MAP));
        townScreen.setScene(townService.getTown(Constants.TOWN_1));
    }

    public void setUpLoadGame(String screen, double x, double y){
        if(screen.equals(MasterController.GAME_SCREEN)){
            fieldScreen.setScene(worldService.getField(Constants.WORLD_MAP), x, y);
            townScreen.setScene(townService.getTown(Constants.TOWN_1));
        } else if(screen.equals(MasterController.TOWN_SCREEN)){
            fieldScreen.setScene(worldService.getField(Constants.WORLD_MAP));
            townScreen.setScene(townService.getTown(Constants.TOWN_1), x, y);
        } else {
            //Dungeon?
        }
    }

    public FieldScreen getFieldScreen() {
        return fieldScreen;
    }

    public TownScreen getTownScreen() { return townScreen; }

    public PlayerActor getPlayerActor() {
        return worldService.getPlayerActor();
    }
}
