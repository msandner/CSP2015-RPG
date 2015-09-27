package org.csproject.view;/**
 * Created by Brett on 9/21/2015.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MasterController extends Application {

    public static final String startMenuID = "startMenu";
    public static final String startMenuFile = "StartMenu.fxml";
    public static final String newGameID = "newGame";
    public static final String newGameFile = "NewGame.fxml";

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
        container.loadScreen(newGameID, newGameFile);

        container.setScreen(MasterController.startMenuID);

        root = new Group();
        root.getChildren().addAll(container);
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
