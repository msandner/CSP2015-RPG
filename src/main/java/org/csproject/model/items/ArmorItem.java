package org.csproject.model.items;

/**
 * Created by Nick on 10/31/2015.
 */
public class ArmorItem extends Item {
    protected String armorPiece;
    protected int value;

    public ArmorItem(String name, String piece, int v){
        super(name, "armor");
        this.armorPiece = piece;
        this.value = v;
    }

    public String getPiece(){
        return armorPiece;
    }

    public int getValue(){
        return value;
    }
}
