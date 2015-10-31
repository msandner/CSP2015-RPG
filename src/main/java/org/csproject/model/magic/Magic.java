package org.csproject.model.magic;

/**
 * Created by Nick on 10/31/2015.
 */
public abstract class Magic {
    protected String magicName;
    protected String magicType;

    public Magic(String name, String type){
        this.magicName = name;
        this.magicType = type;
    }

    public String getName(){
        return magicName;
    }

    public String getType(){
        return magicType;
    }
}
