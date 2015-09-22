package org.csproject.view;/**
 * Created by Brett on 9/21/2015.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MasterController extends Application {

    public static String startMenuID = "startMenu";
    public static String startMenuFile = "StartMenu.fxml";

    ScreensController container;
    Group root;
    Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        container = new ScreensController();
        container.loadScreen(startMenuID, startMenuFile);
    }
}
