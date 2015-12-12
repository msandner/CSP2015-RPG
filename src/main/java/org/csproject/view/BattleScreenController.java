package org.csproject.view;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
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
import org.csproject.model.actors.BattleActor;
import org.csproject.model.actors.Monster;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Direction;
import org.csproject.model.magic.Magic;
import org.csproject.service.ScreensController;

import javax.swing.*;
import java.net.URL;
import java.util.*;

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

    @FXML
    private HBox spellBox;

    @FXML
    private Button spellButton1;

    @FXML
    private Button spellButton2;

    @FXML
    private Button spellButton3;

    @FXML
    private Button spellButton4;

    @FXML
    private Button spellButton5;

    @FXML
    private Button spellButton6;

    @FXML
    private Button spellButton7;

    @FXML
    private Button spellButton8;

    @FXML
    private Button spellButton9;

    int char1HP, char1MaxHP, char2HP, char2MaxHP, char3HP, char3MaxHP;
    int char1MP, char1MaxMP, char2MP, char2MaxMP, char3MP, char3MaxMP;
    int attack, enemyAttacked;
    Image[] enemyImages;
    Map playerSpells;
    TranslateTransition moveChar, reverseMoveChar;
    int currentChar;
    boolean attackIsMagic;

    List<Monster> enemyList;
    List<PlayerActor> players;

    ScreensController screenController;

    public BattleScreenController() {
    }

    /* Start a new battle with the given array of PlayActors and List
        (MAX OF 6) enemies. Their images and names are all added to the correct places.
     */
    public void startNewBattle(List<PlayerActor> players, List<Monster> enemyActors) {
        battleScreenBGBottom.setImage(new Image("images/battleimages/Grassland.png"));
        battleScreenBGTop.setImage(new Image("images/battleimages/GrasslandTop.png"));
        this.players = players;
        this.enemyList = enemyActors;
        setEnemies();
        setPlayers();
    }

    /* Set all of the things related to the PlayerActors to their correct displays */
    private void setPlayers() {
        PlayerActor char1 = players.get(0);
        PlayerActor char2 = players.get(1);
        PlayerActor char3 = players.get(2);
        setUpSpells();
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

        player1HPBar.setProgress(char1HP / char1MaxHP);
        player2HPBar.setProgress(char2HP / char2MaxHP);
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

        player1MPBar.setProgress(char1MP / char1MaxMP);
        player2MPBar.setProgress(char2MP / char2MaxMP);
        player3MPBar.setProgress(char3MP / char3MaxMP);

        /* Images */

        clearPlayerImages();

        CharacterImage CI;

        CI = getCharacterImage(char1.getType());
        charImage1.setImage(CI.getImage());
        charImage1.setViewport(CI.getViewport());

        CI = getCharacterImage(char2.getType());
        charImage2.setImage(CI.getImage());
        charImage2.setViewport(CI.getViewport());

        CI = getCharacterImage(char3.getType());
        charImage3.setImage(CI.getImage());
        charImage3.setViewport(CI.getViewport());
    }

    /**
     * Select a friendly character (0, 1, 2) and set their health (up or down)
     * @param character - The character whose health needs to be altered.
     * @param health - The health of the character after the application of healing or damage.
     */
    public void setPlayerHealth(int character, int health) {
        switch(character) {
            case 0:
                player1HPText.setText(Integer.toString(health) + "/" + Integer.toString(char1MaxHP));
                player1HPBar.setProgress((double)health / (double)char1MaxHP);
                break;
            case 1:
                player2HPText.setText(Integer.toString(health) + "/" + Integer.toString(char2MaxHP));
                player2HPBar.setProgress((double)health / (double)char2MaxHP);
                break;
            case 2:
                player3HPText.setText(Integer.toString(health) + "/" + Integer.toString(char3MaxHP));
                player3HPBar.setProgress((double)health / (double)char3MaxHP);
                break;
            default:
                //???
        }
    }

    /**
     * Choose a character and an enemy and display the animation of that
     * character attacking
     * @param character - The character that is attacking (0, 1, 2)
     * @param enemy - The enemy to be attacked (0, 1, 2, 3, 4, 5)
     */
    public void attackEnemy(int character, int enemy) {
        if(character == 1) {
            moveCharForward(1);
        } else if (character == 2) {
            moveCharForward(2);
        } else if (character == 3) {
            moveCharForward(3);
        }
        //TODO stack an attack animation over an enemy image
        reverseMoveChar.playFromStart();
    }

    /**
     * Moves the character forward, so as to show whose turn it is.
     * @param character - which character to move forward, 1, 2, 3
     */
    private void moveCharForward(int character) {
        if(character == 1) {
            moveChar.setNode(charImage1);
            reverseMoveChar.setNode(charImage1);
            moveChar.playFromStart();
        } else if (character == 2) {
            moveChar.setNode(charImage2);
            reverseMoveChar.setNode(charImage2);
            moveChar.playFromStart();
        } else if (character == 3) {
            moveChar.setNode(charImage3);
            reverseMoveChar.setNode(charImage3);
            moveChar.playFromStart();
        }
    }

    private void moveCharBack() {
        reverseMoveChar.playFromStart();
    }

    /**
     * If an enemy is killed, tell the GUI to eliminate it from the battle
     * @param enemy - Which enemy to be removed (0, 1, 2, 3, 4, 5)
     */
    public void removeEnemy(int enemy) {
        enemyImages[enemy] = null;
        updateEnemyImages();

        //TODO remove enemy name from list at left
        switch (enemy) {
            case 0:
                enemyButton1.setText("");
                enemyButton1.setDisable(true);
                break;
            case 1:
                enemyButton2.setText("");
                enemyButton2.setDisable(true);
                break;
            case 2:
                enemyButton3.setText("");
                enemyButton3.setDisable(true);
                break;
            case 3:
                enemyButton4.setText("");
                enemyButton4.setDisable(true);
                break;
            case 4:
                enemyButton5.setText("");
                enemyButton5.setDisable(true);
                break;
            default:
                enemyButton6.setText("");
                enemyButton6.setDisable(true);
                break;
        }
    }

    /* Set the enemies on the field, on the buttons for attacking, and in the text box. */
    private void setEnemies() {
        clearEnemyNames();
        clearEnemyImages();
        clearEnemyButtons();
        MonsterImage m;
        if(enemyList != null && enemyList.size() < 7) {
            for (int i = 0; i < enemyList.size(); i++) {
                if(enemyList.get(i) != null) {
                    m = new MonsterImage(enemyList.get(i).getName());
                    switch (i) {
                        case 0:
                            enemyButton1.setText(enemyList.get(i).getName());
                            enemyButton1.setDisable(false);
                            enemyLabel1.setText(enemyList.get(i).getName());
                            break;
                        case 1:
                            enemyButton2.setText(enemyList.get(i).getName());
                            enemyButton2.setDisable(false);
                            enemyLabel2.setText(enemyList.get(i).getName());
                            break;
                        case 2:
                            enemyButton3.setText(enemyList.get(i).getName());
                            enemyButton3.setDisable(false);
                            enemyLabel3.setText(enemyList.get(i).getName());
                            break;
                        case 3:
                            enemyButton4.setText(enemyList.get(i).getName());
                            enemyButton4.setDisable(false);
                            enemyLabel4.setText(enemyList.get(i).getName());
                            break;
                        case 4:
                            enemyButton5.setText(enemyList.get(i).getName());
                            enemyButton5.setDisable(false);
                            enemyLabel5.setText(enemyList.get(i).getName());
                            break;
                        default:
                            enemyButton6.setText(enemyList.get(i).getName());
                            enemyButton6.setDisable(false);
                            enemyLabel6.setText(enemyList.get(i).getName());
                            break;
                    }
                    enemyImages[i] = m.getImage();
                }
            }
        }
        updateEnemyImages();
    }

    /* Start of battle, just in case, clear all enemy names from the GUI */
    private void clearEnemyNames() {
        enemyLabel1.setText("");
        enemyLabel2.setText("");
        enemyLabel3.setText("");
        enemyLabel4.setText("");
        enemyLabel5.setText("");
        enemyLabel6.setText("");
    }

    /* Clear the player images from the battle, in case they are in a different order or something. */
    private void clearPlayerImages() {
        charImage1.setImage(null);
        charImage2.setImage(null);
        charImage3.setImage(null);
    }

    /* Remove all enemy images from the field. */
    private void clearEnemyImages() {
        for (int i = 0; i < enemyImages.length; i++) {
            enemyImages[i] = null;
        }
        updateEnemyImages();
    }

    /* Same with the buttons, remove all of the text on them. */
    private void clearEnemyButtons() {
        enemyButton1.setText("");
        enemyButton1.setDisable(true);
        enemyButton2.setText("");
        enemyButton2.setDisable(true);
        enemyButton3.setText("");
        enemyButton3.setDisable(true);
        enemyButton4.setText("");
        enemyButton4.setDisable(true);
        enemyButton5.setText("");
        enemyButton5.setDisable(true);
        enemyButton6.setText("");
        enemyButton6.setDisable(true);
    }

    /**
     * Gets a character image to display an individual party member
     * @param type - The character type, most likely referenced by the Constants class
     * @return - The character image representing the class given
     */
    private CharacterImage getCharacterImage(String type) {
        switch(type) {
            case Constants.CLASS_KNIGHT:
                return new CharacterImage(0, 1, Constants.IMAGE_KNIGHT, Direction.LEFT);
            case Constants.CLASS_MAGE:
                return new CharacterImage(3, 1, Constants.IMAGE_MAGE, Direction.LEFT);
            case Constants.CLASS_THIEF:
                return new CharacterImage(2, 0, Constants.IMAGE_THIEF, Direction.LEFT);
            default:
                return new CharacterImage(0, 1, Constants.IMAGE_KNIGHT, Direction.LEFT);
        }
    }

    /* Update the enemy images all at once instead of individually */
    private void updateEnemyImages() {
        enemyImage1.setImage(enemyImages[0]);
        enemyImage2.setImage(enemyImages[1]);
        enemyImage3.setImage(enemyImages[2]);
        enemyImage4.setImage(enemyImages[3]);
        enemyImage5.setImage(enemyImages[4]);
        enemyImage6.setImage(enemyImages[5]);
    }

    /**
     * Brett Raible
     * Adds all of the available spells for this character to the list for use in battle.
     */
    private void setUpSpells() {
        playerSpells.put(1, new ArrayList<String>());
        playerSpells.put(2, new ArrayList<String>());
        playerSpells.put(3, new ArrayList<String>());
        for(PlayerActor p : players) {
            int index = 0;
            for(Magic m : p.getSpells()) {
                if(m.getLevelRestriction() <= p.getLevel() && m.getName() != "Basic") {
                    ((ArrayList)playerSpells.get(currentChar)).add(index, m.getName());
                    index++;
                }
            }
            nextChar();
        }
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screenController = screenParent;
    }

    /* Oh god why */
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
        assert spellBox != null : "fx:id=\"spellBox\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton1 != null : "fx:id=\"spellButton1\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton2 != null : "fx:id=\"spellButton2\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton3 != null : "fx:id=\"spellButton3\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton4 != null : "fx:id=\"spellButton4\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton5 != null : "fx:id=\"spellButton5\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton6 != null : "fx:id=\"spellButton6\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton7 != null : "fx:id=\"spellButton7\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton8 != null : "fx:id=\"spellButton8\" was not injected: check your FXML file 'BattleScreen.fxml'.";
        assert spellButton9 != null : "fx:id=\"spellButton9\" was not injected: check your FXML file 'BattleScreen.fxml'.";

        enemyImages = new Image[6];
        playerSpells = new HashMap<Integer, List>();

        moveChar = new TranslateTransition(Duration.seconds(.25));
        moveChar.setFromX(55);
        moveChar.setToX(-55);
        reverseMoveChar = new TranslateTransition(Duration.seconds(.25));
        reverseMoveChar.setFromX(-55);
        reverseMoveChar.setToX(55);

        currentChar = 1;
        attackIsMagic = false;

        moveCharForward(currentChar);
    }

    public void showEnemyButtons(ActionEvent actionEvent) {
        chooseBox.setVisible(true);
        if(actionEvent.getSource() == magicButton) {
            attackIsMagic = true;
        }
    }

    public void addEnemy(ActionEvent actionEvent) {
        chooseBox.setVisible(false);

        if(actionEvent.getSource() == enemyButton1) {
            System.out.println("Button 1!");
        } else if (actionEvent.getSource() == enemyButton2) {

        } else if (actionEvent.getSource() == enemyButton3) {

        } else if (actionEvent.getSource() == enemyButton4) {

        } else if (actionEvent.getSource() == enemyButton5) {

        } else if (actionEvent.getSource() == enemyButton6) {

        }

        //TODO: Add the Enemy to a list

        if(attackIsMagic) {
            resetSpellButtons();
            for (String s : ((ArrayList<String>)playerSpells.get(currentChar))) {
                if(spellButton1.getText().equals("")) {
                    spellButton1.setText(s);
                    spellButton1.setDisable(false);
                } else if (spellButton2.getText().equals("")) {
                    spellButton2.setText(s);
                    spellButton2.setDisable(false);
                } else if (spellButton3.getText().equals("")) {
                    spellButton3.setText(s);
                    spellButton3.setDisable(false);
                } else if (spellButton4.getText().equals("")) {
                    spellButton4.setText(s);
                    spellButton4.setDisable(false);
                } else if (spellButton5.getText().equals("")) {
                    spellButton5.setText(s);
                    spellButton5.setDisable(false);
                } else if (spellButton6.getText().equals("")) {
                    spellButton6.setText(s);
                    spellButton6.setDisable(false);
                } else if (spellButton7.getText().equals("")) {
                    spellButton7.setText(s);
                    spellButton7.setDisable(false);
                } else if (spellButton8.getText().equals("")) {
                    spellButton8.setText(s);
                    spellButton8.setDisable(false);
                } else if (spellButton9.getText().equals("")) {
                    spellButton9.setText(s);
                    spellButton9.setDisable(false);
                }
            }
            spellBox.setVisible(true);
            attackIsMagic = false;
        } else {
            //TODO add the "attack" to the list
            moveCharBack();
            nextChar();
        }
    }

    private void resetSpellButtons() {
        spellButton1.setText("");
        spellButton2.setText("");
        spellButton3.setText("");
        spellButton4.setText("");
        spellButton5.setText("");
        spellButton6.setText("");
        spellButton7.setText("");
        spellButton8.setText("");
        spellButton9.setText("");
        spellButton1.setDisable(true);
        spellButton2.setDisable(true);
        spellButton3.setDisable(true);
        spellButton4.setDisable(true);
        spellButton5.setDisable(true);
        spellButton6.setDisable(true);
        spellButton7.setDisable(true);
        spellButton8.setDisable(true);
        spellButton9.setDisable(true);
    }

    public void addSpell(ActionEvent actionEvent) {
        spellBox.setVisible(false);

        //TODO: Add the Spell to the list

        moveCharBack();
        nextChar();
    }

    private void nextChar() {
        if (currentChar != 3) {
            currentChar++;
        } else {
            currentChar = 1;
        }
        moveCharForward(currentChar);
    }
}