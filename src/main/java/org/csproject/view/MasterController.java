package org.csproject.view;/**
 * Created by Brett on 9/21/2015.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.Constants;
import org.csproject.service.ScreensController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MasterController extends Application {

    public static final String START_MENU_ID = "startMenu";
    public static final String START_MENU_FILE = "StartMenu.fxml";
    public static final String NEW_GAME_ID = "newGame";
    public static final String NEW_GAME_FILE = "NewGame.fxml";
    public static final String GAME_SCREEN = "gameScreen";
    public static final String TOWN_SCREEN = "townScreen";
    public static final String BATTLE_SCREEN_ID = "battleScreen";
    public static final String BATTLE_SCREEN_FILE = "BattleScreen.fxml";

    //Switch these two statements to start the game faster/slower
    public static final boolean fastStart = true;
//    public static final boolean fastStart = false;

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {

        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setWidth(Constants.SCREEN_WIDTH);
        primaryStage.setHeight(Constants.SCREEN_HEIGHT);

        ScreensController screensController;

        screensController = context.getBean(ScreensController.class);

        if (!fastStart) {
            screensController.loadScreen(START_MENU_ID, START_MENU_FILE);
            screensController.loadScreen(NEW_GAME_ID, NEW_GAME_FILE);
            screensController.setScreen(START_MENU_ID);


            Group root = new Group();
            root.getChildren().addAll(screensController.getRoot());
            primaryStage.setScene(new Scene(root));
        } else {
            /*Skip the beginning scenes and start the game right away.
             * Change the values in the ScreensController class (setUpNewGame() function)
             * to view different scenes like the static map, dungeon, forest, etc
             */
            screensController.setUpNewGame();
            screensController.addScreen(GAME_SCREEN, screensController.getFieldScreen());
            screensController.setScreen(GAME_SCREEN);
//            screensController.loadScreen(BATTLE_SCREEN_ID, BATTLE_SCREEN_FILE);
//            screensController.setScreen(BATTLE_SCREEN_ID);
            Group root = new Group();
            root.getChildren().addAll(screensController.getRoot());
            primaryStage.setScene(new Scene(root));
        }

        primaryStage.show();
    }
}
