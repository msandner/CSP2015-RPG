package org.csproject.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.actors.PlayerParty;
import org.csproject.service.BattleFactory;
import org.csproject.service.ScreensController;

/**
 * Created by Brett, Maike Keune on 9/22/2015.
 */
public class NewGameController implements ControlledScreen {

    public TextField char1Name, char2Name, char3Name;
    public ComboBox char1Class, char2Class, char3Class;
    public Button confirmButton;

    private ScreensController screenController;

    private PlayerParty party;

    @Override
    public void setScreenParent(ScreensController screenController) {
        this.screenController = screenController;
    }

    public void confirmCharacters(ActionEvent actionEvent) {
        if(char1Class.getValue() != null && char2Class.getValue() != null && char3Class.getValue() != null
                && !char1Name.getText().isEmpty() && !char2Name.getText().isEmpty() && !char3Name.getText().isEmpty()) {

        PlayerActor char1 = new PlayerActor(char1Name.getText(), typeToConstant((String) char1Class.getValue()), 1, 25, 1, 1);
        PlayerActor char2 = new PlayerActor(char2Name.getText(), typeToConstant((String) char2Class.getValue()), 1, 25, 1, 1);
        PlayerActor char3 = new PlayerActor(char3Name.getText(), typeToConstant((String) char3Class.getValue()), 1, 25, 1, 1);

        party = new PlayerParty(char1, char2, char3, 0);

        givePlayerParty();

        //Don't let the player make multiple games!
        confirmButton.setDisable(true);

        // display new game on field
        screenController.setUpNewGame();

        // add field as game screen
        screenController.addScreen(MasterController.GAME_SCREEN, screenController.getFieldScreen());

        // switch screen to game screen
        screenController.setScreen(MasterController.GAME_SCREEN);
       }
    }

    public void cancelNewGame(ActionEvent actionEvent) {
        screenController.setScreen(MasterController.START_MENU_ID);
    }

    public PlayerParty getPParty() {
        return party;
    }

    public String typeToConstant(String type) {
        String classtype = "no type";
        switch(type) {
            case "Mage":
                classtype = Constants.CLASS_MAGE;
                break;
            case "Knight":
                classtype = Constants.CLASS_KNIGHT;
                break;
            case "Thief":
                classtype = Constants.CLASS_THIEF;
                break;
        }
        return classtype;
    }

    private void givePlayerParty(){
        screenController.setParty(getPParty());
    }

}