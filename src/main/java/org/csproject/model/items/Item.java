package org.csproject.model.items;

/**
 * Created by Nick on 10/29/2015.
 * The abstract item class.
 * Contains the item's name and type, as well as that items value for buying and selling
 */
public abstract class Item {
    protected String itemName;
    protected String itemType;
    protected int buyingCost;
    protected int sellingCost;

    public Item(String name, String type, int b){
        this.itemName = name;
        this.itemType = type;
        this.buyingCost = b;
        this.sellingCost = b/2;
    }

    public String getItemName(){
        return itemName;
    }

    public String getItemType(){
        return itemType;
    }

    public int getBuyingCost(){
        return buyingCost;
    }

    public int getSellingCost(){
        return sellingCost;
    }
}
