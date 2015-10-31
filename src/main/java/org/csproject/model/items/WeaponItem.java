package org.csproject.model.items;

/**
 * Created by Nick on 10/31/2015.
 */
public class WeaponItem extends Item {
    protected String classRestrictions;
    protected int value;

    public WeaponItem(String name, String restrictions, int v){
        super(name, "Weapon");
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
