package org.csproject.model.items;

/**
 * Created by Nick on 10/31/2015.
 * The armor item class.
 * Armor comes in four pieces: head, body, hands and feet.
 * Armor can be equipped by a character to boost stats.
 * Armor can only be equipped to the correct character type.
 */
public class ArmorItem extends Item {
    protected String armorPiece;
    protected String restrictions;
    protected int value;

    public ArmorItem(String name, String restrictions, String piece, int v, int b){
        super(name, "armor", b);
        this.restrictions = restrictions;
        this.armorPiece = piece;
        this.value = v;
    }

    public String getRestrictions() { return restrictions; }

    public String getPiece(){
        return armorPiece;
    }

    public int getValue(){
        return value;
    }
}
