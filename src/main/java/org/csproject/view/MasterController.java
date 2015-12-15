package org.csproject.view;/**
 * Created by Brett on 9/21/2015.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.actors.PlayerParty;
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
    public static final String SHOP_SCREEN_ID = "shopScreen";
    public static final String SHOP_SCREEN_FILE = "ShopScreen.fxml";

    /**
     * Switch these two statements to start the game faster/slower
     */
//  public static final boolean fastStart = true;
    public static final boolean fastStart = false;

    public static ScreensController screensController;

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {

        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setWidth(Constants.SCREEN_WIDTH);
        primaryStage.setHeight(Constants.SCREEN_HEIGHT);

        screensController = context.getBean(ScreensController.class);

        if (!fastStart) {
            screensController.loadScreen(START_MENU_ID, START_MENU_FILE);
            screensController.loadScreen(NEW_GAME_ID, NEW_GAME_FILE);
            screensController.loadScreen(BATTLE_SCREEN_ID, BATTLE_SCREEN_FILE);
            screensController.loadScreen(SHOP_SCREEN_ID, SHOP_SCREEN_FILE);
            screensController.setScreen(START_MENU_ID);

            Group root = new Group();
            root.getChildren().addAll(screensController.getRoot());
            primaryStage.setScene(new Scene(root));

        } else {
            /** Skip the beginning scenes and start the game right away.
             * Change the values in the ScreensController class (setUpNewGame() function)
             * to view different scenes like the static map, dungeon, forest, etc
             */

            /**
             * creating a static player party when using faststar, because you can't set a player party in the
             * new game menu
             * leveled them up to 13 for testing purposes
             */
            PlayerActor char1 = new PlayerActor("Bladerunner", Constants.CLASS_THIEF, 25);
            PlayerActor char2 = new PlayerActor("Tim", Constants.CLASS_MAGE, 25);
            PlayerActor char3 = new PlayerActor("Knightrider", Constants.CLASS_KNIGHT, 25);
            int x = 1;
            while(x < 13) {
                char1.levelUp();
                char2.levelUp();
                char3.levelUp();
                x++;
            }

            //char1.setCurrentHp(50);
            //char3.setCurrentHp(50);

            PlayerParty party = new PlayerParty(char1, char2, char3, 0);

            screensController.getFieldScreen().setStartPlayer(party.getPlayer(0));

            screensController.setUpNewGame();
            screensController.addScreen(GAME_SCREEN, screensController.getFieldScreen());
            screensController.setScreen(GAME_SCREEN);
            screensController.loadScreen(BATTLE_SCREEN_ID, BATTLE_SCREEN_FILE);

            Group root = new Group();
            root.getChildren().addAll(screensController.getRoot());
            primaryStage.setScene(new Scene(root));

            screensController.setParty(party);
        }
        primaryStage.show();
    }

    public static void setScreen(String screenName) {
        screensController.setScreen(screenName);
    }

    public static BattleScreenController getBattleController() {
        return screensController.getBattleController();
    }

    public static ShopScreenController getShopController() {
        return screensController.getShopController();
    }

    public static ScreensController getScreensController() {
        return screensController;
    }
}
