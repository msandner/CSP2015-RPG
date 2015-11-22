package org.csproject.view;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;

import java.util.List;

/**
 * Created by Brett on 11/22/2015.
 */
public class BattleScreen {

    public ImageView battleScreenBGTop;
    public ImageView battleScreenBGBottom;
    public HBox enemyNames;
    public VBox commandBox, playerNames, playerHP, characterBox;
    public Label player1Name, player2Name, player3Name;
    public Label player1HPText, player2HPText, player3HPText;
    public Label player1MPText, player2MPText, player3MPText;
    public ProgressBar player1HPBar, player2HPBar, player3HPBar;
    public ProgressBar player1MPBar, player2MPBar, player3MPBar;

    int char1HP, char1MaxHP, char2HP, char2MaxHP, char3HP, char3MaxHP;
    int char1MP, char1MaxMP, char2MP, char2MaxMP, char3MP, char3MaxMP;

    public BattleScreen() {
        battleScreenBGTop.setImage(new Image("images/battleimages/GrasslandTop.png"));
        battleScreenBGBottom.setImage(new Image("images/battleimages/Grassland.png"));
    }

    public void startNewBattle(PlayerActor[] players, List enemyActors) {
        clearEnemyNames();
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

        characterBox.getChildren().add(getCharacterImage(char1.getType()));
        //TODO MORE THINGS HERE
    }

    private void clearEnemyNames() {
        for (int i = 0; i < 2; i++) {
            while(((VBox)enemyNames.getChildren().get(i)).getChildren().get(0) != null) {
                ((VBox) enemyNames.getChildren().get(i)).getChildren().remove(0);
            }
        }
    }

    private void clearPlayerImages() {
        while(characterBox.getChildren().get(0) != null) {
            characterBox.getChildren().remove(0);
        }
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
}
