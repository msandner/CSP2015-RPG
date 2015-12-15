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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.csproject.model.Constants;
import org.csproject.model.actors.*;
import org.csproject.model.bean.Direction;
import org.csproject.model.magic.Magic;
import org.csproject.model.magic.OffensiveMagic;
import org.csproject.model.magic.RestorativeMagic;
import org.csproject.service.BattleFactory;
import org.csproject.service.ScreensController;

import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Brett on 11/22/2015.
 *
 * Controls the battle screen's FXML file as well as some of the logic of the battle
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
    private Label expLabel;

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
    int enemyAttacked, friendTargeted;
    Image[] enemyImages;
    Map playerSpells;
    TranslateTransition moveChar, reverseMoveChar;
    int currentChar;
    boolean attackIsMagic, isFriendAttacked, turnDone;

    List<Monster> enemyList;
    List<PlayerActor> players;
    List playerCommands;
    BattleFactory factory;
    MediaPlayer songPlayer;

    ScreensController screenController;
    /**
     * Brett Raible
     *
     * Start a new battle with the given array of PlayActors and List
     * (MAX OF 6) enemies. Their images and names are all added to the correct places.
     * @param players - The PlayerActor's with which to start the new battle
     * @param enemyActors - The PlayerActor's that will act as the enemies.
     */
    public void startNewBattle(List<PlayerActor> players, List<Monster> enemyActors) {
        battleScreenBGBottom.setImage(new Image("images/battleimages/Grassland.png"));
        battleScreenBGTop.setImage(new Image("images/battleimages/GrasslandTop.png"));
        this.players = players;
        this.enemyList = enemyActors;
        setEnemies();
        setPlayers();

        Media song = new Media(Paths.get("src/main/resources/sounds/BGM/battle/GameBattleMusicv3.mp3").toUri().toString());
        songPlayer = new MediaPlayer(song);
        songPlayer.play();
        songPlayer.setCycleCount(40000);
    }

    /**
     * Brett Raible
     *
     * Set all of the things related to the PlayerActors to their correct displays
     */
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

        setPlayerStats(players);

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
     * Bett Raible
     * Select a friendly character (0, 1, 2) and set their health (up or down)
     * @param thisParty - The list of the players that you want to update the stats with
     */
    public void setPlayerStats(List<PlayerActor> thisParty) {
        boolean dead = false;

        PlayerActor player1 = thisParty.get(0);
        PlayerActor player2 = thisParty.get(1);
        PlayerActor player3 = thisParty.get(2);

        if(player1.getCurrentHp() < 0) {
            player1.setCurrentHp(0);
        }
        if(player2.getCurrentHp() < 0) {
            player2.setCurrentHp(0);
        }
        if(player3.getCurrentHp() < 0) {
            player3.setCurrentHp(0);
        }

        if(player1.getCurrentMp() < 0) {
            player1.setCurrentMp(0);
        }
        if(player2.getCurrentMp() < 0) {
            player2.setCurrentMp(0);
        }
        if(player3.getCurrentMp() < 0) {
            player3.setCurrentMp(0);
        }

        player1HPText.setText(Integer.toString(player1.getCurrentHp()) + "/" + Integer.toString(player1.getMaxHp()));
        player2HPText.setText(Integer.toString(player2.getCurrentHp()) + "/" + Integer.toString(player2.getMaxHp()));
        player3HPText.setText(Integer.toString(player3.getCurrentHp()) + "/" + Integer.toString(player3.getMaxHp()));

        player1HPBar.setProgress((double)player1.getCurrentHp()/(double)player1.getMaxHp());
        player2HPBar.setProgress((double)player2.getCurrentHp()/(double)player2.getMaxHp());
        player3HPBar.setProgress((double)player3.getCurrentHp()/(double)player3.getMaxHp());

        player1MPText.setText(Integer.toString(player1.getCurrentMp()) + "/" + Integer.toString(player1.getMaxMp()));
        player2MPText.setText(Integer.toString(player2.getCurrentMp()) + "/" + Integer.toString(player2.getMaxMp()));
        player3MPText.setText(Integer.toString(player3.getCurrentMp()) + "/" + Integer.toString(player3.getMaxMp()));

        player1MPBar.setProgress((double)player1.getCurrentMp() / player1.getMaxMp());
        player2MPBar.setProgress((double)player1.getCurrentMp() / player2.getMaxMp());
        player3MPBar.setProgress((double)player1.getCurrentMp() / player3.getMaxMp());

        if (player1.getCurrentHp() <= 0) {
            charImage1.setVisible(false);
        } else {
            charImage1.setVisible(true);
        }

        if (player2.getCurrentHp() <= 0) {
            charImage2.setVisible(false);
        } else {
            charImage2.setVisible(true);
        }

        if (player3.getCurrentHp() <= 0) {
            charImage3.setVisible(false);
        } else {
            charImage3.setVisible(true);
        }
    }

    /**
     * Brett Raible
     *
     * Choose a character and an enemy and display the animation of that
     * character attacking
     * @param character - The character that is attacking (0, 1, 2)
     * @param enemy - The enemy to be attacked (0, 1, 2, 3, 4, 5)
     */
    public void attackEnemy(int character, int enemy) {
        //Do we need these?
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
     * Brett Raible
     *
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

    /**
     * Brett Raible
     *
     * Plays the transition to move the character back.
     */
    private void moveCharBack() {
        reverseMoveChar.playFromStart();
    }

    /**
     * Brett Raible
     *
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
                enemyLabel1.setText("");
                break;
            case 1:
                enemyButton2.setText("");
                enemyButton2.setDisable(true);
                enemyLabel2.setText("");
                break;
            case 2:
                enemyButton3.setText("");
                enemyButton3.setDisable(true);
                enemyLabel3.setText("");
                break;
            case 3:
                enemyButton4.setText("");
                enemyButton4.setDisable(true);
                enemyLabel4.setText("");
                break;
            case 4:
                enemyButton5.setText("");
                enemyButton5.setDisable(true);
                enemyLabel5.setText("");
                break;
            default:
                enemyButton6.setText("");
                enemyButton6.setDisable(true);
                enemyLabel6.setText("");
                break;
        }
    }

    /**
     * Brett Raible
     *
     * Set the enemies on the field, on the buttons for attacking, and in the text box.
     */
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

    /**
     * Brett Raible
     *
     * Set all enemy names to blank text.
     */
    private void clearEnemyNames() {
        enemyLabel1.setText("");
        enemyLabel2.setText("");
        enemyLabel3.setText("");
        enemyLabel4.setText("");
        enemyLabel5.setText("");
        enemyLabel6.setText("");
    }

    /**
     * Brett Raible
     *
     * Sets each player image to null.
     */
    private void clearPlayerImages() {
        charImage1.setImage(null);
        charImage2.setImage(null);
        charImage3.setImage(null);
    }

    /**
     * Brett Raible
     *
     * Sets all images in the array to null.
     */
    private void clearEnemyImages() {
        for (int i = 0; i < enemyImages.length; i++) {
            enemyImages[i] = null;
        }
        updateEnemyImages();
    }

    /**
     * Brett Raible
     *
     * Sets enemy buttons to blank text.
     */
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
     * Brett Raible
     *
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

    /**
     * Brett Raible
     *
     * Update GUI images based on array (for easier implementation).
     */
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
        int index = 1;
        for(PlayerActor p : players) {
            for(Magic m : p.getSpells()) {
                if(m.getLevelRestriction() <= p.getLevel() && m.getName() != "Basic") {
                    ((ArrayList)playerSpells.get(index)).add(m.getName());
                }
            }
            index++;
        }
    }

    /**
     * Brett Raible
     *
     * Sets the HBox for actor buttons to visible and let's the game know if the attack is magic.
     * @param actionEvent - Magic or Attack button
     */
    public void showEnemyButtons(ActionEvent actionEvent) {
        spellBox.setVisible(false);
        chooseBox.setVisible(true);
        if(actionEvent.getSource() == magicButton) {
            attackIsMagic = true;
        } else {
            attackIsMagic = false;
        }
    }

    /**
     * Brett Raible
     *
     * Makes the enemy button box invisible, sets which enemy is getting attacked and either
     * gives the spell buttons their text, or calls getMagic("Basic")
     * @param actionEvent - One of the enemy buttons
     */
    public void addEnemy(ActionEvent actionEvent) {
        chooseBox.setVisible(false);
        setEnemyAttacked(actionEvent);
        isFriendAttacked = false;
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
            //Attack is basic attack
            getMagic("Basic");
            moveCharBack();
            nextChar();
        }
    }

    /**
     * Brett Raible
     *
     * Calls addCommands once it finds the magic specified in the
     * current character's available spell list.
     * @param magicName
     */
    private void getMagic(String magicName) {
        PlayerActor p = players.get(currentChar-1);
        for(Magic m : p.getSpells()) {
            if(m.getName().equals(magicName)) {
                addCommands(m);
            }
        }
    }

    /**
     * Brett Raible
     *
     * Sets all of the spell button's text to nothing and disables them.
     */
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

    /**
     * Brett Raible
     *
     * Sets the enemyAttacked integer to one for reference in the list of enemies
     * @param actionEvent - The enemy being attacked
     */
    private void setEnemyAttacked(ActionEvent actionEvent) {
        if(actionEvent.getSource() == enemyButton1) {
            enemyAttacked = 0;
        } else if (actionEvent.getSource() == enemyButton2) {
            enemyAttacked = 1;
        } else if (actionEvent.getSource() == enemyButton3) {
            enemyAttacked = 2;
        } else if (actionEvent.getSource() == enemyButton4) {
            enemyAttacked = 3;
        } else if (actionEvent.getSource() == enemyButton5) {
            enemyAttacked = 4;
        } else if (actionEvent.getSource() == enemyButton6) {
            enemyAttacked = 5;
        }
    }

    /**
     * Same thing as if you were targeting an enemy, but with an ally instead.
     * @param actionEvent
     */
    public void setFriendTargeted(ActionEvent actionEvent){
        chooseBox.setVisible(false);
        isFriendAttacked = true;
        if (actionEvent.getSource() == playerButton1) {
            friendTargeted = 0;
        } else if (actionEvent.getSource() == playerButton2) {
            friendTargeted = 1;
        } else {
            friendTargeted = 2;
        }

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
            //Attack is basic attack
            getMagic("Basic");
            moveCharBack();
            nextChar();
        }
    }

    /**
     * Brett Raible
     *
     * Adds the commands current character, the enemy attacked, and the magic used
     * to the list of commands.
     * @param m - The magic used to attack.
     */
    private void addCommands(Magic m) {
        playerCommands.add(players.get(currentChar-1));
        if(!isFriendAttacked) {
            playerCommands.add(enemyList.get(enemyAttacked));
        } else {
            playerCommands.add(players.get(friendTargeted));
        }
        playerCommands.add(m);
    }

    /**
     * Brett Raible
     *
     * Adds a spell that is not "Basic" to the command list. Moves the character backwards.
     * @param actionEvent - The spell to be added.
     */
    public void addSpell(ActionEvent actionEvent) {
        spellBox.setVisible(false);
        getMagic(((Button)actionEvent.getSource()).getText());
        moveCharBack();
        nextChar();
    }

    /**
     * Brett Raible
     *
     * Increments the current character, determining who is attacking and all that.
     */
    private void nextChar() {
        if (currentChar != 3) {
            currentChar++;
            if(players.get(currentChar-1).getCurrentHp() <= 0) {
                nextChar();
            } else {
                moveCharForward(currentChar);
            }
        } else {
            currentChar = 1;
            doTheBattle();
            turnDone = true;
        }
    }

    /**
     * Brett Raible
     *
     * Begins new round, emptying command list.
     */
    public void newRound() {
        turnDone = false;
        playerCommands.clear();
        for (int i = 0; i < players.size() ; i++) {
            players.get(i).setHasAttacked(false);
        }
        currentChar = 0;
        nextChar();
    }

    /**
     * Brett Raible
     *
     * Tells the caller if the player is done with the turn
     * @return
     */
    public boolean isTurnDone() {
        return turnDone;
    }

    /**
     * Brett Raible & Maren Sandner
     * procedure of the round based battle
     */
    public int doTheBattle() {
        int index = 0;
        PlayerParty playerParty = new PlayerParty(players.get(0), players.get(1), players.get(2), 0);
        MonsterParty monsterParty = new MonsterParty(enemyList);
        PlayerActor attacker = null;
        BattleActor monster;
        Monster nextMonster;
        int monsterPos = 0, nextMonsterPos = 0;
        Magic m;

        while(!playerCommands.isEmpty()) {
            for (PlayerActor p : playerParty.getParty()) {
                if (p.getName() == ((PlayerActor)playerCommands.get(0)).getName()) {
                    attacker = p;
                }
            }
            index++;
            playerCommands.remove(0);
            monster = (BattleActor) playerCommands.get(0);
            if(monster.getClass() == Monster.class) {
                monsterPos = monsterParty.getMonsterPosition((Monster) monster);
                if (monsterPos == monsterParty.getPartySize() - 1 && monsterParty.getParty().size() > 1) {
                    nextMonster = monsterParty.getMonster(monsterParty.getPartySize() - 2);
                } else if (monsterParty.getParty().size() > 1) {
                    nextMonster = monsterParty.getMonster(monsterPos + 1);
                } else {
                    nextMonster = (Monster)monster;
                }
                nextMonsterPos = monsterParty.getMonsterPosition((Monster) nextMonster);
            } else {
                nextMonster = new Monster("Null Monster", "Bat", 420000, 0, 300000000, 0);
            }
            playerCommands.remove(0);
            m = (Magic) playerCommands.get(0);
            playerCommands.remove(0);
            System.out.println(monster.getName() + " HP : " + monster.getCurrentHp());

            switch(m.getName()) {
                case "Basic":
                    factory.basicAttack(attacker, monster);
                    break;
                case "Shield Bash":
                    factory.shieldBash(attacker, monster);
                    break;
                case "Whirlwind":
                    factory.whirlwind(attacker, monster, nextMonster);
                    break;
                case "Berserk":
                    factory.berserk(attacker, monster);
                    break;
                case "Massive Sword Slash":
                    factory.attackCharacterWithMagic(attacker, monster, (OffensiveMagic) m, 1);
                    break;
                case "Fireball":
                    factory.attackCharacterWithMagic(attacker, monster, (OffensiveMagic) m, 1);
                    break;
                case "Chain Lightning":
                    factory.chainLightning(attacker, monster, monsterParty);
                    break;
                case "Heal":
                    factory.healCharactersWithMagic(attacker, monster, playerParty, (RestorativeMagic) m);
                    break;
                case "Frostbite":
                    factory.frostbite(attacker, monster);
                    break;
                case "Ambush":
                    factory.ambush(attacker, monster);
                    break;
                case "Mutilate":
                    factory.mutilate(attacker, monster);
                    break;
                case "Execute":
                    factory.execute(attacker, monster);
                    break;
                case "Shuriken Toss":
                    factory.shurikenToss(attacker, monsterParty);
                    break;
                default:
                    break;
            }
            System.out.println(monster.getName() + " HP: " + monster.getCurrentHp());
            if (monster.is_dead()) {
                removeEnemy(monsterPos);
            }
            if(nextMonster.getClass() == Monster.class && nextMonster.is_dead()) {
                removeEnemy(nextMonsterPos);
            }
            if(monsterParty.isEveryEnemyDead()) {
                for(PlayerActor p : playerParty.getParty()) {
                    p.addXP(monsterParty.getXP() / 3);
                }
                playerCommands.clear();
                newRound();
                expLabel.setText("Each player got " + monsterParty.getXP()/3 + " EXP!");
                Thread fadeOut = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        expLabel.setVisible(true);
                        while(songPlayer.getVolume() >= .000001) {
                            songPlayer.setVolume(songPlayer.getVolume()-.000001);
                        }
                        expLabel.setVisible(false);
                        factory.endBattle();
                    }
                });
                fadeOut.start();
                return 1;
            }
            if (!playerParty.canPartyStillAttack()) {
                for(Monster thisMonster : monsterParty.getParty()) {
                   if(!thisMonster.is_dead())
                       factory.enemyAttackAI(thisMonster, playerParty, new OffensiveMagic("Basic2", "There is none", -15, 20, 0));
                }
            }

            for (int i = 0; i < playerParty.getParty().size(); i++) {
                PlayerActor thisActor = playerParty.getParty().get(i);
                players.set(i, thisActor);
            }
            setPlayerStats(playerParty.getParty());

            if(playerParty.isEveryPlayerDead()) {
                factory.gameOver();
                songPlayer.stop();
                return 0;
            }
        }
        for(PlayerActor p : playerParty.getParty()) {
            p.setHasAttacked(false);
        }
        for (BattleActor mon : monsterParty.getParty()) {
            mon.setHasAttacked(false);
        }
        newRound();
        return 0;
    }

    /**
     * Brett Raible
     *
     * Gives the player the list of commands for the turn.
     * @return - The player's commands; in the format of Player, Monster, Magic
     */
    public List getPlayerCommands() {
        return playerCommands;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screenController = screenParent;
    }

    /**
     * Brett Raible
     *
     * Initialize all global & FXML variables.
     * @param url
     * @param resourceBundle
     */
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
        assert expLabel != null : "fx:id=\"expLabel\" was not injected: check your FXML file 'BattleScreen.fxml'.";
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
        playerCommands = new ArrayList();

        moveChar = new TranslateTransition(Duration.seconds(.25));
        moveChar.setFromX(55);
        moveChar.setToX(-55);
        reverseMoveChar = new TranslateTransition(Duration.seconds(.25));
        reverseMoveChar.setFromX(-55);
        reverseMoveChar.setToX(55);

        currentChar = 1;
        attackIsMagic = false;
        isFriendAttacked = false;
        turnDone = false; //TODO: More with Turn Done
        factory = new BattleFactory();

        moveCharForward(currentChar);
    }
}