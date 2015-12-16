package org.csproject.service;

import org.csproject.model.actors.PlayerParty;
import org.csproject.model.items.ArmorItem;
import org.csproject.model.items.Item;
import org.csproject.model.items.RestorativeItem;
import org.csproject.model.items.WeaponItem;
import org.csproject.view.MasterController;
import org.csproject.view.ShopScreenController;

import java.util.List;

/**
 * Created by Nick on 12/7/2015.
 * The shop service class.
 * This class contains methods that may be called to simulate a shop.
 * The shop has three different stocks containing weapons, armor and potions.
 * Players may buy items using currency, or sell items to gain currency.
 */
public class shopService {
    protected List<Item> weaponStock;
    protected List<Item> armorStock;
    protected List<Item> potionStock;

    private ShopScreenController shopController;

    /**
     * Adds all items to the shops three stocks.
     */
    public void setupShop(PlayerParty party){
        MasterController.setScreen(MasterController.SHOP_SCREEN_ID);
        shopController = MasterController.getShopController();

        weaponStock.add(new WeaponItem("Dull Sword", "Knight", 3, 5));
        weaponStock.add(new WeaponItem("Iron Sword", "Knight", 6, 10));
        weaponStock.add(new WeaponItem("Steel Sword", "Knight", 9, 20));
        weaponStock.add(new WeaponItem("Uncarved Stave", "Mage", 2, 4));
        weaponStock.add(new WeaponItem("Carved Stave", "Mage", 5, 8));
        weaponStock.add(new WeaponItem("Runed Stave", "Mage", 8, 16));
        weaponStock.add(new WeaponItem("Knife", "Thief", 2, 4));
        weaponStock.add(new WeaponItem("Dagger", "Thief", 4, 8));
        weaponStock.add(new WeaponItem("Dark Daggers", "Thief", 6, 16));

        armorStock.add(new ArmorItem("Worn Helm", "Knight", "Head", 2, 4));
        armorStock.add(new ArmorItem("Iron Helm", "Knight", "Head", 4, 6));
        armorStock.add(new ArmorItem("Steel Helm", "Knight", "Head", 6, 12));
        armorStock.add(new ArmorItem("Old Armor", "Knight", "Body", 4, 6));
        armorStock.add(new ArmorItem("Iron Armor", "Knight", "Body", 8, 12));
        armorStock.add(new ArmorItem("Steel Armor", "Knight", "Body", 12, 24));
        armorStock.add(new ArmorItem("Cracked Gauntlets", "Knight", "Hands", 2, 3));
        armorStock.add(new ArmorItem("Iron Gauntlets", "Knight", "Hands", 4, 6));
        armorStock.add(new ArmorItem("Steel Gauntlets", "Knight", "Hands", 6, 12));
        armorStock.add(new ArmorItem("Heavy Boots", "Knight", "Feet", 1, 2));
        armorStock.add(new ArmorItem("Iron-Plated Boots", "Knight", "Feet", 3, 4));
        armorStock.add(new ArmorItem("Steel-Plated Boots", "Knight", "Feet", 5, 8));

        armorStock.add(new ArmorItem("Tattered Hood", "Mage", "Head", 1, 2));
        armorStock.add(new ArmorItem("Wizards's Hood", "Mage", "Head", 2, 4));
        armorStock.add(new ArmorItem("Embroidered Hood", "Mage", "Head", 3, 8));
        armorStock.add(new ArmorItem("Rundown Robes", "Mage", "Body", 1, 2));
        armorStock.add(new ArmorItem("Plain Robes", "Mage", "Body", 2, 4));
        armorStock.add(new ArmorItem("Runed Robes", "Mage", "Body", 4, 8));
        armorStock.add(new ArmorItem("Worn Gloves", "Mage", "Hands", 1, 2));
        armorStock.add(new ArmorItem("Simple Gloves", "Mage", "Hands", 2, 4));
        armorStock.add(new ArmorItem("Sigiled Gloves", "Mage", "Hands", 3, 8));
        armorStock.add(new ArmorItem("Battered Boots", "Mage", "Feet", 1, 2));
        armorStock.add(new ArmorItem("Wizard's Boots", "Mage", "Feet", 2, 4));
        armorStock.add(new ArmorItem("Infused Boots", "Mage", "Feet", 3, 8));

        armorStock.add(new ArmorItem("Old Mask", "Thief", "Head", 2, 4));
        armorStock.add(new ArmorItem("Shroud", "Thief", "Head", 3, 6));
        armorStock.add(new ArmorItem("Black Mask", "Thief", "Head", 4, 8));
        armorStock.add(new ArmorItem("Clothes", "Thief", "Body", 1, 2));
        armorStock.add(new ArmorItem("Leather Armor", "Thief", "Body", 3, 4));
        armorStock.add(new ArmorItem("Dark Armor", "Thief", "Body", 6, 8));
        armorStock.add(new ArmorItem("Bandage Wraps", "Thief", "Hands", 1, 2));
        armorStock.add(new ArmorItem("Fingerless Gloves", "Thief", "Hands", 2, 4));
        armorStock.add(new ArmorItem("Black Gloves", "Thief", "Hands", 3, 6));
        armorStock.add(new ArmorItem("Shoes", "Thief", "Feet", 1, 2));
        armorStock.add(new ArmorItem("Light Boots", "Thief", "Feet", 2, 4));
        armorStock.add(new ArmorItem("Muffled Boots", "Thief", "Feet", 4, 8));

        potionStock.add(new RestorativeItem("Small Health Potion", true, false, "Health", 20, 5));
        potionStock.add(new RestorativeItem("Medium Health Potion", true, false, "Health", 50, 10));
        potionStock.add(new RestorativeItem("Large Health Potion", true, false, "Health", 100, 20));
        potionStock.add(new RestorativeItem("Small Mana Potion", true, false, "Mana", 20, 10));
        potionStock.add(new RestorativeItem("Medium Mana Potion", true, false, "Mana", 50, 20));
        potionStock.add(new RestorativeItem("Large Mana Potion", true, false, "Mana", 100, 30));

        potionStock.add(new RestorativeItem("Great Health Potion", false, true, "Health", 20, 20));
        potionStock.add(new RestorativeItem("Great Mana Potion", false, true, "Mana", 20, 40));

        shopController.setupShopScreen(party);
    }

    public List<Item> getWeapons(){ return weaponStock; }

    public List<Item> getArmor(){ return armorStock; }

    public List<Item> getPotions(){ return potionStock; }

    /**
     * Method to allow players to buy items, they must have enough currency to do so
     * @param i the item to be bought
     * @param p the player party
     * @return true if the purchase was accepted, false if not
     */
    public boolean boughtItem(Item i, PlayerParty p){
        if(p.getCurrency() < i.getBuyingCost()) {
            return false;
        } else {
            p.subCurrency(i.getBuyingCost());
            p.addItem(i);
            return true;
        }
    }

    /**
     * Method to allow players to sell items, they must have the item to be able to sell it
     * @param i the item to be sold
     * @param p the player party
     * @return true if the item was sold, false if not
     */
    public boolean soldItem(Item i, PlayerParty p){
        if(p.removeItem(i)){
            p.addCurrency(i.getSellingCost());
            return true;
        }
        return false;
    }

    /**
     * Returns the player to the field
     */
    public void leaveShop(){ MasterController.setScreen(MasterController.GAME_SCREEN); }
}

