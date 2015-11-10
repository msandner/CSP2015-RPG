package org.csproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.csproject.service.ScreensController;

/**
 * Created by Brett on 11/5/2015.
 */
public class GameMenuController implements ControlledScreen{

    public Button partyButton, itemButton, equipmentButton, magicButton, questButton, saveButton, loadButton;
    public Label moneyLabel, timeLabel;
    public Font x1, x2;
    public Pane partyPane;
    public ImageView char1FaceImage, char2FaceImage, char3FaceImage;
    public Label char1Name, char2Name, char3Name;
    public Label char1Level, char2Level, char3Level;
    public Label char1HPString, char2HPString, char3HPString;
    public ProgressBar char1HPBar, char2HPBar, char3HPBar;
    public Label char1MPString, char2MPString, char3MPString;
    public ProgressBar char1MPBar, char2MPBar, char3MPBar;
    public Insets x3, x4, x5;
    public Label char1EXPString, char2EXPString, char3EXPString;
    public ProgressBar char1EXPBar, char2EXPBar, char3EXPBar;
    private ScreensController screenController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        screenController = screenParent;
    }

    /* Set what pane the player wants to see (based on button selected) */
    public void setPane(Pane pane) {
        allPanesInvisible();
        pane.setVisible(true);
    }

    public void updateValues() {
        
    }

    private void allPanesInvisible() {
        partyPane.setVisible(false);
    }
}
