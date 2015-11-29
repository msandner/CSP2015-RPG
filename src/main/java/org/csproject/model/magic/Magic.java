package org.csproject.model.magic;

/**
 * Created by Nick on 10/31/2015.
 */
public abstract class Magic {
    protected String magicName;
    protected String magicType;
    protected int value;
    protected int mp;

    //mp has to be negative!
    public Magic(String name, String type, int value, int mp){
        this.magicName = name;
        this.magicType = type;
        this.value = value;
        this.mp = mp;
    }

    public String getName(){
        return magicName;
    }

    public String getType(){
        return magicType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }
}
