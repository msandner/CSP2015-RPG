package org.csproject.model.magic;

/**
 * Created by Nick on 10/31/2015.
 */
public class OffensiveMagic extends Magic {
    protected String element;
    protected int value; //has to be negative so it calculates the damage correct

    public OffensiveMagic(String name, String element, int v){
        super(name, "Offensive");
        this.element = element;
        this.value = v;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
