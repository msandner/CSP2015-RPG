package org.csproject.view;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Created by Brett on 9/22/2015.
 */
public class NewGameController implements ControlledScreen {

    public TextField char1Name, char2Name, char3Name;
    public ComboBox char1Class, char2Class, char3Class;

    ScreensController screenController;

    public NewGameController() {

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        screenController = screenParent;
    }

    public void confirmCharacters(ActionEvent actionEvent) {
        //TODO Something
    }

    public void cancelNewGame(ActionEvent actionEvent) {
        screenController.setScreen(MasterController.startMenuID);
    }
}
