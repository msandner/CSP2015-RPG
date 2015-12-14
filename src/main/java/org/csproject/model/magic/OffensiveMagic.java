package org.csproject.model.magic;

/**
 * Created by Nick on 10/31/2015.
 * Offensive magic class.
 * Offensive magic are skills characters use in battle to attack enemies.
 * Offensive magic effects can be found in the battle factory.
 * (Currently element is not used)
 */
public class OffensiveMagic extends Magic {
    protected String element;

    //value and mp have to be negative!
    public OffensiveMagic(String name, String element, int v, int mp, int levelres){
        super(name, "Offensive", v, mp, levelres);
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }


}
