package org.csproject.service;

import java.io.IOException;
import java.util.HashMap;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.actors.PlayerParty;
import org.csproject.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Brett on 9/21/2015.
 * <p>
 * Holds the screens to be displayed
 */
@Component
public class ScreensController {

    private final static Logger LOG = Logger.getLogger(ScreensController.class);

    @Autowired
    private WorldService worldService;

    @Autowired
    private FieldScreen fieldScreen;

    private HashMap<String, Node> screens;

    private StackPane root;

    private String currentScreen;

    private BattleScreenController battleController;

    private PlayerParty party;

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
            if(name == MasterController.BATTLE_SCREEN_ID)
                battleController = loader.<BattleScreenController>getController();
            return true;
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {

        if (screens.get(name) != null) { //screen loaded
            final DoubleProperty opacity = root.opacityProperty();

            //Is there is more than one screen
            if (!root.getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                //remove displayed screen
                                root.getChildren().remove(0);
                                //add new screen
                                fadeScreenIn(name, opacity);
                            }
                        }, new KeyValue(opacity, 0.0)));
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

    public boolean unloadScreen(String name) {
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
        /*starts a overworld*/
//        fieldScreen.setScene(worldService.getField(Constants.WORLD_MAP_1));
        /*starts a dungeon*/
//        fieldScreen.setScene(worldService.generateDungeon(DungeonHelper.Type.TEST, 70, 70, 2, Constants.WORLD_MAP_1,
//                "dungeon_1_2", Constants.WORLD_MAP_1, "dungeon_1_1"));
        fieldScreen.setScene(worldService.getWorldMap(), Constants.TOWN_1);
    }

    public FieldScreen getFieldScreen() {
        return fieldScreen;
    }

    public PlayerActor getPlayerActor() {
        return worldService.getPlayerActor();
    }

    public PlayerParty getParty() {
        return party;
    }

    public void setParty(PlayerParty party) {
        this.party = party;
    }

    /* Used for controlling the battle via the FXML file and the controller class */
    public BattleScreenController getBattleController() {
        return battleController;
    }



}
