package org.csproject.view;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.csproject.service.ScreensController;

/**
 * Created by Brett, Maike Keune on 9/22/2015.
 */
public class NewGameController implements ControlledScreen {

    public TextField char1Name, char2Name, char3Name;
    public ComboBox char1Class, char2Class, char3Class;

    private ScreensController screenController;

    @Override
    public void setScreenParent(ScreensController screenController) {
        this.screenController = screenController;
    }

    public void confirmCharacters(ActionEvent actionEvent) {
        //Currently commented out for testing

//        if(char1Class.getValue() != null && char2Class.getValue() != null && char3Class.getValue() != null &&
//                !char1Name.getText().isEmpty() && !char2Name.getText().isEmpty() && !char3Name.getText().isEmpty()) {
            // display new game on field
            screenController.setUpNewGame();

            // add field as game screen
            screenController.addScreen(MasterController.GAME_SCREEN, screenController.getFieldScreen());

            //add town as town screen
            screenController.addScreen(MasterController.TOWN_SCREEN, screenController.getTownScreen());

            // switch screen to game screen
            screenController.setScreen(MasterController.GAME_SCREEN);
//        }
    }

    public void cancelNewGame(ActionEvent actionEvent) {
        screenController.setScreen(MasterController.START_MENU_ID);
    }
}