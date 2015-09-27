package org.csproject.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Brett on 9/21/2015.
 *
 * Holds the screens to be displayed
 */
public class ScreensController extends StackPane {

    private HashMap<String, Node> screens;

    public ScreensController() {
        super();
        screens = new HashMap<>();
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
            ControlledScreen screenController = loader.getController();
            screenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {

        if(screens.get(name) != null) { //screen loaded
            final DoubleProperty opacity = opacityProperty();

            //Is there is more than one screen
            if(!getChildren().isEmpty()){
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity,1.0)),
                        new KeyFrame(new Duration(1000),

                                new EventHandler() {

                                    @Override
                                    public void handle(Event t) {
                                        //remove displayed screen
                                        getChildren().remove(0);
                                        //add new screen
                                        getChildren().add(0, screens.get(name));
                                        Timeline fadeIn = new Timeline(
                                                new KeyFrame(Duration.ZERO,
                                                        new KeyValue(opacity, 0.0)),
                                                new KeyFrame(new Duration(800),
                                                        new KeyValue(opacity, 1.0)));
                                        fadeIn.play();
                                    }
                                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                //no one else been displayed, then just show
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500),
                                new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("The screen hasn't been loaded!\n");
            return false;
        }
    }

    public boolean unloadScreen (String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen did not exist!");
            return false;
        } else {
            return true;
        }
    }
}
