package org.csproject.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * Created by Brett on 9/21/2015.
 */
public class StartMenuController implements ControlledScreen {

    ScreensController screenController;

    public StartMenuController() {

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        screenController = screenParent;
    }

    public void startNewGame(ActionEvent actionEvent) {
        screenController.setScreen(MasterController.newGameID);
    }

    public void loadSavedGame(ActionEvent actionEvent) {
        //TODO: Something
    }

    public void exitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
