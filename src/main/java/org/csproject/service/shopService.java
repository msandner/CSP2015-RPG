package org.csproject.service;

import org.csproject.model.items.ArmorItem;
import org.csproject.model.items.Item;
import org.csproject.model.items.WeaponItem;

import java.util.List;

/**
 * Created by Nick on 12/7/2015.
 */
public class shopService {
    protected List<Item> weaponStock;
    protected List<Item> armorStock;
    protected List<Item> potionStock;

    public void setupShop(){
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

    }
}
