package org.csproject.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.csproject.model.actors.PlayerParty;
import org.csproject.model.items.Item;
import org.csproject.service.ScreensController;
import org.csproject.service.shopService;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

/**
 * Created by Nick on 12/14/2015.
 */
public class ShopScreenController implements ControlledScreen, Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox mainBox;

    @FXML
    private Button weaponsButton;

    @FXML
    private Button armorsButton;

    @FXML
    private Button potionsButton;

    @FXML
    private Button sellButton;

    @FXML
    private Button exitButton;

    @FXML
    private HBox itemSelectionBox;

    @FXML
    private VBox itemsBox;

    @FXML
    private Button item1;

    @FXML
    private Button item2;

    @FXML
    private Button item3;

    @FXML
    private Button item4;

    @FXML
    private VBox optionsBox;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    @FXML
    private Button backButton;

    @FXML
    private HBox sellingItemSelectionBox;

    @FXML
    private VBox sellingItemsBox;

    @FXML
    private Button sellingItem1;

    @FXML
    private Button sellingItem2;

    @FXML
    private Button sellingItem3;

    @FXML
    private Button sellingItem4;

    @FXML
    private VBox sellingOptionsBox;

    @FXML
    private Button sellingUpButton;

    @FXML
    private Button sellingDownButton;

    @FXML
    private Button sellingBackButton;

    @FXML
    private ImageView shopScreenBGBottom;

    @FXML
    private ImageView shopScreenBGTop;

    private PlayerParty playerParty;

    private ArrayList<Item> weapons;

    private ArrayList<Item> armor;

    private ArrayList<Item> potions;

    private ScreensController screensController;

    private int currentItemsBase;

    private String currentType;

    private shopService shopService;

    /**
     * Nicholas Paquette
     * Sets up the shop screen; recognizes the player party and sets up item stocks
     * @param party
     */
    public void setupShopScreen(PlayerParty party) {
        shopScreenBGBottom.setImage(new Image("images/battleimages/Wood2.png"));
        shopScreenBGTop.setImage(new Image("images/battleimages/Room2.png"));

        playerParty = party;
        weapons = (ArrayList<Item>) shopService.getWeapons();
        armor = (ArrayList<Item>) shopService.getArmor();
        potions = (ArrayList<Item>) shopService.getPotions();

        showMainOptions();
    }

    /**
     * Nicholas Paquette
     * Shows the main options: show weapons, show armor, show potions, sell party items and leave the shop
     */
    public void showMainOptions(){
        itemSelectionBox.setVisible(false);
        sellingItemSelectionBox.setVisible(false);
        mainBox.setVisible(true);
        currentItemsBase = 0;
        currentType = "";
    }

    /**
     * Nicholas Paquette
     * Shows four items from the weapons stock based on currentItemsBase
     */
    public void showWeaponItems(){
        mainBox.setVisible(false);
        item1.setText(weapons.get(currentItemsBase).getItemName());
        item2.setText(weapons.get(currentItemsBase + 1).getItemName());
        item3.setText(weapons.get(currentItemsBase + 2).getItemName());
        item4.setText(weapons.get(currentItemsBase + 3).getItemName());
        itemSelectionBox.setVisible(true);
        currentType = "Weapon";
    }

    /**
     * Nicholas Paquette
     * Shows four items from the armor stock based on currentItemsBase
     */
    public void showArmorItems(){
        mainBox.setVisible(false);
        item1.setText(armor.get(currentItemsBase).getItemName());
        item2.setText(armor.get(currentItemsBase + 1).getItemName());
        item3.setText(armor.get(currentItemsBase + 2).getItemName());
        item4.setText(armor.get(currentItemsBase + 3).getItemName());
        itemSelectionBox.setVisible(true);
        currentType = "Armor";
    }

    /**
     * Nicholas Paquette
     * Shows four items from the potions stock based on currentItemsBase
     */
    public void showPotionItems(){
        mainBox.setVisible(false);
        item1.setText(potions.get(currentItemsBase).getItemName());
        item2.setText(potions.get(currentItemsBase + 1).getItemName());
        item3.setText(potions.get(currentItemsBase + 2).getItemName());
        item4.setText(potions.get(currentItemsBase + 3).getItemName());
        itemSelectionBox.setVisible(true);
        currentType = "Potion";
    }

    /**
     * Nicholas Paquette
     * The player has chosen the item in the first item box, sends a request
     * to shopService to check if the item can be bought.
     */
    public void setItem1(){
        String s = item1.getText();
        if(currentType == "Weapon"){
            for(Item i: weapons){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else if(currentType == "Armor"){
            for(Item i: armor){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else {
            for(Item i: potions){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        }
    }

    /**
     * Nicholas Paquette
     * The player has chosen the item in the second item box, sends a request
     * to shopService to check if the item can be bought.
     */
    public void setItem2(){
        String s = item2.getText();
        if(currentType == "Weapon"){
            for(Item i: weapons){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else if(currentType == "Armor"){
            for(Item i: armor){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else {
            for(Item i: potions){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        }
    }

    /**
     * Nicholas Paquette
     * The player has chosen the item in the third item box, sends a request
     * to shopService to check if the item can be bought.
     */
    public void setItem3(){
        String s = item3.getText();
        if(currentType == "Weapon"){
            for(Item i: weapons){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else if(currentType == "Armor"){
            for(Item i: armor){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else {
            for(Item i: potions){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        }
    }

    /**
     * Nicholas Paquette
     * The player has chosen the item in the fourth item box, sends a request
     * to shopService to check if the item can be bought.
     */
    public void setItem4(){
        String s = item4.getText();
        if(currentType == "Weapon"){
            for(Item i: weapons){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else if(currentType == "Armor"){
            for(Item i: armor){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        } else {
            for(Item i: potions){
                if(i.getItemName() == s){
                    shopService.boughtItem(i, playerParty);
                }
            }
        }
    }

    /**
     * Nicholas Paquette
     * Adjusts currentItemsBase by -1 if possible then calls
     * the appropriate show item method to adjust boxes
     */
    public void setUpButton(){
        if(currentType == "Weapon"){
            if(!(currentItemsBase == 0)){
                currentItemsBase--;
                showWeaponItems();
            }
        } else if(currentType == "Armor"){
            if(!(currentItemsBase == 0)){
                currentItemsBase--;
                showArmorItems();
            }
        } else {
            if(!(currentItemsBase == 0)){
                currentItemsBase--;
                showPotionItems();
            }
        }
    }

    /**
     * Nicholas Paquette
     * Adjusts currentItemsBase by +1 if possible then calls
     * the appropriate show item method to adjust boxes
     */
    public void setDownButton(){
        if(currentType == "Weapon"){
            if(!(currentItemsBase + 4 >= weapons.size())){
                currentItemsBase++;
                showWeaponItems();
            }
        } else if(currentType == "Armor"){
            if(!(currentItemsBase + 4 >= armor.size())){
                currentItemsBase++;
                showArmorItems();
            }
        } else {
            if(!(currentItemsBase + 4 >= potions.size())){
                currentItemsBase++;
                showPotionItems();
            }
        }
    }

    /**
     * Nicholas Paquette
     * Shows the party inventory to allow players to sell items
     * If the party inventory is less than four items some boxes
     * will be blank
     */
    public void showPartyItems(){
        mainBox.setVisible(false);
        if(playerParty.getInventory().size() >= 4){
            sellingItem1.setText(playerParty.getInventory().get(currentItemsBase).getItemName());
            sellingItem2.setText(playerParty.getInventory().get(currentItemsBase + 1).getItemName());
            sellingItem3.setText(playerParty.getInventory().get(currentItemsBase + 2).getItemName());
            sellingItem4.setText(playerParty.getInventory().get(currentItemsBase + 3).getItemName());
        } else if(playerParty.getInventory().size() == 3){
            sellingItem1.setText(playerParty.getInventory().get(currentItemsBase).getItemName());
            sellingItem2.setText(playerParty.getInventory().get(currentItemsBase + 1).getItemName());
            sellingItem3.setText(playerParty.getInventory().get(currentItemsBase + 2).getItemName());
            sellingItem4.setText("");
        } else if(playerParty.getInventory().size() == 2){
            sellingItem1.setText(playerParty.getInventory().get(currentItemsBase).getItemName());
            sellingItem2.setText(playerParty.getInventory().get(currentItemsBase + 1).getItemName());
            sellingItem3.setText("");
            sellingItem4.setText("");
        } else if(playerParty.getInventory().size() == 1){
            sellingItem1.setText(playerParty.getInventory().get(currentItemsBase).getItemName());
            sellingItem2.setText("");
            sellingItem3.setText("");
            sellingItem4.setText("");
        } else {
            sellingItem1.setText("");
            sellingItem2.setText("");
            sellingItem3.setText("");
            sellingItem4.setText("");
        }
        sellingItemSelectionBox.setVisible(true);
    }

    /**
     * Nicholas Paquette
     * The player chose to sell the item in the first box, calls
     * shopService to sell the item
     */
    public void setSellingItem1(){
        if(sellingItem1.getText() != ""){
            for(Item i: playerParty.getInventory()){
                if(i.getItemName() == sellingItem1.getText()){
                    shopService.soldItem(i, playerParty);
                }
            }
        }
        showPartyItems();
    }

    /**
     * Nicholas Paquette
     * The player chose to sell the item in the second box, calls
     * shopService to sell the item
     */
    public void setSellingItem2(){
        if(sellingItem2.getText() != ""){
            for(Item i: playerParty.getInventory()){
                if(i.getItemName() == sellingItem2.getText()){
                    shopService.soldItem(i, playerParty);
                }
            }
        }
        showPartyItems();
    }

    /**
     * Nicholas Paquette
     * The player chose to sell the item in the third box, calls
     * shopService to sell the item
     */
    public void setSellingItem3(){
        if(sellingItem3.getText() != ""){
            for(Item i: playerParty.getInventory()){
                if(i.getItemName() == sellingItem3.getText()){
                    shopService.soldItem(i, playerParty);
                }
            }
        }
        showPartyItems();
    }

    /**
     * Nicholas Paquette
     * The player chose to sell the item in the fourth box, calls
     * shopService to sell the item
     */
    public void setSellingItem4(){
        if(sellingItem1.getText() != ""){
            for(Item i: playerParty.getInventory()){
                if(i.getItemName() == sellingItem4.getText()){
                    shopService.soldItem(i, playerParty);
                }
            }
        }
        showPartyItems();
    }

    /**
     * Nicholas Paquette
     * Adjusts currentItemsBase by -1 if possible then calls
     * the show party inventory method to adjust boxes
     */
    public void setSellingUpButton(){
        if(!(playerParty.getInventory().size() < 4)){
            if(!(currentItemsBase == 0)){
                currentItemsBase--;
                showPartyItems();
            }
        }
    }

    /**
     * Nicholas Paquette
     * Adjusts currentItemsBase by +1 if possible then calls
     * the show party inventory method to adjust boxes
     */
    public void setSellingDownButton(){
        if(!(playerParty.getInventory().size() < 4)){
            if(!(currentItemsBase + 4 >= playerParty.getInventory().size())){
                currentItemsBase++;
                showPartyItems();
            }
        }
    }

    /**
     * Nicholas Paquette
     * Calls shopService to return the player to the field
     */
    public void leaveShop(){
        shopService.leaveShop();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert shopScreenBGTop != null : "fx:id=\"shopScreenBGTop\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert shopScreenBGTop != null : "fx:id=\"shopScreenBGTop\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert mainBox != null : "fx:id=\"mainBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert weaponsButton != null : "fx:id=\"weaponsButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert armorsButton != null : "fx:id=\"armorsButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert potionsButton != null : "fx:id=\"potionsButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellButton != null : "fx:id=\"sellButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert itemSelectionBox != null : "fx:id=\"ItemSelectionBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert itemsBox != null : "fx:id=\"itemsBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert item1 != null : "fx:id=\"item1\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert item2 != null : "fx:id=\"item2\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert item3 != null : "fx:id=\"item3\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert item4 != null : "fx:id=\"item4\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert optionsBox != null : "fx:id=\"optionsBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert upButton != null : "fx:id=\"upButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert downButton != null : "fx:id=\"downButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingItemSelectionBox != null : "fx:id=\"sellingItemSelectionBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingItemsBox != null : "fx:id=\"sellingItemsBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingItem1 != null : "fx:id=\"sellingItem1\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingItem2 != null : "fx:id=\"sellingItem2\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingItem3 != null : "fx:id=\"sellingItem3\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingItem4 != null : "fx:id=\"sellingItem4\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingOptionsBox != null : "fx:id=\"sellingOptionsBox\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingUpButton != null : "fx:id=\"sellingUpButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingDownButton != null : "fx:id=\"sellingDownButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";
        assert sellingBackButton != null : "fx:id=\"sellingBackButton\" was not injected: check your FXML file 'BattleScreenController.fxml'.";

        shopService = new shopService();
    }
}
