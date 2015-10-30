package org.csproject.model.items;

/**
 * Created by Nick on 10/29/2015.
 */
public abstract class Item {
    protected String itemName;
    protected String itemType;

    public Item(String name, String type){
        this.itemName = name;
        this.itemType = type;
    }

    public String getItemName(){
        return itemName;
    }

    public String getItemType(){
        return itemType;
    }
}
