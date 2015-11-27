package org.csproject.view;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.csproject.model.Constants;
import org.csproject.model.actors.Monster;
import org.csproject.model.actors.PlayerActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brett on 11/22/2015.
 */
public class BattleScreen extends Pane {

    public ImageView battleScreenBGTop;
    public ImageView battleScreenBGBottom;
    public HBox enemyNames;
    public VBox commandBox, playerNames, playerHP;
    public Label player1Name, player2Name, player3Name;
    public Label player1HPText, player2HPText, player3HPText;
    public Label player1MPText, player2MPText, player3MPText;
    public ProgressBar player1HPBar, player2HPBar, player3HPBar;
    public ProgressBar player1MPBar, player2MPBar, player3MPBar;
    public ImageView enemyImage1, enemyImage2, enemyImage3, enemyImage4, enemyImage5, enemyImage6;
    public ImageView charImage1, charImage2, charImage3;
    public Button attackButton, magicButton, itemsButton, runButton;

    int char1HP, char1MaxHP, char2HP, char2MaxHP, char3HP, char3MaxHP;
    int char1MP, char1MaxMP, char2MP, char2MaxMP, char3MP, char3MaxMP;
    ImageView[] enemyImages;
    TranslateTransition moveChar;

    public BattleScreen() {
        battleScreenBGTop.setImage(new Image("images/battleimages/GrasslandTop.png"));
        battleScreenBGBottom.setImage(new Image("images/battleimages/Grassland.png"));
        enemyImages = new ImageView[6];
        enemyImages[0] = (enemyImage1);
        enemyImages[1] = (enemyImage2);
        enemyImages[2] = (enemyImage3);
        enemyImages[3] = (enemyImage4);
        enemyImages[4] = (enemyImage5);
        enemyImages[5] = (enemyImage6);
        moveChar = new TranslateTransition(Duration.seconds(.25));
        moveChar.setFromX(0);
        moveChar.setToX(-25);
    }

    public void startNewBattle(PlayerActor[] players, List enemyActors) {
        setEnemies(enemyActors);
        setPlayers(players[0], players[1], players[2]);
    }

    private void setPlayers(PlayerActor char1, PlayerActor char2, PlayerActor char3) {
        player1Name.setText(char1.getName());
        player2Name.setText(char2.getName());
        player3Name.setText(char3.getName());

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

        charImage1 = getCharacterImage(char1.getType());
        charImage2 = getCharacterImage(char2.getType());
        charImage3 = getCharacterImage(char3.getType());
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
            moveChar.playFromStart();
        } else if (character == 1) {
            moveChar.setNode(charImage1);
            moveChar.playFromStart();
        } else if (character == 2) {
            moveChar.setNode(charImage1);
            moveChar.playFromStart();
        }

        //TODO stack an attack animation over an enemy image
    }

    public void removeEnemy(int enemy) {
        enemyImages[enemy].setImage(null);
        updateEnemyImages();
    }

    private void setEnemies(List<Monster> enemyList) {
        clearEnemyNames();
        clearEnemyImages();

        int boxToUse = 0;

        for (int i = 0; i < enemyList.size(); i++) {
            if(i > 2) {
                boxToUse = 1;
            }
            ((VBox)enemyNames.getChildren().get(boxToUse)).getChildren().add(new Label(enemyList.get(i).getName()));
            //TODO set enemy images to their images
        }
        updateEnemyImages();
    }

    private void clearEnemyNames() {
        for (int i = 0; i < 2; i++) {
            while(((VBox)enemyNames.getChildren().get(i)).getChildren().get(0) != null) {
                ((VBox) enemyNames.getChildren().get(i)).getChildren().remove(0);
            }
        }
    }

    private void clearPlayerImages() {
        charImage1 = getCharacterImage(null);
        charImage2 = getCharacterImage(null);
        charImage3 = getCharacterImage(null);
    }

    private void clearEnemyImages() {
        for (int i = 0; i < enemyImages.length; i++) {
            enemyImages[i].setImage(null);
        }
        updateEnemyImages();
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
        enemyImage1 = enemyImages[0];
        enemyImage2 = enemyImages[1];
        enemyImage3 = enemyImages[2];
        enemyImage4 = enemyImages[3];
        enemyImage5 = enemyImages[4];
        enemyImage6 = enemyImages[5];
    }
}
