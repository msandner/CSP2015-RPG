package org.csproject.model.items;

/**
 * Created by Nick on 10/31/2015.
 */
public class ArmorItem extends Item {
    protected String armorPiece;
    protected String classRestrictions;
    protected int value;

    public ArmorItem(String name, String restrictions, String piece, int v, int b){
        super(name, "armor", b);
        this.classRestrictions = restrictions;
        this.armorPiece = piece;
        this.value = v;
    }

    public String getClassRestrictions() { return classRestrictions; }

    public String getPiece(){
        return armorPiece;
    }

    public int getValue(){
        return value;
    }
}
