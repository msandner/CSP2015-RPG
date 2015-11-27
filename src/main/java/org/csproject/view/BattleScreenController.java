package org.csproject.view;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.csproject.model.Constants;
import org.csproject.model.actors.Monster;
import org.csproject.model.actors.PlayerActor;
import org.csproject.service.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

import java.util.List;

/**
 * Created by Brett on 11/22/2015.
 */
public class BattleScreenController implements ControlledScreen, Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button attackButton;

    @FXML
    private Button myButton;

    @FXML
    private ImageView battleScreenBGBottom;

    @FXML
    private ImageView battleScreenBGTop;

    @FXML
    private ImageView charImage1;

    @FXML
    private ImageView charImage2;

    @FXML
    private ImageView charImage3;

    @FXML
    private HBox chooseBox;

    @FXML
    private VBox commandBox;

    @FXML
    private Button enemyButton1;

    @FXML
    private Button enemyButton2;

    @FXML
    private Button enemyButton3;

    @FXML
    private Button enemyButton4;

    @FXML
    private Button enemyButton5;

    @FXML
    private Button enemyButton6;

    @FXML
    private ImageView enemyImage1;

    @FXML
    private ImageView enemyImage2;

    @FXML
    private ImageView enemyImage3;

    @FXML
    private ImageView enemyImage4;

    @FXML
    private ImageView enemyImage5;

    @FXML
    private ImageView enemyImage6;

    @FXML
    private Label enemyLabel1;

    @FXML
    private Label enemyLabel2;

    @FXML
    private Label enemyLabel3;

    @FXML
    private Label enemyLabel4;

    @FXML
    private Label enemyLabel5;

    @FXML
    private Label enemyLabel6;

    @FXML
    private HBox enemyNames;

    @FXML
    private Button itemsButton;

    @FXML
    private Button magicButton;

    @FXML
    private ProgressBar player1HPBar;

    @FXML
    private Label player1HPText;

    @FXML
    private ProgressBar player1MPBar;

    @FXML
    private Label player1MPText;

    @FXML
    private Label player1Name;

    @FXML
    private ProgressBar player2HPBar;

    @FXML
    private Label player2HPText;

    @FXML
    private ProgressBar player2MPBar;

    @FXML
    private Label player2MPText;

    @FXML
    private Label player2Name;

    @FXML
    private ProgressBar player3HPBar;

    @FXML
    private Label player3HPText;

    @FXML
    private ProgressBar player3MPBar;

    @FXML
    private Label player3MPText;

    @FXML
    private Label player3Name;

    @FXML
    private Button playerButton1;

    @FXML
    private Button playerButton2;

    @FXML
    private Button playerButton3;

    @FXML
    private VBox playerHP;

    @FXML
    private VBox playerNames;

    @FXML
    private Button runButton;

    int char1HP, char1MaxHP, char2HP, char2MaxHP, char3HP, char3MaxHP;
    int char1MP, char1MaxMP, char2MP, char2MaxMP, char3MP, char3MaxMP;
    ImageView[] enemyImages;
    TranslateTransition moveChar, reverseMoveChar;

    ScreensController screenController;

    public BattleScreenController() {
        enemyImages = new ImageView[6];
        moveChar = new TranslateTransition(Duration.seconds(.25));
        moveChar.setFromX(0);
        moveChar.setToX(-25);

        reverseMoveChar = new TranslateTransition(Duration.seconds(.25));
        moveChar.setFromX(0);
        moveChar.setToX(25);
    }

    public void startNewBattle() {
        startNewBattle(null, null);
    }

    public void startNewBattle(PlayerActor[] players, List enemyActors) {
        battleScreenBGBottom.setImage(new Image("images/battleimages/Grassland.png"));
        battleScreenBGTop.setImage(new Image("images/battleimages/GrasslandTop.png"));
        setEnemies(enemyActors);
        setPlayers(players[0], players[1], players[2]);
    }

    private void setPlayers(PlayerActor char1, PlayerActor char2, PlayerActor char3) {
        player1Name.setText(char1.getName());
        player2Name.setText(char2.getName());
        player3Name.setText(char3.getName());

        playerButton1.setText(char1.getName());
        playerButton2.setText(char2.getName());
        playerButton3.setText(char3.getName());

        /* Health */

        char1HP = char1.getCurrentHp();
        char1MaxHP = char1.getMaxHp();
        char2HP = char2.getCurrentHp();
        char2MaxHP = char2.getMaxHp();
        char3HP = char3.getCurrentHp();
        char3MaxHP = char3.getMaxHp();

        player1HPText.setText(Integer.toString(char1HP) + "/" + Integer.toString(char1MaxHP));
        player2HPText.setText(Integer.toString(char2HP) + "/" + Integer.toString(char2MaxHP));
        player3HPText.setText(Integer.toString(char3HP) + "/" + Integer.toString(char3MaxHP));

        player1HPBar.setProgress(char1HP/char1MaxHP);
        player2HPBar.setProgress(char2HP/char2MaxHP);
        player3HPBar.setProgress(char3HP/char3MaxHP);

        /* Mana */

        char1MP = char1.getCurrentMp();
        char1MaxMP = char1.getMaxMp();
        char2MP = char2.getCurrentMp();
        char2MaxMP = char2.getMaxMp();
        char3MP = char3.getCurrentMp();
        char3MaxMP = char3.getMaxMp();

        player1MPText.setText(Integer.toString(char1MP) + "/" + Integer.toString(char1MaxMP));
        player2MPText.setText(Integer.toString(char2MP) + "/" + Integer.toString(char2MaxMP));
        player3MPText.setText(Integer.toString(char3MP) + "/" + Integer.toString(char3MaxMP));

        player1HPBar.setProgress(char1MP/char1MaxMP);
        player2HPBar.setProgress(char2MP/char2MaxMP);
        player3HPBar.setProgress(char3MP/char3MaxMP);

        /* Images */

        clearPlayerImages();

        charImage1.setImage(getCharacterImage(char1.getType()).getImage());
        charImage2.setImage(getCharacterImage(char2.getType()).getImage());
        charImage3.setImage(getCharacterImage(char3.getType()).getImage());
    }

    public void setPlayerHealth(int character, int health) {
        switch(character) {
            case 0:
                player1HPText.setText(Integer.toString(health) + "/" + Integer.toString(char1MaxHP));
                player1HPBar.setProgress(health/char1MaxHP);
            case 1:
                player2HPText.setText(Integer.toString(health) + "/" + Integer.toString(char2MaxHP));
                player2HPBar.setProgress(health/char2MaxHP);
            case 2:
                player3HPText.setText(Integer.toString(health) + "/" + Integer.toString(char3MaxHP));
                player3HPBar.setProgress(health/char3MaxHP);
            default:
                //???
        }
    }

    public void attackEnemy(int character, int enemy) {
        if(character == 0) {
            moveChar.setNode(charImage1);
            reverseMoveChar.setNode(charImage1);
            moveChar.playFromStart();
        } else if (character == 1) {
            moveChar.setNode(charImage2);
            reverseMoveChar.setNode(charImage2);
            moveChar.playFromStart();
        } else if (character == 2) {
            moveChar.setNode(charImage3);
            reverseMoveChar.setNode(charImage3);
            moveChar.playFromStart();
        }

        //TODO stack an attack animation over an enemy image



        reverseMoveChar.playFromStart();
    }

    public void removeEnemy(int enemy) {
        enemyImages[enemy].setImage(null);
        updateEnemyImages();

        //TODO remove enemy name from list at left
        switch (enemy) {
            case 0:
                enemyButton1.setText("");
            case 1:
                enemyButton2.setText("");
            case 2:
                enemyButton3.setText("");
            case 3:
                enemyButton4.setText("");
            case 4:
                enemyButton5.setText("");
            default:
                enemyButton6.setText("");
        }
    }

    private void setEnemies(List<Monster> enemyList) {
        clearEnemyNames();
        clearEnemyImages();
        clearEnemyButtons();

        int boxToUse = 0;

        for (int i = 0; i < enemyList.size(); i++) {
            //TODO set enemy images to their images in the array
            switch (i) {
                case 0:
                    enemyButton1.setText(enemyList.get(i).getName());
                    enemyLabel1.setText(enemyList.get(i).getName());
                case 1:
                    enemyButton2.setText(enemyList.get(i).getName());
                    enemyLabel2.setText(enemyList.get(i).getName());
                case 2:
                    enemyButton3.setText(enemyList.get(i).getName());
                    enemyLabel3.setText(enemyList.get(i).getName());
                case 3:
                    enemyButton4.setText(enemyList.get(i).getName());
                    enemyLabel4.setText(enemyList.get(i).getName());
                case 4:
                    enemyButton5.setText(enemyList.get(i).getName());
                    enemyLabel5.setText(enemyList.get(i).getName());
                default:
                    enemyButton6.setText(enemyList.get(i).getName());
                    enemyLabel6.setText(enemyList.get(i).getName());
            }
        }
        updateEnemyImages();
    }

    private void clearEnemyNames() {
        enemyLabel1.setText("");
        enemyLabel2.setText("");
        enemyLabel3.setText("");
        enemyLabel4.setText("");
        enemyLabel5.setText("");
        enemyLabel6.setText("");
    }

    private void clearPlayerImages() {
        charImage1.setImage(null);
        charImage2.setImage(null);
        charImage3.setImage(null);
    }

    private void clearEnemyImages() {
        for (int i = 0; i < enemyImages.length; i++) {
            enemyImages[i].setImage(null);
        }
        updateEnemyImages();
    }

    private void clearEnemyButtons() {
        enemyButton1.setText("");
        enemyButton2.setText("");
        enemyButton3.setText("");
        enemyButton4.setText("");
        enemyButton5.setText("");
        enemyButton6.setText("");

    }

    private CharacterImage getCharacterImage(String type) {
        switch(type) {
            case Constants.CLASS_KNIGHT:
                return new CharacterImage(0, 1, 1, 1, Constants.IMAGE_KNIGHT);
            case Constants.CLASS_SWORDSMAN:
                return new CharacterImage(0, 1, 1, 1, Constants.IMAGE_SWORDSMAN);
            case Constants.CLASS_MAGE:
                return new CharacterImage(3, 1, 1, 1, Constants.IMAGE_MAGE);
            case Constants.CLASS_THIEF:
                return new CharacterImage(2, 0, 1, 1, Constants.IMAGE_THIEF);
            default:
                return new CharacterImage(0, 1, 1, 1, Constants.IMAGE_SWORDSMAN);
        }
    }

    private void updateEnemyImages() {
        enemyImage1.setImage(enemyImages[0].getImage());
        enemyImage2.setImage(enemyImages[1].getImage());
        enemyImage3.setImage(enemyImages[2].getImage());
        enemyImage4.setImage(enemyImages[3].getImage());
        enemyImage5.setImage(enemyImages[4].getImage());
        enemyImage6.setImage(enemyImages[5].getImage());
    }

        @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screenController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert attackButton != null : "fx:id=\"attackButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert battleScreenBGBottom != null : "fx:id=\"battleScreenBGBottom\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert battleScreenBGTop != null : "fx:id=\"battleScreenBGTop\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert charImage1 != null : "fx:id=\"charImage1\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert charImage2 != null : "fx:id=\"charImage2\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert charImage3 != null : "fx:id=\"charImage3\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert chooseBox != null : "fx:id=\"chooseBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert commandBox != null : "fx:id=\"commandBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyButton1 != null : "fx:id=\"enemyButton1\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyButton2 != null : "fx:id=\"enemyButton2\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyButton3 != null : "fx:id=\"enemyButton3\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyButton4 != null : "fx:id=\"enemyButton4\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyButton5 != null : "fx:id=\"enemyButton5\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyButton6 != null : "fx:id=\"enemyButton6\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyImage1 != null : "fx:id=\"enemyImage1\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyImage2 != null : "fx:id=\"enemyImage2\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyImage3 != null : "fx:id=\"enemyImage3\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyImage4 != null : "fx:id=\"enemyImage4\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyImage5 != null : "fx:id=\"enemyImage5\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyImage6 != null : "fx:id=\"enemyImage6\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyLabel1 != null : "fx:id=\"enemyLabel1\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyLabel2 != null : "fx:id=\"enemyLabel2\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyLabel3 != null : "fx:id=\"enemyLabel3\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyLabel4 != null : "fx:id=\"enemyLabel4\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyLabel5 != null : "fx:id=\"enemyLabel5\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyLabel6 != null : "fx:id=\"enemyLabel6\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert enemyNames != null : "fx:id=\"enemyNames\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert itemsButton != null : "fx:id=\"itemsButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert magicButton != null : "fx:id=\"magicButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player1HPBar != null : "fx:id=\"player1HPBar\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player1HPText != null : "fx:id=\"player1HPText\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player1MPBar != null : "fx:id=\"player1MPBar\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player1MPText != null : "fx:id=\"player1MPText\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player1Name != null : "fx:id=\"player1Name\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player2HPBar != null : "fx:id=\"player2HPBar\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player2HPText != null : "fx:id=\"player2HPText\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player2MPBar != null : "fx:id=\"player2MPBar\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player2MPText != null : "fx:id=\"player2MPText\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player2Name != null : "fx:id=\"player2Name\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player3HPBar != null : "fx:id=\"player3HPBar\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player3HPText != null : "fx:id=\"player3HPText\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player3MPBar != null : "fx:id=\"player3MPBar\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player3MPText != null : "fx:id=\"player3MPText\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert player3Name != null : "fx:id=\"player3Name\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert playerButton1 != null : "fx:id=\"playerButton1\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert playerButton2 != null : "fx:id=\"playerButton2\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert playerButton3 != null : "fx:id=\"playerButton3\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert playerHP != null : "fx:id=\"playerHP\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert playerNames != null : "fx:id=\"playerNames\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert runButton != null : "fx:id=\"runButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
    }
}
