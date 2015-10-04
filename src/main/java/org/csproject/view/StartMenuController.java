package org.csproject.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import org.csproject.service.ScreensController;

/**
 * Created by Brett on 9/21/2015.
 */
public class StartMenuController implements ControlledScreen {

    private ScreensController screenController;

    public StartMenuController() {

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        screenController = screenParent;
    }

    public void startNewGame(ActionEvent actionEvent) {
        screenController.setScreen(MasterController.NEW_GAME_ID);
    }

    public void loadSavedGame(ActionEvent actionEvent) {
        //TODO: Something
    }

    public void exitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
