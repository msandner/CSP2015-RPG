package org.csproject.view;/**
 * Created by Brett on 9/21/2015.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.service.ScreensController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MasterController extends Application {

    public static final String START_MENU_ID = "startMenu";
    public static final String START_MENU_FILE = "StartMenu.fxml";
    public static final String NEW_GAME_ID = "newGame";
    public static final String NEW_GAME_FILE = "NewGame.fxml";
    public static final String GAME_SCREEN = "gameScreen";
    public static final String TOWN_SCREEN = "townScreen";

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {

        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        ScreensController screensController;

        screensController = context.getBean(ScreensController.class);

        screensController.loadScreen(START_MENU_ID, START_MENU_FILE);
        screensController.loadScreen(NEW_GAME_ID, NEW_GAME_FILE);
        screensController.setScreen(START_MENU_ID);

        Group root = new Group();
        root.getChildren().addAll(screensController.getRoot());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
