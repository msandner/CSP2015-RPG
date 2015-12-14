package org.csproject.model.items;

/**
 * Created by Nick on 10/31/2015.
 * The weapon item class.
 * Can be equipped to player characters to modify their stats.
 * Weapons can only be equipped to the correct character type.
 */
public class WeaponItem extends Item {
    protected String classRestrictions;
    protected int value;

    public WeaponItem(String name, String restrictions, int v, int b){
        super(name, "Weapon", b);
        this.classRestrictions = restrictions;
        this.value = v;
    }

    public String getRestrictions(){
        return classRestrictions;
    }

    public int getValue(){
        return value;
    }
}
