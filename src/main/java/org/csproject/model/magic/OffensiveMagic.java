package org.csproject.model.magic;

/**
 * Created by Nick on 10/31/2015.
 */
public class OffensiveMagic extends Magic {
    protected String element;

    //value has to be negative!
    public OffensiveMagic(String name, String element, int v, int mp){
        super(name, "Offensive", v, mp);
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }


}
